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

(ns emender-jenkins.job-data-fetcher
     "Module that contains the job fetching machinery that should be run in a separate thread.")

(require '[clojure.tools.logging   :as log])

(require '[emender-jenkins.results    :as results])
(require '[emender-jenkins.time-utils :as time-utils])

(def status (atom {
    :started-on    nil
    :finished-on   nil
    :last-duration nil}))

(defn fetch-data
    "Read job statuses and job results. Stores them in the data structure in result module."
    [configuration]
    (results/reload-all-results configuration))

(defn try-to-fetch-data
    "Fetch job statuses etc. from Jenkins."
    [configuration]
    (try
        (fetch-data configuration)
        (catch Exception e
            (log/error e "Exception in job fetcher"))))

(defn run-fetcher-one-iteration
    "One iteration of job data fetcher."
    [configuration fetch-data-function status-map]
    (log/info "Fetcher is reading job results")
    (let [start-time (System/currentTimeMillis)]
        (fetch-data-function configuration)
        (let [end-time (System/currentTimeMillis)
              duration (- end-time start-time)]
              (reset! status-map {
                  :started-on    (time-utils/get-formatted-time start-time)
                  :finished-on   (time-utils/get-formatted-time end-time)
                  :last-duration duration})
              (log/info "Done in " duration "ms "))))

(defn run-fetcher-in-a-loop
    "Run the fetcher periodically. The sleep amount should containg time delay
    in minutes."
    [sleep-amount configuration fetch-data-function status-map]
    (let [ms-to-sleep (time-utils/compute-sleep-amount sleep-amount)]
        (while true
            (do
                (run-fetcher-one-iteration configuration fetch-data-function status-map)
                (log/info "Sleeping for" sleep-amount " minutes")
                (Thread/sleep ms-to-sleep)))))

(defn run-fetcher
    "Run the endless fetcher loop."
    [configuration sleep-amount fetch-data-function status-map]
    (log/info "Fetcher started in its own thread, configured sleep amount: " sleep-amount)
    (run-fetcher-in-a-loop sleep-amount configuration fetch-data-function status-map))

(defn run-fetcher-in-thread
    "Run the fetcher (its loop) in a separate thread."
    [configuration]
    (when (-> configuration :fetcher :run-job-fetcher)
        (log/info "starting job fetcher")
        (let [sleep-amount (-> configuration :fetcher :job-fetcher-delay)]
            (log/debug "delay" sleep-amount)
            ; he have to use lambda here because we need to pass parameter into the run-fetcher function
            (.start (Thread. #(run-fetcher configuration sleep-amount fetch-data status))))))

