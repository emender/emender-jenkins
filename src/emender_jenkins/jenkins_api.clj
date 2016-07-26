(ns emender-jenkins.jenkins-api)

(require '[clojure.xml       :as xml])
(require '[clojure.zip       :as zip])

(require '[clj-http.client   :as http-client])

(require '[clojure.data.json :as json])
(require '[clj-http.client   :as http-client])

(defn get-command 
    [url]
    (:body (http-client/get url {
                    :keystore "keystore"
                    :keystore-pass "changeit"
                    :trust-store "keystore"
                    :trust-store-pass "changeit"})))

(defn job-name->url
    [jenkins-url job-name]
    (str jenkins-url "job/" (.replaceAll job-name " " "%20")))

(defn post-command
    [jenkins-url job-name command]
    (let [url (str (job-name->url jenkins-url job-name) "/" command)]
        (println "URL to use: " url)
        (http-client/post url {
                    :keystore "keystore"
                    :keystore-pass "changeit"
                    :trust-store "keystore"
                    :trust-store-pass "changeit"})))

(defn read-list-of-all-jobs
    [jenkins-url job-list-part]
    (let [all-jobs-url (str jenkins-url job-list-part)]
        (println "Using the following URL to retrieve all Jenkins jobs: " all-jobs-url)
        (let [data (json/read-str (get-command all-jobs-url))]
            (if data
                (get data "jobs")
                nil))))

(defn read-list-of-test-jobs
    [jenkins-url job-list-part suffix]
     (let [all-jobs (read-list-of-all-jobs jenkins-url job-list-part)
           test-jobs (filter #(.endsWith (get %1 "name") suffix) all-jobs)]
           test-jobs))

(defn read-job-results
    [jenkins-url job-name]
    (let [url (str (job-name->url jenkins-url job-name) "/lastSuccessfulBuild/artifact/results.json/")]
        (println "Using the following URL to retrieve job results: " url)
        (try
            (slurp url)
            (catch Exception e
                 (.printStackTrace e)
                 nil))))

(defn start-job
    [jenkins-url job-name]
    (post-command jenkins-url job-name "build"))

(defn enable-job
    [jenkins-url job-name]
    (post-command jenkins-url job-name "enable"))

(defn disable-job
    [jenkins-url job-name]
    (post-command jenkins-url job-name "disable"))

