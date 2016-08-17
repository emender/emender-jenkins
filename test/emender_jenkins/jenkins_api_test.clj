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

(ns emender-jenkins.jenkins-api-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.jenkins-api :refer :all]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))


(deftest test-log-existence
    "Check that the emender-jenkins.jenkins-api/log definition exists."
    (testing "if the emender-jenkins.jenkins-api/log definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/log))))


(deftest test-get-command-existence
    "Check that the emender-jenkins.jenkins-api/get-command definition exists."
    (testing "if the emender-jenkins.jenkins-api/get-command definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/get-command))))


(deftest test-job-name->url-existence
    "Check that the emender-jenkins.jenkins-api/job-name->url definition exists."
    (testing "if the emender-jenkins.jenkins-api/job-name->url definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/job-name->url))))


(deftest test-update-jenkins-url-existence
    "Check that the emender-jenkins.jenkins-api/update-jenkins-url definition exists."
    (testing "if the emender-jenkins.jenkins-api/update-jenkins-url definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/update-jenkins-url))))


(deftest test-post-command-existence
    "Check that the emender-jenkins.jenkins-api/post-command definition exists."
    (testing "if the emender-jenkins.jenkins-api/post-command definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/post-command))))


(deftest test-read-list-of-all-jobs-existence
    "Check that the emender-jenkins.jenkins-api/read-list-of-all-jobs definition exists."
    (testing "if the emender-jenkins.jenkins-api/read-list-of-all-jobs definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/read-list-of-all-jobs))))


(deftest test-filter-test-jobs-existence
    "Check that the emender-jenkins.jenkins-api/filter-test-jobs definition exists."
    (testing "if the emender-jenkins.jenkins-api/filter-test-jobs definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/filter-test-jobs))))


(deftest test-read-list-of-test-jobs-existence
    "Check that the emender-jenkins.jenkins-api/read-list-of-test-jobs definition exists."
    (testing "if the emender-jenkins.jenkins-api/read-list-of-test-jobs definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/read-list-of-test-jobs))))


(deftest test-read-job-results-existence
    "Check that the emender-jenkins.jenkins-api/read-job-results definition exists."
    (testing "if the emender-jenkins.jenkins-api/read-job-results definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/read-job-results))))


(deftest test-read-file-from-artifact-existence
    "Check that the emender-jenkins.jenkins-api/read-file-from-artifact definition exists."
    (testing "if the emender-jenkins.jenkins-api/read-file-from-artifact definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/read-file-from-artifact))))


(deftest test-ok-response-structure-existence
    "Check that the emender-jenkins.jenkins-api/ok-response-structure definition exists."
    (testing "if the emender-jenkins.jenkins-api/ok-response-structure definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/ok-response-structure))))


(deftest test-error-response-structure-existence
    "Check that the emender-jenkins.jenkins-api/error-response-structure definition exists."
    (testing "if the emender-jenkins.jenkins-api/error-response-structure definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/error-response-structure))))


(deftest test-replace-placeholder-existence
    "Check that the emender-jenkins.jenkins-api/replace-placeholder definition exists."
    (testing "if the emender-jenkins.jenkins-api/replace-placeholder definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/replace-placeholder))))


(deftest test-update-template-existence
    "Check that the emender-jenkins.jenkins-api/update-template definition exists."
    (testing "if the emender-jenkins.jenkins-api/update-template definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/update-template))))


(deftest test-log-operation-existence
    "Check that the emender-jenkins.jenkins-api/log-operation definition exists."
    (testing "if the emender-jenkins.jenkins-api/log-operation definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/log-operation))))


(deftest test-send-configuration-xml-to-jenkins-existence
    "Check that the emender-jenkins.jenkins-api/send-configuration-xml-to-jenkins definition exists."
    (testing "if the emender-jenkins.jenkins-api/send-configuration-xml-to-jenkins definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/send-configuration-xml-to-jenkins))))


(deftest test-get-template-existence
    "Check that the emender-jenkins.jenkins-api/get-template definition exists."
    (testing "if the emender-jenkins.jenkins-api/get-template definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/get-template))))


(deftest test-create-job-existence
    "Check that the emender-jenkins.jenkins-api/create-job definition exists."
    (testing "if the emender-jenkins.jenkins-api/create-job definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/create-job))))


(deftest test-update-job-existence
    "Check that the emender-jenkins.jenkins-api/update-job definition exists."
    (testing "if the emender-jenkins.jenkins-api/update-job definition exists."
        (is (callable? 'emender-jenkins.jenkins-api/update-job))))

