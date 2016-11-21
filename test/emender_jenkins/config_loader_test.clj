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

(ns emender-jenkins.config-loader-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.config-loader :refer :all]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))

;
; Tests for various function definitions
;

(deftest test-properties->map-existence
    "Check that the emender-jenkins.config-loader/properties->map definition exists."
    (testing "if the emender-jenkins.config-loader/properties->map definition exists."
        (is (callable? 'emender-jenkins.config-loader/properties->map))))


(deftest test-load-property-file-existence
    "Check that the emender-jenkins.config-loader/load-property-file definition exists."
    (testing "if the emender-jenkins.config-loader/load-property-file definition exists."
        (is (callable? 'emender-jenkins.config-loader/load-property-file))))


(deftest test-load-configuration-file-existence
    "Check that the emender-jenkins.config-loader/load-configuration-file definition exists."
    (testing "if the emender-jenkins.config-loader/load-configuration-file definition exists."
        (is (callable? 'emender-jenkins.config-loader/load-configuration-file))))

;
; Test for function behaviours
;

(deftest test-properties->map-1
    "Check the behaviour of function emender-jenkins/properties->map."
    (let [property (doto (new java.util.Properties)
                         (.setProperty "a" "A")
                         (.setProperty "b" "B"))]
        (is (= {:a "A" :b "B"} (properties->map property)))))

(deftest test-properties->map-2
    "Check the behaviour of function emender-jenkins/properties->map."
    (let [property (doto (new java.util.Properties)
                         (.setProperty "propertyA" "property_a")
                         (.setProperty "propertyB" "property_b"))]
        (is (= {:propertyA "property_a" :propertyB "property_b"} (properties->map property)))))

(deftest test-properties->map-3
    "Check the behaviour of function emender-jenkins/properties->map."
    (let [property (doto (new java.util.Properties)
                         (.setProperty "value1" "1")
                         (.setProperty "value2" "2"))]
        (is (= {:value1 "1" :value2 "2"} (properties->map property)))))

(deftest test-properties->map-4
    "Check the behaviour of function emender-jenkins/properties->map."
    (let [property (doto (new java.util.Properties)
                         (.setProperty "value1" "")
                         (.setProperty "" "2"))]
        (is (= {(keyword "") "2" :value1 ""} (properties->map property)))))

(deftest test-load-property-file
    "Check the behaviour of function emender-jenkins/load-property-file."
    (let [property (load-property-file "test/test1.properties")]
        (is (= {:value1 "value1" :value2 "42" :value.3 "3"} (properties->map property)))))

