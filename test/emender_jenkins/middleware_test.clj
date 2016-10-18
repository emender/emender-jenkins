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
            [emender-jenkins.middleware :refer :all]))

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

(deftest test-inject-configuration-existence
    "Check that the emender-jenkins.middleware/inject-configuration definition exists."
    (testing "if the emender-jenkins.middleware/inject-configuration definition exists."
        (is (callable? 'emender-jenkins.middleware/inject-configuration))))

;
; Tests for function behaviours.
;

(deftest test-inject-confuguration-1
    "Check the behaviour of function emender-jenkins.middleware/inject-configuration."
    (testing "The function emender-jenkins.middleware/inject-configuration."
        (let [function (inject-configuration (fn [x] x) :cfg)]
            (are [x y] (= x y)
                {:configuration :cfg} (function nil)
                {:configuration :cfg} (function {})
                {:configuration :cfg :foo :bar} (function {:foo :bar})))))

