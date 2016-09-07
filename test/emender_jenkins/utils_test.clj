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

(ns emender-jenkins.utils-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.utils :refer :all]))

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

(deftest test-third-existence
    "Check that the clouseau.utils/third definition exists."
    (testing "if the clouseau.utils/third definition exists."
        (is (callable? 'clouseau.utils/third))))


(deftest test-substring-existence
    "Check that the clouseau.utils/substring definition exists."
    (testing "if the clouseau.utils/substring definition exists."
        (is (callable? 'clouseau.utils/substring))))


(deftest test-startsWith-existence
    "Check that the clouseau.utils/startsWith definition exists."
    (testing "if the clouseau.utils/startsWith definition exists."
        (is (callable? 'clouseau.utils/startsWith))))


(deftest test-endsWith-existence
    "Check that the clouseau.utils/endsWith definition exists."
    (testing "if the clouseau.utils/endsWith definition exists."
        (is (callable? 'clouseau.utils/endsWith))))


(deftest test-contains-existence
    "Check that the clouseau.utils/contains definition exists."
    (testing "if the clouseau.utils/contains definition exists."
        (is (callable? 'clouseau.utils/contains))))


