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
            [emender-jenkins.metadata-analyzer :refer :all]))

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

(deftest test-data->csv-existence
    "Check that the emender-jenkins.metadata-analyzer/data->csv definition exists."
    (testing "if the emender-jenkins.metadata-analyzer/data->csv definition exists."
        (is (callable? 'emender-jenkins.metadata-analyzer/data->csv))))


(deftest test-select-results-existence
    "Check that the emender-jenkins.metadata-analyzer/select-results definition exists."
    (testing "if the emender-jenkins.metadata-analyzer/select-results definition exists."
        (is (callable? 'emender-jenkins.metadata-analyzer/select-results))))

