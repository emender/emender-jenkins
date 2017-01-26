;
;  (C) Copyright 2017  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns emender-jenkins.common-fetcher
     "Module that contains common functions used by any asynchronous data fetchers.")

(require '[clojure.tools.logging   :as log])

(require '[emender-jenkins.time-utils :as time-utils])

(defn try-to-fetch-data
    "Fetch job statuses etc. from Jenkins."
    [configuration fetch-data-function]
    (try
        (fetch-data-function configuration)
        (catch Exception e
            (log/error e "Exception in job fetcher"))))

(defn run-fetcher-one-iteration
    "One iteration of job data fetcher."
    [fetcher-name configuration fetch-data-function status-map]
    (log/info fetcher-name " is reading job results")
    (let [start-time (System/currentTimeMillis)]
        (fetch-data-function configuration)
        (let [end-time (System/currentTimeMillis)
              duration (- end-time start-time)]
              (reset! status-map {
                  :started-on    (time-utils/get-formatted-time start-time)
                  :finished-on   (time-utils/get-formatted-time end-time)
                  :last-duration duration})
              (log/info fetcher-name "done in " duration "ms "))))

(defn run-fetcher-in-a-loop
    "Run the fetcher periodically. The sleep amount should containg time delay
    in minutes."
    [fetcher-name sleep-amount configuration fetch-data-function status-map]
    (let [ms-to-sleep (time-utils/compute-sleep-amount sleep-amount)]
        (while true
            (do
                (run-fetcher-one-iteration fetcher-name configuration fetch-data-function status-map)
                (log/info fetcher-name "sleeping for" sleep-amount " minutes")
                (Thread/sleep ms-to-sleep)))))

(defn run-fetcher
    "Run the endless fetcher loop."
    [fetcher-name configuration sleep-amount fetch-data-function status-map]
    (log/info fetcher-name "started in its own thread, configured sleep amount: " sleep-amount)
    (run-fetcher-in-a-loop fetcher-name sleep-amount configuration fetch-data-function status-map))

