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

(ns emender-jenkins.jenkins-api
    "Module with functions implementing Jenkins API.")

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
    (if (empty? jenkins-auth)
        job-config-url
        (cond (.startsWith job-config-url "https://") (str "https://" jenkins-auth "@" (subs job-config-url 8))
              (.startsWith job-config-url "http://")  (str "http://"  jenkins-auth "@" (subs job-config-url 7))
              :else job-config-url)))

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

(defn filter-test-jobs
    [all-jobs preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix]
    (filter #(or (.endsWith (get %1 "name") preview-jobs-suffix)
                 (.endsWith (get %1 "name") stage-jobs-suffix)
                 (.endsWith (get %1 "name") prod-jobs-suffix))
            all-jobs))

(defn read-list-of-test-jobs
    [jenkins-url job-list-part preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix]
    (-> (read-list-of-all-jobs jenkins-url job-list-part)
        (filter-test-jobs preview-jobs-suffix stage-jobs-suffix prod-jobs-suffix)))

(defn read-job-results
    [jenkins-url job-name]
    (let [url (str (job-name->url jenkins-url job-name) "/lastSuccessfulBuild/artifact/results.json/")]
        (println "Using the following URL to retrieve job results: " url)
        (try
            (slurp url)
            (catch Exception e
                 (.printStackTrace e)
                 nil))))

(defn ok-response-structure
    [job-name command jenkins-response]
    {:status   "ok"
     :job-name job-name
     :command  command
     :jenkins-response jenkins-response})

(defn error-response-structure
    [job-name command exception]
    {:status   "error"
     :job-name job-name
     :command  command
     :message  (.getMessage exception)
    })

(defn job-related-command
    [jenkins-url jenkins-auth job-name command]
    (try
        (let [response (post-command jenkins-url jenkins-auth job-name command)]
            (ok-response-structure job-name command response))
        (catch Exception e
            (.printStackTrace e)
            (error-response-structure job-name command e))))

(defn update-template
    [template git-repo branch]
    (-> template
        (clojure.string/replace "<placeholder id=\"git-repo\" />" git-repo)
        (clojure.string/replace "<placeholder id=\"git-branch\" />" (str "*/" branch))))

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

(defn log-operation
    [job-name git-repo branch operation]
    (println "***" operation "***")
    (println "job-name" job-name)
    (println "git-repo" git-repo)
    (println "branch"   branch))

(defn send-configuration-xml-to-jenkins
    [url config]
    (http-client/post url {
        :body             config
        :content-type     "application/xml"
        :keystore         "keystore"
        :keystore-pass    "changeit"
        :trust-store      "keystore"
        :trust-store-pass "changeit"}))

(defn create-job
    [jenkins-url jenkins-auth job-name git-repo branch]
    (log-operation job-name git-repo branch "create")
    (let [template (slurp "data/template.xml")
          config   (update-template template git-repo branch)
          url      (str (update-jenkins-url jenkins-url jenkins-auth) "createItem?name=" (.replaceAll job-name " " "%20"))]
          (println "URL to use: " url)
          (try
              (->> (send-configuration-xml-to-jenkins url config)
                   (ok-response-structure job-name "create"))
              (catch Exception e
                  (.printStackTrace e)
                  (error-response-structure job-name "create" e)))))

(defn update-job
    [jenkins-url jenkins-auth job-name git-repo branch]
    (log-operation job-name git-repo branch "update")
    (let [template (slurp "data/template.xml")
          config   (update-template template git-repo branch)
          url      (str (job-name->url (update-jenkins-url jenkins-url jenkins-auth) job-name) "/config.xml")]
          (println "URL to use: " url)
          (try
              (->> (send-configuration-xml-to-jenkins url config)
                   (ok-response-structure job-name "update"))
              (catch Exception e
                  (.printStackTrace e)
                  (error-response-structure job-name "update" e)))))

