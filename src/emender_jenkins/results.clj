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
    [job-list]
    (for [job job-list]
        (let [job-name (get job "name")
              job-color  (get job "color")
              buildable? (get job "buildable")
              message    (-> (get job "lastSuccessfulBuild")
                             (get "description"))]
            {:job-name  job-name
             :product   (job-name->product-name job-name)
             :version   (job-name->version job-name)
             :book-name (job-name->book-name job-name)
             :job-status (compute-job-status job-color buildable?)
             :message    message
             :results    (parse-test-results message)
            })))

(defn reload-all-results
    [configuration]
    (let [job-list (jenkins-api/read-list-of-test-jobs (-> configuration :jenkins :jenkins-url)
                                                       (-> configuration :jenkins :jenkins-job-list-url)
                                                       (-> configuration :jobs    :test-jobs-suffix))
          job-results (read-update-job-info job-list)]
          (clojure.pprint/pprint job-results)
          ;job-results (read-all-test-results configuration job-list)]
          (reset! results job-results)
          ;job-results))
))

(defn find-job-with-name
    [job-name]
    (some #(if (= job-name (:job-name %)) %) @results))

