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

(defn update-jenkins-url
    [job-config-url jenkins-auth]
    (cond (.startsWith job-config-url "https://") (str "https://" jenkins-auth "@" (subs job-config-url 8))
          (.startsWith job-config-url "http://")  (str "http://"  jenkins-auth "@" (subs job-config-url 7))
          :else job-config-url))

(defn post-command
    [jenkins-url jenkins-auth job-name command]
    (let [url (str (job-name->url (update-jenkins-url jenkins-url jenkins-auth) job-name) "/" command)]
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

(defn job-related-command
    [jenkins-url jenkins-auth job-name command]
    (try
        (let [response (post-command jenkins-url jenkins-auth job-name command)]
            {:status   "ok"
             :job-name job-name
             :command  command
             :jenkins-response response})
        (catch Exception e
            (.printStackTrace e)
            {:status "error"
             :job-name job-name
             :command  command
             :message (.getMessage e)
            })))

(defn start-job
    [jenkins-url jenkins-auth job-name]
    (job-related-command jenkins-url jenkins-auth job-name "build"))

(defn enable-job
    [jenkins-url jenkins-auth job-name]
    (job-related-command jenkins-url jenkins-auth job-name "enable"))

(defn disable-job
    [jenkins-url jenkins-auth job-name]
    (job-related-command jenkins-url jenkins-auth job-name "disable"))

(defn delete-job
    [jenkins-url jenkins-auth job-name]
    (job-related-command jenkins-url jenkins-auth job-name "doDelete"))

