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

