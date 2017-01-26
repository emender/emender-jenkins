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

(ns emender-jenkins.build-queue-fetcher
     "Module that contains the build queue fetching machinery that should be run in a separate thread.")

(require '[clojure.tools.logging   :as log])

(require '[emender-jenkins.time-utils :as time-utils])

(def started-on    (atom nil))
(def finished-on   (atom nil))
(def last-duration (atom nil))

(defn fetch-data
    "Read job statuses and job results. Stores them in the data structure in result module."
    [configuration]
    (println "***"))

(defn try-to-fetch-data
    "Fetch job statuses etc. from Jenkins."
    [configuration]
    (try
        (fetch-data configuration)
        (catch Exception e
            (log/error e "Exception in job fetcher"))))

(defn run-fetcher-one-iteration
    "One iteration of job data fetcher."
    [configuration]
    (log/info "Fetcher is reading job results")
    (let [start-time (System/currentTimeMillis)]
        (try-to-fetch-data configuration)
        (let [end-time (System/currentTimeMillis)
              duration (- end-time start-time)]
              (reset! started-on  (time-utils/get-formatted-time start-time))
              (reset! finished-on (time-utils/get-formatted-time end-time))
              (reset! last-duration    duration)
              (log/info "Done in " duration "ms "))))

(defn run-fetcher-in-a-loop
    "Run the fetcher periodically. The sleep amount should containg time delay
    in minutes."
    [sleep-amount configuration]
    (let [ms-to-sleep (time-utils/compute-sleep-amount sleep-amount)]
        (while true
            (do
                (run-fetcher-one-iteration configuration)
                (log/info "Sleeping for" sleep-amount " minutes")
                (Thread/sleep ms-to-sleep)))))

(defn run-fetcher
    "Run the endless fetcher loop."
    [configuration]
    (let [sleep-amount  (-> configuration :fetcher :build-queue-fetcher-delay)]
        (log/info "Fetcher started in its own thread, configured sleep amount: " sleep-amount)
        (run-fetcher-in-a-loop sleep-amount configuration)))

(defn run-fetcher-in-thread
    "Run the fetcher (its loop) in a separate thread."
    [configuration]
    (when (-> configuration :fetcher :run-build-queue-fetcher)
        (log/info "starting build queue fetcher")
        (log/debug "delay" (-> configuration :fetcher :build-queue-fetcher-delay))
        ; he have to use lambda here because we need to pass parameter into the run-fetcher function
        (.start (Thread. #(run-fetcher configuration)))))

