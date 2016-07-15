(ns emender-jenkins.core)

(require '[clojure.tools.cli       :as cli])
 
(def cli-options
    ;; an option with a required argument
    [["-h"   "--help"                       "show help"                                                    :id :help]
     ["-p"   "--port   PORT"                "port number on which Stage Dashboard should accepts requests" :id :port]
     ["-j"   "--jenkins-url url"            "url to Jenkins, for example: http://10.20.30.40:8080/"        :id :jenkins-url]
     ["-t"   "--test-jobs-suffix suffix"    "test jobs suffix, for example 'test'"                         :id :test-jobs-suffix]
     ["-f"   "--fetch-only"                 "just start job fetcher once, then stop processing"            :id :fetch-only]
     ["-c"   "--config"                     "just show the actual configuration"                           :id :show-config]])

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

