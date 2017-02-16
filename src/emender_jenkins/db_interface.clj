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

(defn select-product-version-id
    [product-id version]
    (log/info "select-product-version-id" product-id version)
    (->
        (jdbc/query db-spec/emender-jenkins-db
           ["select id from product_versions where product_id=? and version=?" product-id version])
           first
           :id))

(defn insert-product-version
    [product-id version]
    (log/info "insert-product-version" product-id version)
    (jdbc/insert! db-spec/emender-jenkins-db
        :product_versions {:product_id product-id
                           :version    version}))

(defn select-product-version-or-insert
    [product-id version]
    (if-let [version-id (select-product-version-id product-id version)]
            version-id
            (do (insert-product-version product-id version)
                (select-product-version-id product-id version))))

(defn select-test-suite-id
    [test-suite-name]
    (log/info "select-test-suite-id" test-suite-name)
    (->
        (jdbc/query db-spec/emender-jenkins-db
           ["select id from test_suites where name=?" test-suite-name])
           first
           :id))

(defn insert-test-suite-name
    [test-suite-name]
    (log/info "insert-test-suite-name" test-suite-name)
    (jdbc/insert! db-spec/emender-jenkins-db
        :test_suites {:name test-suite-name}))

(defn select-test-suite-id-or-insert
    [test-suite-name]
    (if-let [test-suite-id (select-test-suite-id test-suite-name)]
            test-suite-id
            (do (insert-test-suite-name test-suite-name)
                (select-test-suite-id test-suite-name))))

(defn insert-test-waive
    [waive-data]
    (println waive-data)
)

