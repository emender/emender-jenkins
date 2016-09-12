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

(defn column->xml-key
    [columns]
    (for [column columns]
    (-> (clojure.string/replace column " " "_")
        clojure.string/lower-case)))

(defn mix-columns-with-data
    [columns data function]
    (for [item data]
        (zipmap (function columns) item)))

(defn data->csv
    "Convert/format any data to CSV format."
    [data]
    (with-out-str
        (csv/write-csv *out* data)))

(defn data->txt
    "Convert/format any data to the plain text format. Items are separated by tabs."
    [data]
    (clojure.string/join "\n"
        (for [item data]
            (clojure.string/join "\t" item))))

(defn data->json
    "Convert/format any data to JSON format."
    [products]
    (json/write-str products))

(defn data->edn
    "Convert/format any data to EDN format."
    [data]
    (with-out-str
        (clojure.pprint/pprint data)))

(defn flat-file-export
    [columns data export-function]
    (export-function (cons columns data)))

(defn tree-file-export
    [columns data export-function column-rename-function]
    (-> (mix-columns-with-data columns data column-rename-function)
        export-function))

(defn export2xml
    [columns data]
    (with-out-str
        (xml/emit {:tag "jobs" :content
            (for [item data]
                {:tag :job-metadata
                 :attrs   {:name (first item)}
                 :content
                     (for [z (zipmap (column->xml-key columns) item)]
                         {:tag (key z) :content [(str (val z))]}
                         )})})))

(defn export
    [columns data output-format]
    (case output-format
        :json  (tree-file-export columns data data->json column->json-key)
        :edn   (tree-file-export columns data data->edn  column->keyword)
        :csv   (flat-file-export columns data data->csv)
        :txt   (flat-file-export columns data data->txt)
        :xml   (export2xml columns data)))

