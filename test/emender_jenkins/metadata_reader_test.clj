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

(ns emender-jenkins.middleware-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.metadata-reader :refer :all]))

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

(deftest test-read-and-parse-list-of-commiters-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-list-of-commiters definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-list-of-commiters definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-list-of-commiters))))


(deftest test-job-results->job-names-existence
    "Check that the emender-jenkins.metadata-reader/job-results->job-names definition exists."
    (testing "if the emender-jenkins.metadata-reader/job-results->job-names definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/job-results->job-names))))


(deftest test-load-and-parse-metadata-existence
    "Check that the emender-jenkins.metadata-reader/load-and-parse-metadata definition exists."
    (testing "if the emender-jenkins.metadata-reader/load-and-parse-metadata definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/load-and-parse-metadata))))


(deftest test-reload-tests-metadata-existence
    "Check that the emender-jenkins.metadata-reader/reload-tests-metadata definition exists."
    (testing "if the emender-jenkins.metadata-reader/reload-tests-metadata definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/reload-tests-metadata))))


(deftest test-metadata-count-existence
    "Check that the emender-jenkins.metadata-reader/metadata-count definition exists."
    (testing "if the emender-jenkins.metadata-reader/metadata-count definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/metadata-count))))

