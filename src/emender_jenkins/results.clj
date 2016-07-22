;
;  (C) Copyright 2016  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns emender-jenkins.results)

(require '[clojure.pprint :as pprint])

(require '[emender-jenkins.file-utils  :as file-utils])
(require '[emender-jenkins.jenkins-api :as jenkins-api])
(require '[emender-jenkins.config      :as config])

(def results (atom {}))

(defn render-edn-data
    "Render EDN data to be used for debugging purposes etc."
    [output-data pretty-print?]
    (if pretty-print?
        (with-out-str (pprint/pprint output-data))
        (with-out-str (println output-data))))

(defn add-new-results
    [job-name new-results]
    (swap! results assoc job-name new-results))

(defn store-results
    [pretty-print?]
    (let [edn-data (render-edn-data @results pretty-print?)]
        (spit "results2.edn" edn-data)
        ; rename files atomically (on the same filesystem)
        (file-utils/mv-file "results2.edn" "results.edn")))

(defn reload-all-results
    [configuration]
    (let [job-list (jenkins-api/read-list-of-all-jobs (-> configuration :jenkins :jenkins-url)
                                                      (-> configuration :jenkins :jenkins-job-list-url))]
    ))

