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

(ns emender-jenkins.process-info-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.process-info :refer :all]))

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

(deftest test-get-current-pid-existence
    "Check that the emender-jenkins.process-info/get-current-pid definition exists."
    (testing "if the emender-jenkins.process-info/get-current-pid definition exists."
        (is (callable? 'emender-jenkins.process-info/get-current-pid))))


(deftest test-read-properties-existence
    "Check that the emender-jenkins.process-info/read-properties definition exists."
    (testing "if the emender-jenkins.process-info/read-properties definition exists."
        (is (callable? 'emender-jenkins.process-info/read-properties))))

