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

(defn column->keyword
    [columns]
    (for [column columns]
    (-> (clojure.string/replace column " " "-")
        keyword)))

(defn column->json-key
    [columns]
    (for [column columns]
    (-> (clojure.string/replace column " " "_")
        keyword)))

(defn mix-columns-with-data
    [columns data function]
    (for [item data]
        (zipmap (function columns) item)))

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
    (-> (mix-columns-with-data columns data column->json-key)
        data->json))

(defn export2edn
    [columns data]
    (-> (mix-columns-with-data columns data column->keyword)
        data->edn))

(defn export
    [columns data output-format]
    (case output-format
        :json  (export2json columns data)
        :edn   (export2edn columns data)
        :csv   (export2csv columns data)
        :txt   nil
        :xml   nil))

