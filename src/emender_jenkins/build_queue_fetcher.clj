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

(require '[emender-jenkins.common-fetcher :as common-fetcher])

(def status (atom {
    :started-on    nil
    :finished-on   nil
    :last-duration nil}))

(defn fetch-data
    "Read job statuses and job results. Stores them in the data structure in result module."
    [configuration]
    (println "***"))

(defn run-fetcher-in-thread
    "Run the fetcher (its loop) in a separate thread."
    [configuration]
    (when (-> configuration :fetcher :run-build-queue-fetcher)
        (log/info "starting build queue fetcher")
        (let [sleep-amount (-> configuration :fetcher :build-queue-fetcher-delay)]
            (log/debug "delay" sleep-amount)
            ; he have to use lambda here because we need to pass parameter into the run-fetcher function
            (.start (Thread. #(common-fetcher/run-fetcher "Build queue fetcher" configuration sleep-amount fetch-data status))))))

