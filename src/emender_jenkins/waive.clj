;
;  (C) Copyright 2017  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns emender-jenkins.waive
    "Test waiving functionality")

(require '[clojure.data.json            :as json])
(require '[clj-fileutils.fileutils      :as file-utils])
(require '[clj-utils.utils              :refer :all])

(require '[emender-jenkins.results      :as results])
(require '[emender-jenkins.db-interface :as db-interface])

(defn read-waive-input-data
    [request]
    (try
        (-> request
            :body
            file-utils/slurp-
            (json/read-str :key-fn clojure.core/keyword))
        (catch Exception e
            (throw-exception "Can not read POST data: " e))))

(defn parse-job-name
    [input-data]
    (let [job-name (:job_name input-data)]
        (-> input-data
            (assoc :product (results/job-name->product-name job-name))
            (assoc :version (results/job-name->version job-name))
            (assoc :guide   (results/job-name->book-name job-name))
            (dissoc :job_name))))

(defn assert-not-empty
    [waive-data key-name message]
    (if (empty? (get waive-data key-name))
        (throw-exception message)))

(defn check-waive-data
    [waive-data]
    (assert-not-empty waive-data :product    "Product name not specified")
    (assert-not-empty waive-data :version    "Version not specified")
    (assert-not-empty waive-data :guide      "Guide (book) name not specified")
    (assert-not-empty waive-data :test_suite "Test suite not specified")
    (assert-not-empty waive-data :test_name  "Test name not specified")
    (assert-not-empty waive-data :cause      "Cause not specified"))

(defn parse-waive-data
    [input-data]
    (let [parsed-data (if (:job_name input-data)
                          (parse-job-name input-data)
                          input-data)]
         (check-waive-data parsed-data)
         parsed-data))

(defn waive-test
    [request]
        (let [waive-input-data  (read-waive-input-data request)
              parsed-waive-data (parse-waive-data waive-input-data)]
              (db-interface/insert-test-waive parsed-waive-data)
              parsed-waive-data))

(defn get-waives
    [product version book test-case test-name]
    {:product   product
     :version   version
     :book      book
     :test_case test-case
     :test_name test-name
     :waives    (db-interface/read-waives product version book test-case test-name)
     })

