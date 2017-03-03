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

(defn select-test-id
    [test-suite-id test-name]
    (log/info "select-test-id" test-suite-id test-name)
    (->
        (jdbc/query db-spec/emender-jenkins-db
           ["select id from tests where test_suite_id=? and name=?" test-suite-id test-name])
           first
           :id))

(defn insert-test-name
    [test-suite-id test-name]
    (log/info "insert-test-name" test-suite-id test-name)
    (jdbc/insert! db-spec/emender-jenkins-db
        :tests {:test_suite_id test-suite-id
                :name          test-name}))

(defn select-test-id-or-insert
    [test-suite-id test-name]
    (if-let [test-id (select-test-id test-suite-id test-name)]
            test-id
            (do (insert-test-name test-suite-id test-name)
                (select-test-id test-suite-id test-name))))

(defn select-guide-id
    [version-id guide-name]
    (log/info "select-guide-id" version-id guide-name)
    (->
        (jdbc/query db-spec/emender-jenkins-db
           ["select id from guides where prod_ver_id=? and name=?" version-id guide-name])
           first
           :id))

(defn insert-guide-name
    [version-id guide-name]
    (log/info "insert-guide-name" version-id guide-name)
    (jdbc/insert! db-spec/emender-jenkins-db
        :guides {:prod_ver_id version-id
                 :name   guide-name}))

(defn select-guide-id-or-insert
    [version-id guide-name]
    (if-let [guide-id (select-guide-id version-id guide-name)]
            guide-id
            (do (insert-guide-name version-id guide-name)
                (select-guide-id version-id guide-name))))

(defn insert-test-waive
    [waive-data]
    (let [product-id    (select-product-id-or-insert                 (:product    waive-data))
          version-id    (select-product-version-or-insert product-id (:version    waive-data))
          guide-id      (select-guide-id-or-insert        version-id (:guide      waive-data))
          test-suite-id (select-test-suite-id-or-insert              (:test_suite waive-data))
          test-id       (select-test-id-or-insert  test-suite-id     (:test_name  waive-data))]
         (jdbc/insert! db-spec/emender-jenkins-db
             :waives {:guide_id guide-id
                      :test_id  test-id
                      :cause    (:cause waive-data)
                      }
         ))
)

(defn read-waives
    [product version book test-case test-name]
    (jdbc/query db-spec/emender-jenkins-db
        ["
select product_names.name, product_versions.version, guides.name, test_suites.name, tests.name, waives.cause
    from waives
         join tests            on waives.test_id = tests.id
         join test_suites      on tests.test_suite_id = test_suites.id
         join guides           on waives.guide_id = guides.id
         join product_versions on  guides.prod_ver_id = product_versions.id
         join product_names    on product_versions.product_id = product_names.id
    where product_names.name = ?
      and product_versions.version = ?
      and guides.name = ?
      and test_suites.name = ?
      and tests.name       = ?" product version book test-case test-name]))

;(insert-test-waive {
;    :product    "Product name"
;    :version    "Version number"
;    :guide      "Guide name"
;    :test_suite "Test suite name"
;    :test_name  "Test name"
;    :cause      "Cause"
;})

