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

(ns emender-jenkins.server)

(require '[clojure.pprint           :as pprint])
(require '[ring.util.response       :as http-response])

(require '[emender-jenkins.config   :as config])
(require '[emender-jenkins.rest-api :as rest-api])

(defn return-file
    [file-name content-type]
    (http-response/file-response file-name {:root "www"}))

(defn render-front-page
    "Create front page."
    [request]
    (return-file "index.html" "text/html"))

(defn render-error-page
    "Create error page."
    [request]
    (return-file "error.html" "text/html"))

(defn get-hostname
    []
    (.. java.net.InetAddress getLocalHost getHostName))

(defn get-api-command
    "Retrieve the actual command from the API call."
    [request uri]
    (if uri
        (re-find #"/[^/]*" (subs uri (count (config/get-api-prefix request))))))

(defn api-call-handler
    [request uri method]
    (if (= uri "/api")
        (rest-api/info-handler request (get-hostname))
        (condp = [method (get-api-command request uri)]
            [:get  "/"]             (rest-api/info-handler request (get-hostname))
                                    (rest-api/unknown-call-handler uri method))))

(defn non-api-call-handler
    [request uri]
    (condp = uri
        "/"                      (render-front-page request)
                                 (render-error-page request)))

(defn handler
    "Handler that is called by Ring for all requests received from user(s)."
    [request]
    (if (config/verbose? request)
        (pprint/pprint request))
    (let [uri    (:uri request)
          method (:request-method request)]
         (if (.startsWith uri (config/get-api-prefix request))
             (api-call-handler     request uri method)
             (non-api-call-handler request uri))))

