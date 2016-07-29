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

(ns emender-jenkins.job-data-fetcher
     "Module that contains the job fetching machinery that should be run in a separate thread.")

(require '[emender-jenkins.results :as results])

(def started-on    (atom nil))
(def finished-on   (atom nil))
(def last-duration (atom nil))

(defn minutes-to-seconds
    "Convert minutes to seconds."
    [minutes]
    (* minutes 60))

(defn seconds-to-ms
    "Convert seconds to milliseconds."
    [seconds]
    (* seconds 1000))

(defn compute-sleep-amount
    "Convert sleep amount specified in minutes into millisesonds."
    [minutes]
    (-> minutes
        minutes-to-seconds
        seconds-to-ms))

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
            (println "*** Exception in fetcher:" (.getMessage e)))))

;TODO put it into the proper module."

(defn get-formatted-time
    "Returns formatted time."
    [ms]
    (let [sdf         (new java.text.SimpleDateFormat "yyyy-MM-dd HH:mm:ss")
          result-date (new java.util.Date ms)]
          (.format sdf result-date)))

(defn run-fetcher-in-a-loop
    "Run the fetcher periodically. The sleep amount should containg time delay
    in minutes."
    [sleep-amount configuration]
    (let [ms-to-sleep (compute-sleep-amount sleep-amount)]
        (while true
            (do
                (println "[Fetcher] reading job results")
                (let [start-time (System/currentTimeMillis)]
                    (try-to-fetch-data configuration)
                    (let [end-time (System/currentTimeMillis)
                          duration (- end-time start-time)]
                          (reset! started-on  (get-formatted-time start-time))
                          (reset! finished-on (get-formatted-time end-time))
                          (reset! last-duration    duration)
                          (println "Done in " duration "ms , sleeping for" sleep-amount " minutes")))
                (Thread/sleep ms-to-sleep)))))

(defn run-fetcher
    "Run the endless fetcher loop."
    [configuration]
    (let [sleep-amount  (-> configuration :fetcher :delay)]
        (println "Fetcher started in its own thread, configured sleep amount: " sleep-amount)
        (run-fetcher-in-a-loop (-> configuration :fetcher :delay) configuration)))

(defn run-fetcher-in-thread
    "Run the fetcher (its loop) in a separate thread."
    [configuration]
    ; he have to use lambda here because we need to pass parameter into the run-fetcher function
    (.start (Thread. #(run-fetcher configuration))))

