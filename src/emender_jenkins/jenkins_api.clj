(ns emender-jenkins.jenkins-api)

(require '[clojure.xml       :as xml])
(require '[clojure.zip       :as zip])

(require '[clojure.data.json :as json])
(require '[clj-http.client   :as http-client])

(defn read-list-of-all-jobs
    [jenkins-url job-list-part]
    (let [all-jobs-url (str jenkins-url job-list-part)]
        (println "Using the following URL to retrieve all Jenkins jobs: " all-jobs-url)
        (let [data (json/read-str (slurp all-jobs-url))]
            (if data
                (get data "jobs")
                nil))))

(defn read-list-of-test-jobs
    [jenkins-url job-list-part suffix]
     (let [all-jobs (read-list-of-all-jobs jenkins-url job-list-part)
           test-jobs (filter #(.endsWith (get %1 "name") suffix) all-jobs)]
           test-jobs))

