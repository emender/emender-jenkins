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
    [job-name command include-jenkins-reply? jenkins-response]
    (if include-jenkins-reply?
        {:status   "ok"
         :job-name job-name
         :command  command
         :jenkins-response jenkins-response}
        {:status   "ok"
         :job-name job-name
         :command  command}))

(defn error-response-structure
    [job-name command exception]
    {:status   "error"
     :job-name job-name
     :command  command
     :message  (.getMessage exception)
    })

(defn replace-placeholder
    [string placeholder-name value]
    (if value
        (clojure.string/replace string (str "<placeholder id=\"" placeholder-name "\" />") value)
        string))

(defn update-template
    [template git-repo branch metadata]
    (-> template
        (replace-placeholder "git-repo"                   git-repo)
        (replace-placeholder "git-branch"                 (str "*/" branch))
        (replace-placeholder "metadata-language"          (get metadata :language))
        (replace-placeholder "metadata-environment"       (get metadata :environment))
        (replace-placeholder "metadata-content-directory" (get metadata :content_directory))
        (replace-placeholder "metadata-content-type"      (get metadata :content_type))))

(defn log-operation
    [job-name git-repo branch operation metadata]
    (println "***" operation "***")
    (println "job-name" job-name)
    (println "git-repo" git-repo)
    (println "branch"   branch)
    (println "metadata" metadata))

(defn send-configuration-xml-to-jenkins
    [url config]
    (http-client/post url {
        :body             config
        :content-type     "application/xml"
        :keystore         "keystore"
        :keystore-pass    "changeit"
        :trust-store      "keystore"
        :trust-store-pass "changeit"}))

(defn get-template
    [metadata]
    (slurp (if metadata "data/template_with_metadata.xml" "data/template.xml")))

(defn create-job
    [jenkins-url jenkins-auth include-jenkins-reply? job-name git-repo branch metadata]
    (log-operation job-name git-repo branch "create" metadata)
    (let [template (get-template metadata)
          config   (update-template template git-repo branch metadata)
          url      (str (update-jenkins-url jenkins-url jenkins-auth) "createItem?name=" (.replaceAll job-name " " "%20"))]
          (println "URL to use: " url)
          (try
              (->> (send-configuration-xml-to-jenkins url config)
                   (ok-response-structure job-name "create" include-jenkins-reply?))
              (catch Exception e
                  (.printStackTrace e)
                  (error-response-structure job-name "create" e)))))

(defn update-job
    [jenkins-url jenkins-auth include-jenkins-reply? job-name git-repo branch metadata]
    (log-operation job-name git-repo branch "update" metadata)
    (let [template (get-template metadata)
          config   (update-template template git-repo branch metadata)
          url      (str (job-name->url (update-jenkins-url jenkins-url jenkins-auth) job-name) "/config.xml")]
          (println "URL to use: " url)
          (try
              (->> (send-configuration-xml-to-jenkins url config)
                   (ok-response-structure job-name "update" include-jenkins-reply?))
              (catch Exception e
                  (.printStackTrace e)
                  (error-response-structure job-name "update" e)))))

