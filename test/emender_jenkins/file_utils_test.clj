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

(ns emender-jenkins.file-utils-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.file-utils :refer :all]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))


(deftest test-print-slurp-exception-existence
    "Check that the emender-jenkins.file-utils/print-slurp-exception definition exists."
    (testing "if the emender-jenkins.file-utils/print-slurp-exception definition exists."
        (is (callable? 'emender-jenkins.file-utils/print-slurp-exception))))


(deftest test-slurp--existence
    "Check that the emender-jenkins.file-utils/slurp- definition exists."
    (testing "if the emender-jenkins.file-utils/slurp- definition exists."
        (is (callable? 'emender-jenkins.file-utils/slurp-))))


(deftest test-silent-slurp-existence
    "Check that the emender-jenkins.file-utils/silent-slurp definition exists."
    (testing "if the emender-jenkins.file-utils/silent-slurp definition exists."
        (is (callable? 'emender-jenkins.file-utils/silent-slurp))))


(deftest test-new-file-existence
    "Check that the emender-jenkins.file-utils/new-file definition exists."
    (testing "if the emender-jenkins.file-utils/new-file definition exists."
        (is (callable? 'emender-jenkins.file-utils/new-file))))


(deftest test-make-temporary-log-file-name-existence
    "Check that the emender-jenkins.file-utils/make-temporary-log-file-name definition exists."
    (testing "if the emender-jenkins.file-utils/make-temporary-log-file-name definition exists."
        (is (callable? 'emender-jenkins.file-utils/make-temporary-log-file-name))))


(deftest test-make-temporary-directory-existence
    "Check that the emender-jenkins.file-utils/make-temporary-directory definition exists."
    (testing "if the emender-jenkins.file-utils/make-temporary-directory definition exists."
        (is (callable? 'emender-jenkins.file-utils/make-temporary-directory))))


(deftest test-remove-directory-existence
    "Check that the emender-jenkins.file-utils/remove-directory definition exists."
    (testing "if the emender-jenkins.file-utils/remove-directory definition exists."
        (is (callable? 'emender-jenkins.file-utils/remove-directory))))


(deftest test-move-file-existence
    "Check that the emender-jenkins.file-utils/move-file definition exists."
    (testing "if the emender-jenkins.file-utils/move-file definition exists."
        (is (callable? 'emender-jenkins.file-utils/move-file))))


(deftest test-mv-file-existence
    "Check that the emender-jenkins.file-utils/mv-file definition exists."
    (testing "if the emender-jenkins.file-utils/mv-file definition exists."
        (is (callable? 'emender-jenkins.file-utils/mv-file))))


(deftest test->abs-path-existence
    "Check that the emender-jenkins.file-utils/>abs-path definition exists."
    (testing "if the emender-jenkins.file-utils/>abs-path definition exists."
        (is (callable? 'emender-jenkins.file-utils/>abs-path))))

