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

(ns emender-jenkins.rest-api-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.rest-api :refer :all]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))


(deftest test-read-request-body-existence
    "Check that the emender-jenkins.rest-api/read-request-body definition exists."
    (testing "if the emender-jenkins.rest-api/read-request-body definition exists."
        (is (callable? 'emender-jenkins.rest-api/read-request-body))))


(deftest test-body->results-existence
    "Check that the emender-jenkins.rest-api/body->results definition exists."
    (testing "if the emender-jenkins.rest-api/body->results definition exists."
        (is (callable? 'emender-jenkins.rest-api/body->results))))


(deftest test-body->job-info-existence
    "Check that the emender-jenkins.rest-api/body->job-info definition exists."
    (testing "if the emender-jenkins.rest-api/body->job-info definition exists."
        (is (callable? 'emender-jenkins.rest-api/body->job-info))))


(deftest test-get-job-name-existence
    "Check that the emender-jenkins.rest-api/get-job-name definition exists."
    (testing "if the emender-jenkins.rest-api/get-job-name definition exists."
        (is (callable? 'emender-jenkins.rest-api/get-job-name))))


(deftest test-get-job-name-from-body-existence
    "Check that the emender-jenkins.rest-api/get-job-name-from-body definition exists."
    (testing "if the emender-jenkins.rest-api/get-job-name-from-body definition exists."
        (is (callable? 'emender-jenkins.rest-api/get-job-name-from-body))))


(deftest test-send-response-existence
    "Check that the emender-jenkins.rest-api/send-response definition exists."
    (testing "if the emender-jenkins.rest-api/send-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/send-response))))


(deftest test-send-plain-response-existence
    "Check that the emender-jenkins.rest-api/send-plain-response definition exists."
    (testing "if the emender-jenkins.rest-api/send-plain-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/send-plain-response))))


(deftest test-info-handler-existence
    "Check that the emender-jenkins.rest-api/info-handler definition exists."
    (testing "if the emender-jenkins.rest-api/info-handler definition exists."
        (is (callable? 'emender-jenkins.rest-api/info-handler))))


(deftest test-configuration-handler-existence
    "Check that the emender-jenkins.rest-api/configuration-handler definition exists."
    (testing "if the emender-jenkins.rest-api/configuration-handler definition exists."
        (is (callable? 'emender-jenkins.rest-api/configuration-handler))))


