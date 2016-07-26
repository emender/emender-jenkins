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

(ns emender-jenkins.rest-api)

(require '[ring.util.response           :as http-response])
(require '[clojure.pprint               :as pprint])
(require '[clojure.data.json            :as json])

(require '[emender-jenkins.file-utils   :as file-utils])
(require '[emender-jenkins.results      :as results])
(require '[emender-jenkins.config       :as config])
(require '[emender-jenkins.jenkins-api  :as jenkins-api])

(defn read-request-body
    [request]
    (file-utils/slurp- (:body request)))

(defn body->results
    [body]
    (json/read-str body))

(defn body->job-info
    [body]
    (json/read-str body :key-fn clojure.core/keyword))

(defn get-job-name
    [json]
    (if json
        (get json :name)))

(defn get-job-name-from-body
    [request]
    (-> (read-request-body request)
        body->job-info
        get-job-name))

(defn send-response
    [response]
    (-> (http-response/response (json/write-str response))
        (http-response/content-type "application/json")))

(defn send-plain-response
    [response]
    (-> (http-response/response response)
        (http-response/content-type "application/json")))

(defn info-handler
    [request hostname]
    (let [response {:name       "Emender Jenkins Service"
                    :version    (config/get-version request)
                    :api_prefix (config/get-api-prefix request)
                    :hostname   hostname :test "/api"}]
        (send-response response)))

(defn configuration-handler
    [request]
    (let [response (-> request :configuration)]
        (send-response response)))

(defn system-banners
    [request uri]
    (if (= uri "/api/system/banners")
        (let [response {:message "Alpha version"
                        :type    "Warning"}]
        (send-response response))))

(defn reload-all-results
    [request]
    (results/reload-all-results (:configuration request))
    (let [response @results/results]
        (send-response response)))

(defn create-job
    [request]
    )

(defn delete-job
    [request]
    (let [job-name (get-job-name-from-body request)]
         (println job-name)
    ))

(defn start-job
    [request]
    (let [job-name (get-job-name-from-body request)]
        (when (results/job-exists? job-name)
           (println "Starting " job-name)
           (let [response (jenkins-api/start-job (config/get-jenkins-url request) job-name)]
               (send-response response)))))

(defn enable-job
    [request]
    (let [job-name (get-job-name-from-body request)]
        (when (results/job-exists? job-name)
           (println "Enabling: " job-name)
           (let [response (jenkins-api/enable-job (config/get-jenkins-url request) job-name)]
               (send-response response)))))

(defn disable-job
    [request]
    (let [job-name (get-job-name-from-body request)]
        (when (results/job-exists? job-name)
           (println "Disabling " job-name)
           (let [response (jenkins-api/disable-job (config/get-jenkins-url request) job-name)]
               (send-response response)))))

(defn uri->job-name
    [uri prefix]
    (try
        (let [secondPart (subs uri (count prefix))
              job-name
                 (-> secondPart
                     clojure.string/trim
                     (.replaceAll "%20" " "))]
                 (if (empty? job-name) nil job-name))
        (catch IndexOutOfBoundsException e
             nil)))

(defn get-job
    [request uri]
    (let [job-name (uri->job-name uri "/api/get_job/")]
        (if job-name
            (let [job-metadata (results/find-job-with-name job-name)]
                 (send-response job-metadata)))))

(defn update-job
    [request]
    )

(defn get-jobs
    [request]
    (let [job-names (results/get-job-names)]
        (send-response job-names)))

(defn get-job-results
    [request uri]
    (let [job-name (uri->job-name uri "/api/get_job_results/")]
        (if (results/job-exists? job-name)
            (let [job-results   (jenkins-api/read-job-results (config/get-jenkins-url request) job-name)]
                 (if job-results
                     (send-plain-response job-results))))))

(defn job-started-handler
    [request]
    (println "job-started")
    )

(defn job-finished-handler
    [request]
    (println "job-finished")
    )

(defn job-results
    [request]
    (println "job-results")
    )

(defn unknown-call-handler
    [uri method]
    (let [response {:status :error
                    :error "Unknown API call"
                    :uri uri
                    :method method}]
        (send-response response)))

