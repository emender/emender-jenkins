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

(ns emender-jenkins.config
    "Module that contains all functions required to load configuration from the INI file.")

(require '[clojure.pprint :as pprint])

(require '[emender-jenkins.config-loader :as config-loader])

(def default-port
    "Default port used when no -p or --port CLI option is specified."
    "3000")

(defn update-configuration
    "Update selected items in the configuration structure."
    [configuration]
    (-> configuration
        (update-in [:config :verbose]      config-loader/parse-boolean)
        (update-in [:config :pretty-print] config-loader/parse-boolean)
        (update-in [:fetcher :delay]       config-loader/parse-int)))

(defn load-configuration-from-ini
    "Load configuration from the provided INI file and perform conversions on numeric and Boolean values."
    [ini-file-name]
    (-> (config-loader/load-configuration-file ini-file-name)
        update-configuration))

(defn assoc-in-if-not-nil
    "Assoc new (updated) value into the configuration map, but only when new value exists."
    [input selector value]
    (if value
        (assoc-in input selector value)
        input))

(defn override-options-by-cli
    "Update configuration options read form the INI file by new values."
    [configuration jenkins-url test-jobs-suffix]
    (-> configuration
        (assoc-in-if-not-nil [:jenkins :jenkins-url]      jenkins-url)
        (assoc-in-if-not-nil [:jobs    :test-jobs-suffix] test-jobs-suffix)))

(defn print-configuration
    "Print actual configuration to the output."
    [configuration]
    (pprint/pprint configuration))

(defn get-api-prefix
    "Read prefix for API calls from the configuration passed via HTTP request."
    [request]
    (-> request :configuration :api :prefix))

(defn verbose?
    "Read verbose mode settings from the configuration passed via HTTP request."
    [request]
    (-> request :configuration :config :verbose))

(defn get-version
    "Read service version from the configuration passed via HTTP request."
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

(defn pretty-print?
    "Read the pretty-print settings (it is used for JSON output etc.)"
    [request]
    (-> request :configuration :config :pretty-print))

