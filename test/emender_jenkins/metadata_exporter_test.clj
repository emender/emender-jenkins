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

(ns emender-jenkins.metadata-exporter-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.metadata-exporter :refer :all]))

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

(deftest test-column->keyword-existence
    "Check that the emender-jenkins.metadata-exporter/column->keyword definition exists."
    (testing "if the emender-jenkins.metadata-exporter/column->keyword definition exists."
        (is (callable? 'emender-jenkins.metadata-exporter/column->keyword))))


(deftest test-column->json-key-existence
    "Check that the emender-jenkins.metadata-exporter/column->json-key definition exists."
    (testing "if the emender-jenkins.metadata-exporter/column->json-key definition exists."
        (is (callable? 'emender-jenkins.metadata-exporter/column->json-key))))


(deftest test-column->xml-key-existence
    "Check that the emender-jenkins.metadata-exporter/column->xml-key definition exists."
    (testing "if the emender-jenkins.metadata-exporter/column->xml-key definition exists."
        (is (callable? 'emender-jenkins.metadata-exporter/column->xml-key))))


(deftest test-data->csv-existence
    "Check that the emender-jenkins.metadata-exporter/data->csv definition exists."
    (testing "if the emender-jenkins.metadata-exporter/data->csv definition exists."
        (is (callable? 'emender-jenkins.metadata-exporter/data->csv))))


(deftest test-data->txt-existence
    "Check that the emender-jenkins.metadata-exporter/data->txt definition exists."
    (testing "if the emender-jenkins.metadata-exporter/data->txt definition exists."
        (is (callable? 'emender-jenkins.metadata-exporter/data->txt))))


(deftest test-data->json-existence
    "Check that the emender-jenkins.metadata-exporter/data->json definition exists."
    (testing "if the emender-jenkins.metadata-exporter/data->json definition exists."
        (is (callable? 'emender-jenkins.metadata-exporter/data->json))))


(deftest test-data->edn-existence
    "Check that the emender-jenkins.metadata-exporter/data->edn definition exists."
    (testing "if the emender-jenkins.metadata-exporter/data->edn definition exists."
        (is (callable? 'emender-jenkins.metadata-exporter/data->edn))))


