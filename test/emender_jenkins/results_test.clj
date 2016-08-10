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

(ns emender-jenkins.results-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.results :refer :all]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))


(deftest test-render-edn-data-existence
    "Check that the emender-jenkins.results/render-edn-data definition exists."
    (testing "if the emender-jenkins.results/render-edn-data definition exists."
        (is (callable? 'emender-jenkins.results/render-edn-data))))


(deftest test-add-new-results-existence
    "Check that the emender-jenkins.results/add-new-results definition exists."
    (testing "if the emender-jenkins.results/add-new-results definition exists."
        (is (callable? 'emender-jenkins.results/add-new-results))))


(deftest test-store-results-existence
    "Check that the emender-jenkins.results/store-results definition exists."
    (testing "if the emender-jenkins.results/store-results definition exists."
        (is (callable? 'emender-jenkins.results/store-results))))


(deftest test-job-name->product-name-existence
    "Check that the emender-jenkins.results/job-name->product-name definition exists."
    (testing "if the emender-jenkins.results/job-name->product-name definition exists."
        (is (callable? 'emender-jenkins.results/job-name->product-name))))


(deftest test-job-name->version-existence
    "Check that the emender-jenkins.results/job-name->version definition exists."
    (testing "if the emender-jenkins.results/job-name->version definition exists."
        (is (callable? 'emender-jenkins.results/job-name->version))))


(deftest test-job-name->book-name-existence
    "Check that the emender-jenkins.results/job-name->book-name definition exists."
    (testing "if the emender-jenkins.results/job-name->book-name definition exists."
        (is (callable? 'emender-jenkins.results/job-name->book-name))))


(deftest test-job-name->environment-existence
    "Check that the emender-jenkins.results/job-name->environment definition exists."
    (testing "if the emender-jenkins.results/job-name->environment definition exists."
        (is (callable? 'emender-jenkins.results/job-name->environment))))


(deftest test-compute-job-status-existence
    "Check that the emender-jenkins.results/compute-job-status definition exists."
    (testing "if the emender-jenkins.results/compute-job-status definition exists."
        (is (callable? 'emender-jenkins.results/compute-job-status))))


(deftest test-compute-job-disabled-existence
    "Check that the emender-jenkins.results/compute-job-disabled definition exists."
    (testing "if the emender-jenkins.results/compute-job-disabled definition exists."
        (is (callable? 'emender-jenkins.results/compute-job-disabled))))


(deftest test-parse-int-existence
    "Check that the emender-jenkins.results/parse-int definition exists."
    (testing "if the emender-jenkins.results/parse-int definition exists."
        (is (callable? 'emender-jenkins.results/parse-int))))


(deftest test-parse-test-results-existence
    "Check that the emender-jenkins.results/parse-test-results definition exists."
    (testing "if the emender-jenkins.results/parse-test-results definition exists."
        (is (callable? 'emender-jenkins.results/parse-test-results))))


(deftest test-read-update-job-info-existence
    "Check that the emender-jenkins.results/read-update-job-info definition exists."
    (testing "if the emender-jenkins.results/read-update-job-info definition exists."
        (is (callable? 'emender-jenkins.results/read-update-job-info))))


(deftest test-reload-all-results-existence
    "Check that the emender-jenkins.results/reload-all-results definition exists."
    (testing "if the emender-jenkins.results/reload-all-results definition exists."
        (is (callable? 'emender-jenkins.results/reload-all-results))))


(deftest test-select-jobs-existence
    "Check that the emender-jenkins.results/select-jobs definition exists."
    (testing "if the emender-jenkins.results/select-jobs definition exists."
        (is (callable? 'emender-jenkins.results/select-jobs))))


(deftest test-read-job-results-existence
    "Check that the emender-jenkins.results/read-job-results definition exists."
    (testing "if the emender-jenkins.results/read-job-results definition exists."
        (is (callable? 'emender-jenkins.results/read-job-results))))


(deftest test-all-products-existence
    "Check that the emender-jenkins.results/all-products definition exists."
    (testing "if the emender-jenkins.results/all-products definition exists."
        (is (callable? 'emender-jenkins.results/all-products))))


