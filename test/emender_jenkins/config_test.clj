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

(ns emender-jenkins.config-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.config :refer :all]))

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

(deftest test-update-configuration-existence
    "Check that the emender-jenkins.config/update-configuration definition exists."
    (testing "if the emender-jenkins.config/update-configuration definition exists."
        (is (callable? 'emender-jenkins.config/update-configuration))))


(deftest test-load-configuration-from-ini-existence
    "Check that the emender-jenkins.config/load-configuration-from-ini definition exists."
    (testing "if the emender-jenkins.config/load-configuration-from-ini definition exists."
        (is (callable? 'emender-jenkins.config/load-configuration-from-ini))))


(deftest test-assoc-in-if-not-nil-existence
    "Check that the emender-jenkins.config/assoc-in-if-not-nil definition exists."
    (testing "if the emender-jenkins.config/assoc-in-if-not-nil definition exists."
        (is (callable? 'emender-jenkins.config/assoc-in-if-not-nil))))


(deftest test-override-options-by-cli-existence
    "Check that the emender-jenkins.config/override-options-by-cli definition exists."
    (testing "if the emender-jenkins.config/override-options-by-cli definition exists."
        (is (callable? 'emender-jenkins.config/override-options-by-cli))))


(deftest test-print-configuration-existence
    "Check that the emender-jenkins.config/print-configuration definition exists."
    (testing "if the emender-jenkins.config/print-configuration definition exists."
        (is (callable? 'emender-jenkins.config/print-configuration))))


