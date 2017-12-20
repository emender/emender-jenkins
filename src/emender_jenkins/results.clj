;
;  (C) Copyright 2016  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns emender-jenkins.results
    "Module with functions to handle and manipulate test results.")

(require '[clojure.pprint              :as pprint])
(require '[clojure.data.json           :as json])
(require '[clj-fileutils.fileutils     :as file-utils])
(require '[clj-jenkins-api.jenkins-api :as jenkins-api])
(require '[clojure.tools.logging       :as log])

(require '[emender-jenkins.config      :as config])
(require '[emender-jenkins.time-utils  :as time-utils])
(use     '[clj-utils.utils])

(def results (atom nil))

(defn render-edn-data
    "Render EDN data to be used for debugging purposes etc."
    [output-data pretty-print?]
    (if pretty-print?
        (with-out-str (pprint/pprint output-data))
        (with-out-str (println output-data))))

(defn add-new-results
    [job-name new-results]
    (swap! results assoc job-name new-results))

(defn store-results
    [pretty-print?]
    (let [edn-data (render-edn-data @results pretty-print?)]
        (spit "results2.edn" edn-data)
        ; rename files atomically (on the same filesystem)
        (file-utils/mv-file "results2.edn" "results.edn")))

(defn job-name->product-name
    [job-name]
    (if job-name
        (let [name-version (clojure.string/split job-name #"-")
              product-name (second name-version)]
              (if product-name
                  (clojure.string/replace product-name "_" " ")))
        "unknown"))

(defn job-name->version
    [job-name]
    (if job-name
        (let [name-version (clojure.string/split job-name #"-")
              version      (get name-version 2)]
              (if (and version (re-matches #"[0-9.]+" version))
                  version
                  "unknown"))
        "unknown"))

(defn job-name->book-name
    [job-name]
    (if job-name
        (let [name-version (clojure.string/split job-name #"-")
              book-name    (get name-version 3)]
              (if book-name
                  (clojure.string/replace book-name "_" " ")
                  "unknown"))
        "unknown"))

(defn job-name->environment
    [job-name preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix]
    (cond
        (endsWith job-name preview-jobs-suffix) :preview
        (endsWith job-name stage-jobs-suffix)   :stage
        (endsWith job-name prod-jobs-suffix)    :prod))

(defn compute-job-status
    [jenkins-job-status buildable?]
    ; check if the 'disabled' option is set in job config
    (if (not buildable?)
        :disabled
        (if jenkins-job-status ; job is buildable, so let's check the icon
            (cond (= jenkins-job-status "blue")     :ok
                  (= jenkins-job-status "yellow")   :unstable
                  (= jenkins-job-status "disabled") :disabled ; should it happen?
                  :else                             :failure)
            :does-not-exists)))

(defn compute-job-disabled
    [jenkins-job-status buildable?]
    ; check if the 'disabled' option is set in job config
    (if (not buildable?)
        true
        (if jenkins-job-status ; job is buildable, so let's check the icon
            (cond (= jenkins-job-status "blue")     false
                  (= jenkins-job-status "yellow")   false
                  (= jenkins-job-status "disabled") true
                  :else                             false)
            true)))

(defn parse-test-results
    [message]
    (if message
        (let [parsed (re-matches #"Total: ([0-9]+)  Passed: ([0-9]+)  Failed: ([0-9]+)" message)]
            (if (= (count parsed) 4)
                {:total  (parse-int (get parsed 1))
                 :passed (parse-int (get parsed 2))
                 :failed (parse-int (get parsed 3))}))))

(defn test-summary
    [jenkins-url job-name message]
    (if message
        {:url         (str jenkins-url "job/" (clojure.string/replace job-name " " "%20") "/lastSuccessfulBuild/artifact/results.html")
         :results     (parse-test-results message)}))

(defn read-update-job-info
    [jenkins-url job-list preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix]
    (for [job job-list]
        (let [job-name   (get job "name")
              job-color  (get job "color")
              buildable? (get job "buildable")
              message    (-> (get job "lastSuccessfulBuild")
                             (get "description"))]
            {:jobName     job-name
             :product     (job-name->product-name job-name)
             :version     (job-name->version job-name)
             :environment (job-name->environment job-name preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix)
             :title       (job-name->book-name job-name)
             :jobStatus   (compute-job-status job-color buildable?)
             :disabled    (compute-job-disabled job-color buildable?)
             :testSummary (test-summary jenkins-url job-name message)
            })))

(defn test-job?
    [job-name jobs-prefix preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix]
    (and (startsWith job-name jobs-prefix)
         (or (endsWith job-name preview-jobs-suffix)
             (endsWith job-name stage-jobs-suffix)
             (endsWith job-name prod-jobs-suffix))))

(defn filter-test-jobs
    [all-jobs jobs-prefix preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix]
    (filter #(test-job? (get %1 "name")
                        jobs-prefix preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix)
            all-jobs))

(defn read-list-of-test-jobs
    [jenkins-url job-list-part jobs-prefix preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix]
    (-> (jenkins-api/read-list-of-all-jobs jenkins-url job-list-part)
        (filter-test-jobs jobs-prefix preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix)))

(defn reload-all-results
    [configuration]
    (let [test-jobs-prefix    (-> configuration :jobs :test-jobs-prefix)
          preview-jobs-suffix (-> configuration :jobs :preview-test-jobs-suffix)
          stage-jobs-suffix   (-> configuration :jobs :stage-test-jobs-suffix)
          prod-jobs-suffix    (-> configuration :jobs :prod-test-jobs-suffix)
          verbose             (-> configuration :config :verbose)
          pretty-print        (-> configuration :config :pretty-print)
          job-list (read-list-of-test-jobs (-> configuration :jenkins :jenkins-url)
                                           (-> configuration :jenkins :jenkins-job-list-url)
                                           test-jobs-prefix
                                           preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix)
          job-results (read-update-job-info (-> configuration :jenkins :jenkins-url) job-list   preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix)]
          (log/info "Job read:" (count job-results))

          ; print pretty printed tree of all jobs to the standard output
          (if (and verbose pretty-print)
              (clojure.pprint/pprint job-results))

          ;(spit "test.edn" (with-out-str (clojure.pprint/pprint job-results)))
          ;job-results (read-all-test-results configuration job-list)]
          (reset! results job-results)
          ;job-results))
))

(defn select-jobs
    [results pred]
    (into []
        (filter pred results)))

(defn read-job-results
    [product version]
    (cond
        (and product version) (select-jobs @results #(and (= product (:product %)) (= version (:version %))))
        product               (select-jobs @results #(= product (:product %)))
        :else                 (into [] @results)))

(defn all-products
    "Returns set containing names of all products taken from results."
    [results]
    (into #{} (for [result results] (:product result))))

(defn versions-per-products
    "Returns set containing versions for given product."
    [product results]
    (into #{} (for [result results :when (= (:product result) product)] (:version result))))

(defn books-for-product-version
    [results product version]
    (into #{}
    (for [job (select-jobs results #(and (= product (:product %)) (= version (:version %))))]
        (:title job))))

(defn job-for-environment
    [product version book-name environment results]
    (-> (select-jobs results #(and (= product (:product %))
                                   (= version (:version %))
                                   (= book-name (:title %))
                                   (= environment (:environment %))))
        first))

(defn select-results-for-book
    "Select results for given book and return them as a map with results separated for preview, stage, prod."
    [product version book-name results]
    {:tests
        {:preview (job-for-environment product version book-name :preview results)
         :stage   (job-for-environment product version book-name :stage   results)
         :prod    (job-for-environment product version book-name :prod    results)}})

(defn select-results-for-product-version
    "Select results for all books for given product and version."
    [product version results]
    {:titles
        (into {}
              (for [book (books-for-product-version results product version)]
                   [book (select-results-for-book product version book results)]))})

(defn select-results-for-product
    "Select results for all books for given product."
    [product results]
    {:versions
        (into {}
              (for [version (versions-per-products product results)]
                   [version (select-results-for-product-version product version results)]))})

(defn get-job-results
    []
    @results)

(defn get-job-results-as-tree
    [product version]
    (let [results  (read-job-results product version)
          products (all-products results)] ; set of all products read from test results
          {:products
              (into {}
                    (for [product products]
                         [product (select-results-for-product product results)]))}))

(defn find-job-with-name
    [job-name]
    (some #(if (= job-name (:jobName %)) %) @results))

(defn job-exists?
    [job-name]
    (if job-name
        (some #(= job-name (:jobName %)) @results)))

(def currently-building-jobs                  (atom nil))
(def currently-building-jobs-jenkins-response (atom nil))
(def currently-building-jobs-cache-timestamp(atom nil))

(def jobs-in-queue                            (atom nil))
(def jobs-in-queue-jenkins-response           (atom nil))
(def jobs-in-queue-cache-timestamp          (atom nil))

(def running-jobs                             (atom nil))

(defn get-building-jobs-url
    "Construct URL used to read all jobs that are currently building."
    [configuration]
    (let [jenkins-url   (-> configuration :jenkins :jenkins-url)
          view          (-> configuration :jenkins :currently-building-view)]
          (str jenkins-url "view/" view "/")))

(defn read-building-jobs-from-jenkins
    "Read info about building jobs via Jenkins REST API."
    [url job-list-part]
    (try
        (jenkins-api/read-list-of-all-jobs url job-list-part)
        (catch Exception e
            (log/error (.getMessage e))
            nil)))

(defn create-currently-building-jobs-response
    [jobs]
    (map #(get % "name") jobs))

(defn read-currently-building-jobs
    [configuration]
    (let [url           (get-building-jobs-url configuration)
          job-list-part (-> configuration :jenkins :jenkins-job-list-url)
          jobs          (read-building-jobs-from-jenkins url job-list-part)]
          (reset! currently-building-jobs-jenkins-response jobs)
          (reset! currently-building-jobs
              (if jobs (create-currently-building-jobs-response jobs) nil))
          jobs))

(defn get-job-name-from-queue-info
    "Read job name from the structure about one item in Jenkins build queue."
    [job-info]
    (-> job-info (get "task") (get "name")))

(defn get-jobs-in-queue-url
    "Construct URL used to read all jobs that are put into Jenkins build queue."
    [configuration]
    (let [jenkins-url   (-> configuration :jenkins :jenkins-url)
          job-list-part (-> configuration :jenkins :in-queue-url)]
          (str jenkins-url job-list-part)))

(defn read-queue-info-from-jenkins
    "Read info about queue via Jenkins REST API."
    [url]
    (try
        (-> (jenkins-api/get-command url)
            json/read-str
            (get "items"))
        (catch Exception e
            (log/error (.getMessage e))
            nil)))

(defn create-jobs-in-queue-response
    [items]
    (let [job-names       (map get-job-name-from-queue-info items) ; items are sorted properly!
          queue-positions (range (count items) 0 -1)]              ; we need to have index assigned to each item in queue
          (for [[queue-position job-name] (zipmap queue-positions job-names)]
             {"queuePos" queue-position
              "jobName"  job-name})))

(defn read-jobs-in-queue
    [configuration]
    (let [url   (get-jobs-in-queue-url configuration)
          jobs  (read-queue-info-from-jenkins url)]
          (reset! jobs-in-queue-jenkins-response jobs)
          (reset! jobs-in-queue
              (if jobs (create-jobs-in-queue-response jobs) nil))
          jobs))

(defn prepare-jobs-in-queue
    [jobs-in-queue]
    (map #(assoc % "state" "QUEUED") (create-jobs-in-queue-response jobs-in-queue)))

(defn prepare-building-jobs
    [building-jobs]
    (for [building-job building-jobs]
        {"state" "BUILDING"
         "jobName" (get building-job "name")}))

(defn create-running-jobs-response
    [jobs-in-queue building-jobs]
    (concat
        (prepare-jobs-in-queue jobs-in-queue)
        (prepare-building-jobs building-jobs)))

(defn read-running-jobs
    "Get (and return) all jobs that are in Jenkins queue or that are currently building."
    [configuration]
    (let [jobs-in-queue @jobs-in-queue-jenkins-response
          building-jobs @currently-building-jobs-jenkins-response]
          (reset! running-jobs
          (if (and jobs-in-queue building-jobs) (create-running-jobs-response jobs-in-queue building-jobs)))))

(defn update-timestamp
    [atom-name]
    (reset! atom-name (time-utils/ms->seconds (System/currentTimeMillis))))

(defn update-cache
    "Returns true if update was performed."
    [cache-name cache-timestamp max-age function-to-call configuration]
    (let [cache-age (time-utils/elapsed-time @cache-timestamp)]
        (if (not cache-age)
            (do
                (log/info (str cache-name " cache will be read for the first time"))
                (function-to-call configuration)
                (update-timestamp cache-timestamp)
                true)
            (when (> cache-age max-age)
                (log/info (str "Updating " cache-name " (the cache is " (int cache-age) " seconds old, max age is set to " max-age " seconds)"))
                (function-to-call configuration)
                (update-timestamp cache-timestamp)
                true))))

(defn update-currently-building-jobs-cache
    "Updates currently building jobs cache if its age is too old."
    [configuration]
    (update-cache "currenty building jobs"
                  currently-building-jobs-cache-timestamp
                  (-> configuration :fetcher :currently-building-jobs-cache-max-age)
                  read-currently-building-jobs
                  configuration))

(defn update-jobs-in-queue-cache
    "Updates jobs in queue cache if its age is too old."
    [configuration]
    (update-cache "jobs in queue"
                  jobs-in-queue-cache-timestamp
                  (-> configuration :fetcher :jobs-in-queue-cache-max-age)
                  read-jobs-in-queue
                  configuration))

(defn update-running-jobs-cache
    "Updates running jobs cache if its age is too old."
    [configuration]
    ; we need to use local vars here to avoid short-circuit evaluation!
    (let [cache1-updated (update-currently-building-jobs-cache configuration)
          cache2-updated (update-jobs-in-queue-cache configuration)]
         (if (or cache1-updated cache2-updated)
             (read-running-jobs configuration))))

(defn get-currently-building-jobs
    "Get the content of currently building jobs cache, cache is re-read if needed."
    [configuration]
    (update-currently-building-jobs-cache configuration)
    @currently-building-jobs)

(defn get-jobs-in-queue
    "Get the content of jobs in queue cache, cache is re-read if needed."
    [configuration]
    (update-jobs-in-queue-cache configuration)
    @jobs-in-queue)

(defn get-running-jobs
    "Get the content of running jobs cache, cache is re-read if needed."
    [configuration]
    (update-running-jobs-cache configuration)
    @running-jobs)

; REPL testing
; (require '[clojure.pprint :as pprint])
; (require '[clojure.edn])
; (def results (atom (clojure.edn/read-string (slurp "x.edn"))))

; (clojure.pprint/pprint
; (get-job-results "Red Hat Enterprise Linux" "6")
; )

