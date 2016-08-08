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


(deftest test-system-banners-existence
    "Check that the emender-jenkins.rest-api/system-banners definition exists."
    (testing "if the emender-jenkins.rest-api/system-banners definition exists."
        (is (callable? 'emender-jenkins.rest-api/system-banners))))


(deftest test-reload-job-list-existence
    "Check that the emender-jenkins.rest-api/reload-job-list definition exists."
    (testing "if the emender-jenkins.rest-api/reload-job-list definition exists."
        (is (callable? 'emender-jenkins.rest-api/reload-job-list))))


(deftest test-reload-all-results-existence
    "Check that the emender-jenkins.rest-api/reload-all-results definition exists."
    (testing "if the emender-jenkins.rest-api/reload-all-results definition exists."
        (is (callable? 'emender-jenkins.rest-api/reload-all-results))))


(deftest test-error-response-existence
    "Check that the emender-jenkins.rest-api/error-response definition exists."
    (testing "if the emender-jenkins.rest-api/error-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/error-response))))


(deftest test-job-does-not-exist-response-existence
    "Check that the emender-jenkins.rest-api/job-does-not-exist-response definition exists."
    (testing "if the emender-jenkins.rest-api/job-does-not-exist-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/job-does-not-exist-response))))


(deftest test-job-already-exist-response-existence
    "Check that the emender-jenkins.rest-api/job-already-exist-response definition exists."
    (testing "if the emender-jenkins.rest-api/job-already-exist-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/job-already-exist-response))))


(deftest test-start-job-existence
    "Check that the emender-jenkins.rest-api/start-job definition exists."
    (testing "if the emender-jenkins.rest-api/start-job definition exists."
        (is (callable? 'emender-jenkins.rest-api/start-job))))


(deftest test-enable-job-existence
    "Check that the emender-jenkins.rest-api/enable-job definition exists."
    (testing "if the emender-jenkins.rest-api/enable-job definition exists."
        (is (callable? 'emender-jenkins.rest-api/enable-job))))


(deftest test-disable-job-existence
    "Check that the emender-jenkins.rest-api/disable-job definition exists."
    (testing "if the emender-jenkins.rest-api/disable-job definition exists."
        (is (callable? 'emender-jenkins.rest-api/disable-job))))


(deftest test-delete-job-existence
    "Check that the emender-jenkins.rest-api/delete-job definition exists."
    (testing "if the emender-jenkins.rest-api/delete-job definition exists."
        (is (callable? 'emender-jenkins.rest-api/delete-job))))


(deftest test-create-job-existence
    "Check that the emender-jenkins.rest-api/create-job definition exists."
    (testing "if the emender-jenkins.rest-api/create-job definition exists."
        (is (callable? 'emender-jenkins.rest-api/create-job))))


(deftest test-uri->job-name-existence
    "Check that the emender-jenkins.rest-api/uri->job-name definition exists."
    (testing "if the emender-jenkins.rest-api/uri->job-name definition exists."
        (is (callable? 'emender-jenkins.rest-api/uri->job-name))))


(deftest test-get-job-existence
    "Check that the emender-jenkins.rest-api/get-job definition exists."
    (testing "if the emender-jenkins.rest-api/get-job definition exists."
        (is (callable? 'emender-jenkins.rest-api/get-job))))


(deftest test-update-job-existence
    "Check that the emender-jenkins.rest-api/update-job definition exists."
    (testing "if the emender-jenkins.rest-api/update-job definition exists."
        (is (callable? 'emender-jenkins.rest-api/update-job))))


(deftest test-get-jobs-existence
    "Check that the emender-jenkins.rest-api/get-jobs definition exists."
    (testing "if the emender-jenkins.rest-api/get-jobs definition exists."
        (is (callable? 'emender-jenkins.rest-api/get-jobs))))


(deftest test-get-job-results-existence
    "Check that the emender-jenkins.rest-api/get-job-results definition exists."
    (testing "if the emender-jenkins.rest-api/get-job-results definition exists."
        (is (callable? 'emender-jenkins.rest-api/get-job-results))))


(deftest test-job-started-handler-existence
    "Check that the emender-jenkins.rest-api/job-started-handler definition exists."
    (testing "if the emender-jenkins.rest-api/job-started-handler definition exists."
        (is (callable? 'emender-jenkins.rest-api/job-started-handler))))


(deftest test-job-finished-handler-existence
    "Check that the emender-jenkins.rest-api/job-finished-handler definition exists."
    (testing "if the emender-jenkins.rest-api/job-finished-handler definition exists."
        (is (callable? 'emender-jenkins.rest-api/job-finished-handler))))


(deftest test-job-results-existence
    "Check that the emender-jenkins.rest-api/job-results definition exists."
    (testing "if the emender-jenkins.rest-api/job-results definition exists."
        (is (callable? 'emender-jenkins.rest-api/job-results))))


(deftest test-unknown-call-handler-existence
    "Check that the emender-jenkins.rest-api/unknown-call-handler definition exists."
    (testing "if the emender-jenkins.rest-api/unknown-call-handler definition exists."
        (is (callable? 'emender-jenkins.rest-api/unknown-call-handler))))

