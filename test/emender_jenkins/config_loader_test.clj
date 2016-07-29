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

(deftest test-parse-int-existence
    "Check that the emender-jenkins.config-loader/parse-int definition exists."
    (testing "if the emender-jenkins.config-loader/parse-int definition exists."
        (is (callable? 'emender-jenkins.config-loader/parse-int))))


(deftest test-parse-float-existence
    "Check that the emender-jenkins.config-loader/parse-float definition exists."
    (testing "if the emender-jenkins.config-loader/parse-float definition exists."
        (is (callable? 'emender-jenkins.config-loader/parse-float))))


(deftest test-parse-boolean-existence
    "Check that the emender-jenkins.config-loader/parse-boolean definition exists."
    (testing "if the emender-jenkins.config-loader/parse-boolean definition exists."
        (is (callable? 'emender-jenkins.config-loader/parse-boolean))))


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

(deftest test-parse-int-zero
    "Check the behaviour of function emender-jenkins.config/parse-int."
    (are [x y] (== x y)
        0 (parse-int "0")
        0 (parse-int "00")
        0 (parse-int "000")
        0 (parse-int "-0")
        0 (parse-int "-00")
        0 (parse-int "-000")))

(deftest test-parse-int-positive-int
    "Check the behaviour of function emender-jenkins.config/parse-int."
    (are [x y] (== x y)
        1          (parse-int "1")
        2          (parse-int "2")
        42         (parse-int "42")
        65535      (parse-int "65535")
        65536      (parse-int "65536")
        2147483646 (parse-int "2147483646")))

(deftest test-parse-int-negative-int
    "Check the behaviour of function emender-jenkins.config/parse-int."
    (are [x y] (== x y)
        -1          (parse-int "-1")
        -2          (parse-int "-2")
        -42         (parse-int "-42")
        -65535      (parse-int "-65535")
        -65536      (parse-int "-65536")
        -2147483647 (parse-int "-2147483647")))

