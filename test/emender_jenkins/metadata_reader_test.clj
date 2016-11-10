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

(ns emender-jenkins.metadata-reader-test
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

(deftest test-parse-int-existence
    "Check that the emender-jenkins.metadata-reader/parse-int definition exists."
    (testing "if the emender-jenkins.metadata-reader/parse-int definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/parse-int))))


(deftest test-parse-commiter-existence
    "Check that the emender-jenkins.metadata-reader/parse-commiter definition exists."
    (testing "if the emender-jenkins.metadata-reader/parse-commiter definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/parse-commiter))))


(deftest test-parse-commiters-existence
    "Check that the emender-jenkins.metadata-reader/parse-commiters definition exists."
    (testing "if the emender-jenkins.metadata-reader/parse-commiters definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/parse-commiters))))


(deftest test-count-total-commits-existence
    "Check that the emender-jenkins.metadata-reader/count-total-commits definition exists."
    (testing "if the emender-jenkins.metadata-reader/count-total-commits definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/count-total-commits))))


(deftest test-read-and-parse-list-of-commiters-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-list-of-commiters definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-list-of-commiters definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-list-of-commiters))))


(deftest test-read-and-parse-chunkable-tags-ids-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-chunkable-tags-ids definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-chunkable-tags-ids definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-chunkable-tags-ids))))


(deftest test-read-and-parse-docbook-versions-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-docbook-versions definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-docbook-versions definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-docbook-versions))))


(deftest test-find-value-on-line-existence
    "Check that the emender-jenkins.metadata-reader/find-value-on-line definition exists."
    (testing "if the emender-jenkins.metadata-reader/find-value-on-line definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/find-value-on-line))))


(deftest test-read-and-parse-other-parts-count-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-other-parts-count definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-other-parts-count definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-other-parts-count))))


(deftest test-read-and-parse-program-listing-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-program-listing definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-program-listing definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-program-listing))))


(deftest test-read-and-parse-tag-frequencies-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-tag-frequencies definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-tag-frequencies definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-tag-frequencies))))


(deftest test-read-and-parse-tags-with-conditions-count-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-tags-with-conditions-count definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-tags-with-conditions-count definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-tags-with-conditions-count))))


(deftest test-read-and-parse-tags-with-conditions-list-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-tags-with-conditions-list definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-tags-with-conditions-list definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-tags-with-conditions-list))))


(deftest test-parse-first-number-from-stream-existence
    "Check that the emender-jenkins.metadata-reader/parse-first-number-from-stream definition exists."
    (testing "if the emender-jenkins.metadata-reader/parse-first-number-from-stream definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/parse-first-number-from-stream))))


(deftest test-read-file-from-artifact-existence
    "Check that the emender-jenkins.metadata-reader/read-file-from-artifact definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-file-from-artifact definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-file-from-artifact))))


(deftest test-read-and-parse-first-number-from-jenkins-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-first-number-from-jenkins definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-first-number-from-jenkins definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-first-number-from-jenkins))))


(deftest test-read-and-parse-entities-count-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-entities-count definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-entities-count definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-entities-count))))


(deftest test-read-and-parse-entities-uniq-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-entities-uniq definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-entities-uniq definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-entities-uniq))))


(deftest test-read-and-parse-used-graphics-count-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-used-graphics-count definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-used-graphics-count definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-used-graphics-count))))


(deftest test-read-and-parse-word-count-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-word-count definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-word-count definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-word-count))))


(deftest test-read-and-parse-xincludes-count-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-xincludes-count definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-xincludes-count definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-xincludes-count))))


(deftest test-read-and-parse-zpage-count-existence
    "Check that the emender-jenkins.metadata-reader/read-and-parse-zpage-count definition exists."
    (testing "if the emender-jenkins.metadata-reader/read-and-parse-zpage-count definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/read-and-parse-zpage-count))))


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


(deftest test-metadata-filter-existence
    "Check that the emender-jenkins.metadata-reader/metadata-filter definition exists."
    (testing "if the emender-jenkins.metadata-reader/metadata-filter definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/metadata-filter))))


(deftest test-get-metadata-existence
    "Check that the emender-jenkins.metadata-reader/get-metadata definition exists."
    (testing "if the emender-jenkins.metadata-reader/get-metadata definition exists."
        (is (callable? 'emender-jenkins.metadata-reader/get-metadata))))

