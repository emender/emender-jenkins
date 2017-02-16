;
;  (C) Copyright 2017  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns emender-jenkins.db-interface
    "Namespace that contains interface to the database.")

(require '[clojure.java.jdbc       :as jdbc])

(require '[clojure.tools.logging   :as log])

(require '[emender-jenkins.db-spec :as db-spec])

(defn select-product-id
    [product-name]
    (log/info "select-product-id" product-name)
    (->
        (jdbc/query db-spec/emender-jenkins-db
           ["select id from product_names where name=?" product-name])
           first
           :id))

(defn insert-product-name
    [product-name]
    (log/info "insert-product-name" product-name)
    (jdbc/insert! db-spec/emender-jenkins-db
        :product_names {:name product-name}))

(defn select-product-id-or-insert
    [product-name]
    (if-let [product-id (select-product-id product-name)]
            product-id
            (do (insert-product-name product-name)
                (select-product-id product-name))))

(defn insert-test-waive
    [waive-data]
    (println waive-data)
)

