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

(ns emender-jenkins.core-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.core :refer :all]))

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

(deftest test-get-cors-headers-existence
    "Check that the emender-jenkins.core/get-cors-headers definition exists."
    (testing "if the emender-jenkins.core/get-cors-headers definition exists."
        (is (callable? 'emender-jenkins.core/get-cors-headers))))


(deftest test-origin-allowed?-existence
    "Check that the emender-jenkins.core/origin-allowed? definition exists."
    (testing "if the emender-jenkins.core/origin-allowed? definition exists."
        (is (callable? 'emender-jenkins.core/origin-allowed?))))


(deftest test-all-cors-existence
    "Check that the emender-jenkins.core/all-cors definition exists."
    (testing "if the emender-jenkins.core/all-cors definition exists."
        (is (callable? 'emender-jenkins.core/all-cors))))


(deftest test-start-server-on-regular-machine-existence
    "Check that the emender-jenkins.core/start-server-on-regular-machine definition exists."
    (testing "if the emender-jenkins.core/start-server-on-regular-machine definition exists."
        (is (callable? 'emender-jenkins.core/start-server-on-regular-machine))))


(deftest test-start-server-on-openshift-existence
    "Check that the emender-jenkins.core/start-server-on-openshift definition exists."
    (testing "if the emender-jenkins.core/start-server-on-openshift definition exists."
        (is (callable? 'emender-jenkins.core/start-server-on-openshift))))


(deftest test-start-server-existence
    "Check that the emender-jenkins.core/start-server definition exists."
    (testing "if the emender-jenkins.core/start-server definition exists."
        (is (callable? 'emender-jenkins.core/start-server))))


(deftest test-get-and-check-port-existence
    "Check that the emender-jenkins.core/get-and-check-port definition exists."
    (testing "if the emender-jenkins.core/get-and-check-port definition exists."
        (is (callable? 'emender-jenkins.core/get-and-check-port))))


(deftest test-get-port-existence
    "Check that the emender-jenkins.core/get-port definition exists."
    (testing "if the emender-jenkins.core/get-port definition exists."
        (is (callable? 'emender-jenkins.core/get-port))))


(deftest test--main-existence
    "Check that the emender-jenkins.core/-main definition exists."
    (testing "if the emender-jenkins.core/-main definition exists."
        (is (callable? 'emender-jenkins.core/-main))))

