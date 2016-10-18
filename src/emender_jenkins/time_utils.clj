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

(ns emender-jenkins.time-utils
     "Module that contains helper functions to work with time.")

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

(defn get-formatted-time
    "Returns formatted time."
    [^long ms]
    (let [sdf         (new java.text.SimpleDateFormat "yyyy-MM-dd HH:mm:ss")
          result-date (new java.util.Date ms)]
          (.format sdf result-date)))

