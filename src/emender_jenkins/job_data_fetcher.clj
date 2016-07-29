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

(ns emender-jenkins.job-data-fetcher)

(require '[emender-jenkins.results :as results])

(def started-on    (atom nil))
(def finished-on   (atom nil))
(def last-duration (atom nil))

(defn minutes-to-seconds
    "Converts minutes to seconds."
    [minutes]
    (* minutes 60))

(defn seconds-to-ms
    "Converts seconds to milliseconds."
    [seconds]
    (* seconds 1000))

(defn compute-sleep-amount
    [minutes]
    (-> minutes
        minutes-to-seconds
        seconds-to-ms))

(defn fetch-data
    "Dynamically creates file containing EDN data about books."
    [configuration]
    (results/reload-all-results configuration))

(defn try-to-fetch-data
    [configuration]
    (try
        (fetch-data configuration)
        (catch Exception e
            (println "*** Exception in fetcher:" (.getMessage e)))))

(defn get-formatted-time
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
    [configuration]
    (.start (Thread. #(run-fetcher configuration))))

