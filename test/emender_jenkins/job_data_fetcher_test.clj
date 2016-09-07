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

(ns emender-jenkins.job-data-fetcher-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.job-data-fetcher :refer :all]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))


(deftest test-minutes-to-seconds-existence
    "Check that the emender-jenkins.job-data-fetcher/minutes-to-seconds definition exists."
    (testing "if the emender-jenkins.job-data-fetcher/minutes-to-seconds definition exists."
        (is (callable? 'emender-jenkins.job-data-fetcher/minutes-to-seconds))))


(deftest test-seconds-to-ms-existence
    "Check that the emender-jenkins.job-data-fetcher/seconds-to-ms definition exists."
    (testing "if the emender-jenkins.job-data-fetcher/seconds-to-ms definition exists."
        (is (callable? 'emender-jenkins.job-data-fetcher/seconds-to-ms))))


(deftest test-compute-sleep-amount-existence
    "Check that the emender-jenkins.job-data-fetcher/compute-sleep-amount definition exists."
    (testing "if the emender-jenkins.job-data-fetcher/compute-sleep-amount definition exists."
        (is (callable? 'emender-jenkins.job-data-fetcher/compute-sleep-amount))))


(deftest test-fetch-data-existence
    "Check that the emender-jenkins.job-data-fetcher/fetch-data definition exists."
    (testing "if the emender-jenkins.job-data-fetcher/fetch-data definition exists."
        (is (callable? 'emender-jenkins.job-data-fetcher/fetch-data))))


(deftest test-try-to-fetch-data-existence
    "Check that the emender-jenkins.job-data-fetcher/try-to-fetch-data definition exists."
    (testing "if the emender-jenkins.job-data-fetcher/try-to-fetch-data definition exists."
        (is (callable? 'emender-jenkins.job-data-fetcher/try-to-fetch-data))))


(deftest test-get-formatted-time-existence
    "Check that the emender-jenkins.job-data-fetcher/get-formatted-time definition exists."
    (testing "if the emender-jenkins.job-data-fetcher/get-formatted-time definition exists."
        (is (callable? 'emender-jenkins.job-data-fetcher/get-formatted-time))))


(deftest test-run-fetcher-in-a-loop-existence
    "Check that the emender-jenkins.job-data-fetcher/run-fetcher-in-a-loop definition exists."
    (testing "if the emender-jenkins.job-data-fetcher/run-fetcher-in-a-loop definition exists."
        (is (callable? 'emender-jenkins.job-data-fetcher/run-fetcher-in-a-loop))))


(deftest test-run-fetcher-existence
    "Check that the emender-jenkins.job-data-fetcher/run-fetcher definition exists."
    (testing "if the emender-jenkins.job-data-fetcher/run-fetcher definition exists."
        (is (callable? 'emender-jenkins.job-data-fetcher/run-fetcher))))


(deftest test-run-fetcher-in-thread-existence
    "Check that the emender-jenkins.job-data-fetcher/run-fetcher-in-thread definition exists."
    (testing "if the emender-jenkins.job-data-fetcher/run-fetcher-in-thread definition exists."
        (is (callable? 'emender-jenkins.job-data-fetcher/run-fetcher-in-thread))))

;
; Function behaviours
;

(deftest test-minutes-to-seconds
    "Check the function emender-jenkins.job-data-fetcher/minutes-to-seconds."
    (testing "the function emender-jenkins.job-data-fetcher/minutes-to-seconds."
        (are [x y] (= x y)
               0 (minutes-to-seconds 0)
              60 (minutes-to-seconds 1)
             120 (minutes-to-seconds 2)
            3600 (minutes-to-seconds 60))))

(deftest test-seconds-to-ms
    "Check the function emender-jenkins.job-data-fetcher/seconds-to-ms."
    (testing "the function emender-jenkins.job-data-fetcher/seconds-to-ms."
        (are [x y] (= x y)
               0 (seconds-to-ms 0)
            1000 (seconds-to-ms 1)
            2000 (seconds-to-ms 2))))

(deftest test-compute-sleep-amount
    "Check the function emender-jenkins.job-data-fetcher/compute-sleep-amount."
    (testing "the function emender-jenkins.job-data-fetcher/compute-sleep-amount."
        (are [x y] (= x y)
               0 (compute-sleep-amount 0)
           60000 (compute-sleep-amount 1)
          120000 (compute-sleep-amount 2))))

