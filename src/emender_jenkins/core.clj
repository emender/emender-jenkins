(ns emender-jenkins.core)

(require '[ring.adapter.jetty      :as jetty])
(require '[ring.middleware.params  :as http-params])
(require '[ring.middleware.cookies :as cookies])
(require '[clojure.tools.cli       :as cli])
 
(require '[emender-jenkins.config       :as config])
(require '[emender-jenkins.server       :as server])
(require '[emender-jenkins.process-info :as process-info])

(def cli-options
    ;; an option with a required argument
    [["-h"   "--help"                       "show help"                                                    :id :help]
     ["-p"   "--port   PORT"                "port number on which Stage Dashboard should accepts requests" :id :port]
     ["-j"   "--jenkins-url url"            "url to Jenkins, for example: http://10.20.30.40:8080/"        :id :jenkins-url]
     ["-t"   "--test-jobs-suffix suffix"    "test jobs suffix, for example 'test'"                         :id :test-jobs-suffix]
     ["-f"   "--fetch-only"                 "just start job fetcher once, then stop processing"            :id :fetch-only]
     ["-c"   "--config"                     "just show the actual configuration"                           :id :show-config]])

(def access-control-allow-origins
    "Only following domains will be properly handled by HTTP access control."
    [
     #".*localhost"
     #".*localhost:[0-9]+"])

; we don't use this handler and the whole cors library due to error in it
;(defn cors-handler
;    "Handler for the HTTP access control (CORS)"
;    [handler]
;    ;(cors/wrap-cors handler
;    (cors/wrap-cors handler
;        :access-control-allow-origin access-control-allow-origins
;        :access-control-allow-methods [:get :put :post]))
;        ;:access-control-allow-methods [:get :put :post :delete :options :head]))

(defn get-cors-headers 
    [origin]
    {"Access-Control-Allow-Origin"      origin
     "Access-Control-Allow-Headers"     "Origin, Content-Type, Accept, Authorization"
     "Access-Control-Allow-Credentials" "true"
     "Access-Control-Allow-Methods"     "GET,POST,OPTIONS" })

(defn origin-allowed?
    "Returns truth value (not nil) when the origin matches at least one
     regexp declared in access-control-allow-origins sequence."
    [allowed-origins origin]
    (if origin
        (some #(re-matches %1 origin) access-control-allow-origins)))

(defn all-cors
    "Allow requests from all origins, not as good as cors-handler, but it does not work properly"
    [handler]
    (fn [request]
        (let [headers (get request :headers)
              origin  (get headers "origin")
              response (handler request)]
              (println "Origin: " origin)
              (if (origin-allowed? access-control-allow-origins origin)
                  (update-in response [:headers]
                   merge (get-cors-headers origin))
                   response))))

(def app
    "Ring application."
    (-> server/handler
        cookies/wrap-cookies
        http-params/wrap-params
        all-cors))
        ;cors-handler))

(defn start-server-on-regular-machine
    [port]
    (println "Starting the server at the port: " port)
    (jetty/run-jetty app {:port (read-string port)}))

(defn start-server-on-openshift
    [port host]
    (println "Starting the server on openshift at the port: " port " and host: " host)
    (jetty/run-jetty app {:port (read-string port) :host host}))

(defn start-server
    "Start server on specified port."
    [port openshift-port openshift-ip]
    (if (and openshift-ip openshift-port)
        (start-server-on-openshift openshift-port openshift-ip)
        (start-server-on-regular-machine port)))

(defn get-and-check-port
    "Accepts port number represented by string and throws AssertionError
     if port number is outside defined range."
    [port]
    (let [port-number (. Integer parseInt port)]
        (assert (> port-number 0))
        (assert (< port-number 65536))
        port))

(defn get-port
    "Returns specified port or default port if none is specified on the command line."
    [specified-port]
    (if (or (not specified-port) (not (string? specified-port)) (empty? specified-port))
        config/default-port
        (get-and-check-port specified-port)))

(defn show-help
    [summary]
    (println "Usage:")
    (println summary))

(defn print-environment-configuration
    []
    (let [properties (process-info/read-properties)]
        (println "Environment configuration")
        (println "=========================")
        (println "KeyStore:      " (System/getProperty "javax.net.ssl.keyStore"))
        (println "Trust Store:   " (System/getProperty "javax.net.ssl.trustStore"))
        (println "OpenShift IP:  " (System/getenv "OPENSHIFT_CLOJURE_HTTP_IP"))
        (println "OpenShift PORT:" (System/getenv "OPENSHIFT_CLOJURE_HTTP_PORT"))
        (println)
        (println "Properties")
        (println "----------")
        (doseq [property properties]
            (println (key property) (val property)))
        (println)))

(defn fetch-jobs-only
    [options]
    ;(config/load-all-configuration-options-if-necessary)
    (println "Generating data2.edn")
    ;(job-data-fetcher/try-to-fetch-and-export-data)
    (println "Done"))

(defn show-config
    [options]
    (let [port                (options :port)
          jenkins-url         (options :jenkins-url)
          test-jobs-suffix    (options :test-jobs-suffix)
          openshift-ip        (System/getenv "OPENSHIFT_CLOJURE_HTTP_IP")
          openshift-port      (System/getenv "OPENSHIFT_CLOJURE_HTTP_PORT")]
        (println "Finished")))

(defn run-app
    [options]
    (let [port                (options :port)
          jenkins-url         (options :jenkins-url)
          test-jobs-suffix    (options :test-jobs-suffix)
          openshift-ip        (System/getenv "OPENSHIFT_CLOJURE_HTTP_IP")
          openshift-port      (System/getenv "OPENSHIFT_CLOJURE_HTTP_PORT")]
       ;(config/load-all-configuration-options-if-necessary)
       ;(config/override-options-by-cli jenkins-url preview-jobs-suffix stage-jobs-suffix portal-jobs-suffix)
       ;(product-list/read-product-list-if-necessary config/load-product-list-from-database)
       ;(job-data-fetcher/read-books-info)
       ;(.start (Thread. job-data-fetcher/run-fetcher))
        (start-server (get-port port) openshift-port openshift-ip)))

(defn -main
    "Entry point to the titan server."
    [& args]
    (let [all-options         (cli/parse-opts args cli-options)
          options             (all-options :options)
          show-help?          (options :help)
          fetch-only?         (options :fetch-only)
          show-config?        (options :show-config)]
          (System/setProperty "javax.net.ssl.keyStore",   "keystore")
          (System/setProperty "javax.net.ssl.trustStore", "keystore")
          (print-environment-configuration)
          (cond show-help?   (show-help (:summary all-options))
                fetch-only?  (fetch-jobs-only options)
                show-config? (show-config options)
                :else        (run-app options))))

