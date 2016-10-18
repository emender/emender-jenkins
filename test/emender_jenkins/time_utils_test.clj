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

(ns emender-jenkins.time-utils-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.time-utils :refer :all]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))


(deftest test-minutes-to-seconds-existence
    "Check that the emender-jenkins.time-utils/minutes-to-seconds definition exists."
    (testing "if the emender-jenkins.time-utils/minutes-to-seconds definition exists."
        (is (callable? 'emender-jenkins.time-utils/minutes-to-seconds))))


(deftest test-seconds-to-ms-existence
    "Check that the emender-jenkins.time-utils/seconds-to-ms definition exists."
    (testing "if the emender-jenkins.time-utils/seconds-to-ms definition exists."
        (is (callable? 'emender-jenkins.time-utils/seconds-to-ms))))


(deftest test-compute-sleep-amount-existence
    "Check that the emender-jenkins.time-utils/compute-sleep-amount definition exists."
    (testing "if the emender-jenkins.time-utils/compute-sleep-amount definition exists."
        (is (callable? 'emender-jenkins.time-utils/compute-sleep-amount))))


(deftest test-get-formatted-time-existence
    "Check that the emender-jenkins.time-utils/get-formatted-time definition exists."
    (testing "if the emender-jenkins.time-utils/get-formatted-time definition exists."
        (is (callable? 'emender-jenkins.time-utils/get-formatted-time))))


;
; Function behaviours
;

(deftest test-minutes-to-seconds
    "Check the function emender-jenkins.time-utils/minutes-to-seconds."
    (testing "the function emender-jenkins.time-utils/minutes-to-seconds."
        (are [x y] (= x y)
               0 (minutes-to-seconds 0)
              60 (minutes-to-seconds 1)
             120 (minutes-to-seconds 2)
            3600 (minutes-to-seconds 60))))

(deftest test-seconds-to-ms
    "Check the function emender-jenkins.time-utils/seconds-to-ms."
    (testing "the function emender-jenkins.time-utils/seconds-to-ms."
        (are [x y] (= x y)
               0 (seconds-to-ms 0)
            1000 (seconds-to-ms 1)
            2000 (seconds-to-ms 2))))

(deftest test-compute-sleep-amount
    "Check the function emender-jenkins.time-utils/compute-sleep-amount."
    (testing "the function emender-jenkins.time-utils/compute-sleep-amount."
        (are [x y] (= x y)
               0 (compute-sleep-amount 0)
           60000 (compute-sleep-amount 1)
          120000 (compute-sleep-amount 2))))

