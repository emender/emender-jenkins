;
;  (C) Copyright 2016, 2017  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns emender-jenkins.config
    "Module that contains all functions that are required to load configuration
     from the provided INI file.")

(require '[clojure.pprint :as pprint])
(require '[clojure.tools.logging :as log])

(require '[emender-jenkins.utils         :as utils])
(require '[emender-jenkins.config-loader :as config-loader])

(def default-port
    "Default port used when no -p or --port CLI option is specified."
    "3000")

(defn update-configuration
    "Update selected items in the configuration structure."
    [configuration]
    (-> configuration
        (update-in [:config :verbose]                    utils/parse-boolean)
        (update-in [:config :pretty-print]               utils/parse-boolean)
        (update-in [:config :include-jenkins-reply]      utils/parse-boolean)
        (update-in [:config :verbose-show-configuration] utils/parse-boolean)
        (update-in [:fetcher :job-fetcher-delay]         utils/parse-int)
        (update-in [:fetcher :run-job-fetcher]           utils/parse-boolean)
        (update-in [:irc :port]                          utils/parse-int)
        (update-in [:irc :connect]                       utils/parse-boolean)))

(defn load-configuration-from-ini
    "Load configuration from the provided INI file and perform conversions
     on selected items from strings to numeric or Boolean values."
    [ini-file-name]
    (-> (config-loader/load-configuration-file ini-file-name)
        update-configuration))

(defn assoc-in-if-not-nil
    "Assoc new (updated) value into the configuration map, but only when
     new value exists. If value does not exist at all, the old value is kept."
    [input selector value]
    (if value
        (assoc-in input selector value)
        input))

(defn override-options-by-cli
    "Update configuration options read from the provided INI file by new values."
    [configuration jenkins-url test-jobs-suffix]
    (-> configuration
        (assoc-in-if-not-nil [:jenkins :jenkins-url]      jenkins-url)
        (assoc-in-if-not-nil [:jobs    :test-jobs-suffix] test-jobs-suffix)))

(defn override-runtime-params
    [configuration started-on started-system-ms]
    (-> configuration
        (assoc :started-on started-on)
        (assoc :started-ms started-system-ms)))

(defn print-configuration
    "Print actual configuration to the standard output."
    [configuration]
    (pprint/pprint configuration))

(defn get-api-prefix
    "Read prefix for API calls from the configuration passed via
     HTTP request structure (middleware can be used to pass config into it)."
    [request]
    (-> request :configuration :api :prefix))

(defn verbose?
    "Read verbose mode settings from the configuration passed via
     HTTP request structure (middleware can be used to pass config into it)."
    [request]
    (-> request :configuration :config :verbose))

(defn get-version
    "Read service version from the configuration passed via
     HTTP request structure (middleware can be used to pass config into it)."
    [request]
    (-> request :configuration :info :version))

(defn get-jenkins-url
    "Read Jenkins URL from the configuration passed via HTTP request."
    [request]
    (-> request :configuration :jenkins :jenkins-url))

(defn get-jenkins-auth
    "Read Jenkins auth string from the configuration passed via HTTP request."
    [request]
    (-> request :configuration :jenkins :jenkins-auth))

(defn get-job-list-url
    [request]
    (-> request :configuration :jenkins :jenkins-job-list-url))

(defn get-in-queue-view
    "Read name of view that contains list of builds added into queue."
    [request]
    (-> request :configuration :jenkins :in-queue-view))

(defn get-currently-building-view
    "Read name of view that contains list of builds that are currently builing."
    [request]
    (-> request :configuration :jenkins :currently-building-view))

(defn get-in-queue-url
    "URL for Jenkins REST API"
    [request]
    (-> request :configuration :jenkins :in-queue-url))

(defn pretty-print?
    "Read the pretty-print settings (it is used for JSON output etc.)"
    [request]
    (-> request :configuration :config :pretty-print))

(defn verbose-show-configuration?
    "Read the verbose-show-configuration settings."
    [request]
    (-> request :configuration :config :verbose-show-configuration))

(defn include-jenkins-reply?
    "Read the include-jenkins-reply settings."
    [request]
    (-> request :configuration :config :include-jenkins-reply))

(defn get-credentials-id
    "Read the Jenkins credentials ID."
    [request]
    (-> request :configuration :jenkins :credentials-id))

(defn get-test-jobs-prefix
    "Read prefix used in names of all test jobs."
    [request]
    (-> request :configuration :jobs :test-jobs-prefix))

(defn get-preview-test-jobs-suffix
    "Read suffix used in names of all test jobs on preview environment."
    [request]
    (-> request :configuration :jobs :preview-test-jobs-suffix))

(defn get-stage-test-jobs-suffix
    "Read suffix used in names of all test jobs on stage environment."
    [request]
    (-> request :configuration :jobs :stage-test-jobs-suffix))

(defn get-prod-test-jobs-suffix
    "Read suffix used in names of all test jobs on production environment."
    [request]
    (-> request :configuration :jobs :prod-test-jobs-suffix))

(defn get-started-on-str
    [request]
    (-> request :configuration :started-on))

(defn get-started-on-ms
    [request]
    (-> request :configuration :started-ms))

