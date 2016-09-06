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

(ns emender-jenkins.metadata-exporter
    "Module for exporting selected metadata into selected format.")

(require '[clojure.data.json :as json])
(require '[clojure.xml       :as xml])
(require '[clojure.data.csv  :as csv])

(defn data->csv
    "Convert/format any data to CSV format."
    [data]
    (with-out-str
        (csv/write-csv *out* data)))

(defn data->json
    "Convert/format any data to JSON format."
    [products]
    (json/write-str products))

(defn data->edn
    "Convert/format any data to EDN format."
    [data]
    (with-out-str
        (clojure.pprint/pprint data)))

(defn export2csv
    [columns data]
    (data->csv (cons columns data)))

(defn export2json
    [columns data]
    (data->json data))

(defn export2edn
    [columns data]
    (data->edn data))

(defn export
    [columns data output-format]
    (case output-format
        :json  (export2json columns data)
        :edn   (export2edn columns data)
        :csv   (export2csv columns data)
        :txt   nil
        :xml   nil))
