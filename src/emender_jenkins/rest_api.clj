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

(defn read-request-body
    [request]
    (file-utils/slurp- (:body request)))

(defn body->results
    [body]
    (json/read-str body))

(defn body->test-info
    [body]
    (json/read-str body :key-fn clojure.core/keyword))

(defn send-response
    [response]
    (-> (http-response/response (json/write-str response))
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
    [request]
    )

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
    )

(defn start-job
    [request]
    )

(defn enable-job
    [request]
    )

(defn disable-job
    [request]
    )

(defn get-job
    [request]
    )

(defn update-job
    [request]
    )

(defn get-jobs
    [request]
    )

(defn get-job-results
    [request]
    )

(defn job-started-handler
    [request]
    )

(defn job-finished-handler
    [request]
    )

(defn job-results
    [request]
    )


(defn unknown-call-handler
    [uri method]
    (let [response {:status :error
                    :error "Unknown API call"
                    :uri uri
                    :method method}]
        (send-response response)))

