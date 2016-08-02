;
;  (C) Copyright 2016  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns emender-jenkins.results)

(require '[clojure.pprint :as pprint])

(require '[emender-jenkins.file-utils  :as file-utils])
(require '[emender-jenkins.jenkins-api :as jenkins-api])
(require '[emender-jenkins.config      :as config])

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
    (let [name-version (clojure.string/split job-name #"-")
          product-name (second name-version)]
          (if product-name
              (clojure.string/replace product-name "_" " "))))

(defn job-name->version
    [job-name]
    (let [name-version (clojure.string/split job-name #"-")
          version      (get name-version 2)]
          (if (and version (re-matches #"[0-9.]+" version))
              version
              "unknown")))

(defn job-name->book-name
    [job-name]
    (let [name-version (clojure.string/split job-name #"-")
          book-name    (get name-version 3)]
          (if book-name
              (clojure.string/replace book-name "_" " ")
              "unknown")))

(defn job-name->environment
    [job-name preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix]
    (cond
        (.endsWith job-name preview-jobs-suffix) :preview
        (.endsWith job-name stage-jobs-suffix)   :stage
        (.endsWith job-name prod-jobs-suffix)    :prod))

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

(defn parse-int
    [string]
    (java.lang.Integer/parseInt string))

(defn parse-test-results
    [message]
    (if message
        (let [parsed (re-matches #"Total: ([0-9]+)  Passed: ([0-9]+)  Failed: ([0-9]+)" message)]
            (if (= (count parsed) 4)
                {:total  (parse-int (get parsed 1))
                 :passed (parse-int (get parsed 2))
                 :failed (parse-int (get parsed 3))}))))

(defn read-update-job-info
    [job-list preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix]
    (for [job job-list]
        (let [job-name (get job "name")
              job-color  (get job "color")
              buildable? (get job "buildable")
              message    (-> (get job "lastSuccessfulBuild")
                             (get "description"))]
            {:job-name    job-name
             :product     (job-name->product-name job-name)
             :version     (job-name->version job-name)
             :environment (job-name->environment job-name preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix)
             :book-name   (job-name->book-name job-name)
             :job-status  (compute-job-status job-color buildable?)
             :disabled    (compute-job-disabled job-color buildable?)
             :message     message
             :results     (parse-test-results message)
            })))

(defn reload-all-results
    [configuration]
    (let [preview-jobs-suffix (-> configuration :jobs    :preview-test-jobs-suffix)
          stage-jobs-suffix   (-> configuration :jobs    :stage-test-jobs-suffix)
          prod-jobs-suffix    (-> configuration :jobs    :prod-test-jobs-suffix)
          job-list (jenkins-api/read-list-of-test-jobs (-> configuration :jenkins :jenkins-url)
                                                       (-> configuration :jenkins :jenkins-job-list-url)
                                                       preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix)
          job-results (read-update-job-info job-list   preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix)]
          (clojure.pprint/pprint job-results)
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
        (:book-name job))))

(defn job-for-environment
    [product version book-name environment results]
    (-> (select-jobs results #(and (= product (:product %))
                                   (= version (:version %))
                                   (= book-name (:book-name %))
                                   (= environment (:environment %))))
        first))

(defn select-results-for-book
    "Select results for given book and return them as a map with results separated for preview, stage, prod."
    [product version book-name results]
    {:preview (job-for-environment product version book-name :preview results)
     :stage   (job-for-environment product version book-name :stage   results)
     :prod    (job-for-environment product version book-name :prod    results)})

(defn select-results-for-product-version
    "Select results for all books for given product and version."
    [product version results]
    (into {}
          (for [book (books-for-product-version results product version)]
               [book (select-results-for-book product version book results)])))

(defn select-results-for-product
    "Select results for all books for given product."
    [product results]
    (into {}
          (for [version (versions-per-products product results)]
               [version (select-results-for-product-version product version results)])))

(defn get-job-results
    [product version]
    (let [results  (read-job-results product version)
          products (all-products results)] ; set of all products read from test results
          (into {}
                (for [product products]
                     [product (select-results-for-product product results)]))))

(defn find-job-with-name
    [job-name]
    (some #(if (= job-name (:job-name %)) %) @results))

(defn job-exists?
    [job-name]
    (if job-name
        (some #(= job-name (:job-name %)) @results)))

; REPL testing
;(def results (atom (clojure.edn/read-string (slurp "x.edn"))))

