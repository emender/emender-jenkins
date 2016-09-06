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

(ns emender-jenkins.rest-api
    "Handler for all REST API calls.")

(require '[ring.util.response                :as http-response])
(require '[clojure.pprint                    :as pprint])
(require '[clojure.data.json                 :as json])
(require '[clj-jenkins-api.jenkins-api       :as jenkins-api])
(require '[clj-fileutils.fileutils           :as file-utils])

(require '[emender-jenkins.results           :as results])
(require '[emender-jenkins.config            :as config])
(require '[emender-jenkins.metadata-reader   :as metadata-reader])
(require '[emender-jenkins.metadata-exporter :as metadata-exporter])
(require '[emender-jenkins.metadata-analyzer :as metadata-analyzer])

; command names used by various REST API responses
(def commands {
    :start-job             "start_job"
    :enable-job            "enable_job"
    :disable-job           "disable_job"
    :create-job            "create_job"
    :delete-job            "delete_job"
    :update-job            "update_job"
    :get-job               "get_job"
    :get-job-results       "get_job_results"})

; HTTP codes used by several REST API responses
(def http-codes {
    :ok                    200
    :bad-request           400
    :not-found             404
    :internal-server-error 500
    :not-implemented       501})

(def metadata-directory "data")

(defn read-request-body
    "Read all informations from the request body."
    [request]
    (file-utils/slurp- (:body request)))

(defn body->results
    "Try to parse the request body as JSON format."
    [body]
    (json/read-str body))

(defn body->job-info
    "Try to parse the request body as JSON format and keywordize keys."
    [body]
    (if body
        (json/read-str body :key-fn clojure.core/keyword)))

(defn get-job-name
    "Common function to retrieve job name from given data structure."
    [json]
    (if json
        (get json :name)))

(defn get-job-name-from-body
    "Returns job name from request body or nil if any exception happens."
    [request]
    (try
        (-> (read-request-body request)
            body->job-info
            get-job-name)
        (catch Exception e
            nil)))

(defn test-job?
    [job-name request]
    (results/test-job? job-name (config/get-test-jobs-prefix request)
                                (config/get-preview-test-jobs-suffix request)
                                (config/get-stage-test-jobs-suffix request)
                                (config/get-prod-test-jobs-suffix request)))

(defn send-response
    [response request]
    (if (config/pretty-print? request)
        (-> (http-response/response (with-out-str (json/pprint response)))
            (http-response/content-type "application/json"))
        (-> (http-response/response (json/write-str response))
            (http-response/content-type "application/json"))))

(defn send-error-response
    [response request http-code]
    (if (config/pretty-print? request)
        (-> (http-response/response (with-out-str (json/pprint response)))
            (http-response/content-type "application/json")
            (http-response/status (get http-codes http-code)))
        (-> (http-response/response (json/write-str response))
            (http-response/content-type "application/json")
            (http-response/status (get http-codes http-code)))))

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
        (send-response response request)))

(defn configuration-handler
    [request]
    (if (config/verbose-show-configuration? request)
        (let [response (-> request :configuration)]
            (send-response response request))
        (let [response (-> request :configuration
                           (assoc-in [:jenkins :jenkins-auth] "********"))]
            (send-response response request))))

(defn system-banners
    [request uri]
    (if (= uri "/api/system/banners")
        (let [response {:message "Alpha version"
                        :type    "Warning"}]
        (send-response response request))))

(defn reload-job-list
    [previous-response request]
    (results/reload-all-results (:configuration request))
    previous-response)

(defn create-error-response
    [job-name command message]
    {:status "error"
     :jobName job-name
     :command command
     :message message})

(defn create-bad-request-response
    ([command message]
        {:status "error"
         :command command
         :message message})
    ([command]
        {:status "error"
         :command command}))

(defn job-does-not-exist-response
    [job-name command]
    (create-error-response job-name (get commands command) "Job does not exist"))

(defn wrong-job-name
    [job-name command]
    (create-error-response job-name (get commands command) "The name of job is wrong"))

(defn job-already-exist-response
    [job-name command]
    (create-error-response job-name (get commands command) "Job already exist"))

(defn send-job-does-not-exist-response
    [request job-name command]
    (-> (job-does-not-exist-response job-name command)
        (send-error-response request :not-found)))

(defn send-wrong-job-name-response
    [request job-name command]
    (-> (wrong-job-name job-name command)
        (send-error-response request :bad-request)))

(defn send-job-not-specified-response
    [request command]
    (-> (create-bad-request-response (get commands command) "job name is not specified")
        (send-error-response request :bad-request)))

(defn send-job-invalid-metadata-response
    [request command message]
    (-> (create-bad-request-response (get commands command) message)
        (send-error-response request :bad-request)))

(defn reload-all-results
    [request]
    (try
        (results/reload-all-results (:configuration request))
        (let [response @results/results]
            (send-response response request))
        (catch Exception e
                (-> (create-error-response "(not needed)" "reload-all-results" (.getMessage e))
                    (send-error-response request :internal-server-error)))))

(defn reload-tests-metadata
    [request]
    (try
        (results/reload-all-results (:configuration request))
        (metadata-reader/reload-tests-metadata (:configuration request) (results/get-job-results))
        (let [response (metadata-reader/get-metadata)]
            (send-response response request))
        (catch Exception e
                (.printStackTrace e)
                (-> (create-error-response "(not needed)" "reload-tests-metadata" (.getMessage e))
                    (send-error-response request :internal-server-error)))))

(defn perform-job-command
    [request function command]
    (let [job-name (get-job-name-from-body request)]
        (if job-name
            (if (test-job? job-name request)
                (if (results/job-exists? job-name)
                    (-> (function (config/get-jenkins-url request)
                                  (config/get-jenkins-auth request)
                                  (config/include-jenkins-reply? request)
                                  job-name)
                        (reload-job-list request)
                        (send-response request))
                    (send-job-does-not-exist-response request job-name command))
                (send-wrong-job-name-response request job-name command))
            (send-job-not-specified-response request command))))

(defn start-job
    [request]
    (perform-job-command request jenkins-api/start-job :start-job))

(defn enable-job
    [request]
    (perform-job-command request jenkins-api/enable-job :enable-job))

(defn disable-job
    [request]
    (perform-job-command request jenkins-api/disable-job :disable-job))

(defn delete-job
    [request]
    (perform-job-command request jenkins-api/delete-job :delete-job))

(defn job-invalid-input
    [job-name git-repo branch]
    (cond
        (nil? job-name) "invalid input: job name not specified"
        (nil? git-repo) "invalid input: git repo not specified"
        (nil? branch)   "invalid input: branch not specified"
        :else           "other error"))

(defn job-metadata-ok?
    [job-name git-repo branch]
    (and job-name git-repo branch))

(defn create-job
    [request]
    (try
        (let [input-data (-> (read-request-body request)
                             body->job-info)
              job-name   (get input-data :name)
              git-repo   (get input-data :ssh_url_to_repo)
              branch     (get input-data :branch)
              metadata   (get input-data :metadata)]
            (if (test-job? job-name request)
                (if (results/job-exists? job-name)
                    (-> (job-already-exist-response job-name :create-job)
                        (send-error-response request :bad-request))
                    (if (job-metadata-ok? job-name git-repo branch)
                        (-> (jenkins-api/create-job (config/get-jenkins-url request)
                                                    (config/get-jenkins-auth request)
                                                    (config/include-jenkins-reply? request)
                                                    job-name git-repo branch
                                                    (config/get-credentials-id request)
                                                    metadata-directory metadata)
                            (reload-job-list request)
                            (send-response request))
                        (send-job-invalid-metadata-response request :create-job (job-invalid-input job-name git-repo branch))))
                (send-wrong-job-name-response request job-name :create-job)))
        (catch Exception e
            (.printStackTrace e)
            (send-job-invalid-metadata-response request :create-job "invalid or missing input"))))

(defn update-job
    [request]
    (try
        (let [input-data (-> (read-request-body request)
                             body->job-info)
              job-name   (get input-data :name)
              git-repo   (get input-data :ssh_url_to_repo)
              branch     (get input-data :branch)
              metadata   (get input-data :metadata)]
            (if (test-job? job-name request)
                (if (results/job-exists? job-name)
                    (if (job-metadata-ok? job-name git-repo branch)
                        (-> (jenkins-api/update-job (config/get-jenkins-url request)
                                                    (config/get-jenkins-auth request)
                                                    (config/include-jenkins-reply? request)
                                                    job-name git-repo branch
                                                    (config/get-credentials-id request)
                                                    metadata-directory metadata)
                            ;(reload-job-list request)
                            (send-response request))
                        (send-job-invalid-metadata-response request :update-job (job-invalid-input job-name git-repo branch)))
                    (send-job-does-not-exist-response request job-name :update-job))
                (send-wrong-job-name-response request job-name :update-job)))
        (catch Exception e
            (send-job-invalid-metadata-response request :update-job "invalid or missing input"))))

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

(defn get-job-name-from-uri-or-params
    [request uri-prefix uri]
    (or (uri->job-name uri uri-prefix)
        (get (:params request) "name")))

(defn get-job
    [request uri]
    (let [job-name (get-job-name-from-uri-or-params request "/api/get_job/" uri)]
        (if job-name
            (if (test-job? job-name request)
                (if (results/job-exists? job-name)
                    (let [job-metadata (results/find-job-with-name job-name)]
                         (if job-metadata
                             (send-response job-metadata request)
                             (-> (create-error-response job-name (get commands :get-job) "Test results does not exist")
                                 (send-error-response request :not-found))))
                    (send-job-does-not-exist-response request job-name :get-job))
                (send-wrong-job-name-response request job-name :get-job))
            (send-job-not-specified-response request :get-job))))

(defn get-jobs
    [request]
    (let [params  (:params request)
          product (get params "product")
          version (get params "version")
          results (results/get-job-results-as-tree product version)]
        (send-response results request)))

(defn get-job-results
    [request uri]
    (let [job-name (get-job-name-from-uri-or-params request "/api/get_job_results/" uri)]
        (if job-name
            (if (test-job? job-name request)
                (if (results/job-exists? job-name)
                    (let [job-results (jenkins-api/read-job-results (config/get-jenkins-url request) job-name)]
                         (if job-results
                             (send-plain-response job-results)
                             (-> (create-error-response job-name "get_job_results" "can not read test results")
                                 (send-error-response request :not-found))))
                    (send-job-does-not-exist-response request job-name :get-job-results))
                (send-wrong-job-name-response request job-name :get-job-results))
            (send-job-not-specified-response request :get-job-results))))

(defn job-started-handler
    [request]
    (println "job-started")
    (send-response {:status "ok"} request))

(defn job-finished-handler
    [request]
    (println "job-finished")
    (send-response {:status "ok"} request))

(defn job-results
    [request]
    (println "job-results")
    (send-response {:status "ok"} request))

(defn unknown-call-handler
    "Function called for all unknown API calls."
    [request uri method]
    (let [response {:status :error
                    :error "Unknown API call"
                    :uri uri
                    :method method}]
        (send-error-response response request :bad-request)))

(defn get-output-format
    [request]
    (let [output-format (-> (:params request) (get "format"))]
        (condp = output-format
            "json" :json
            "xml"  :xml
            "csv"  :csv
            "edn"  :edn
            "text" :txt
            "txt"  :txt
                   :json)))

(defn mime-type
    [output-format]
    (case output-format
        :json  "application/json"
        :edn   "application/edn"
        :csv   "text/csv"
        :txt   "text/plain"
        :xml   "text/xml"))

(defn get-metadata
    [request]
    (let [params  (:params request)
          output-format (get-output-format request)
          mime-type (mime-type output-format)
          columns ["Job" "Product" "Version" "Book" "Total tags" "Tags without ID"]
          ;product (get params "product")
          ;version (get params "version")
          metadata (metadata-reader/get-metadata)
          results  (metadata-analyzer/select-results metadata)
          output   (metadata-exporter/export columns results output-format)]
        (-> (http-response/response output)
            (http-response/content-type mime-type))))

