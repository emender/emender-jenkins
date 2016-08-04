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


