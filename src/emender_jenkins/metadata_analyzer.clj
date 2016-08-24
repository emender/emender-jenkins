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

(ns emender-jenkins.metadata-analyzer)

(require '[clojure.data.csv      :as csv])

(defn data->csv
    "Convert/format any data to CSV format."
    [data]
    (with-out-str
        (csv/write-csv *out* data)))

(defn select-results
    [metadata]
    (data->csv (cons ["Job" "Product" "Version" "Book" "Total tags" "Tags without ID"]
                     (for [job-result metadata]
                         [(:job-name job-result)
                          (:product  job-result)
                          (:version  job-result)
                          (:book     job-result)
                          (-> (:chunkable-tags-ids job-result)
                              :total)
                          (-> (:chunkable-tags-ids job-result)
                              :missing)]))))

