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
            [emender-jenkins.job-data-fetcher :refer :all]
            [emender-jenkins.results          :as results]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))



(deftest test-fetch-data-existence
    "Check that the emender-jenkins.job-data-fetcher/fetch-data definition exists."
    (testing "if the emender-jenkins.job-data-fetcher/fetch-data definition exists."
        (is (callable? 'emender-jenkins.job-data-fetcher/fetch-data))))


(deftest test-try-to-fetch-data-existence
    "Check that the emender-jenkins.job-data-fetcher/try-to-fetch-data definition exists."
    (testing "if the emender-jenkins.job-data-fetcher/try-to-fetch-data definition exists."
        (is (callable? 'emender-jenkins.job-data-fetcher/try-to-fetch-data))))


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

(deftest test-fetch-data
    "Checking the function fetch-data."
    (testing "the function fetch-data."
        (with-redefs [results/reload-all-results (fn [configuration] :ok)]
            (is (= :ok (fetch-data nil))))))

(deftest test-try-to-fetch-data
    "Checking the function try-to-fetch-data."
    (testing "the function try-to-fetch-data."
        (with-redefs [results/reload-all-results (fn [configuration] (throw (new Exception)))]
            (is (nil? (try-to-fetch-data nil)))
        (with-redefs [results/reload-all-results (fn [configuration] :ok)]
            (is (= :ok (try-to-fetch-data nil)))))))

(deftest test-run-fetcher-one-iteration
    "Checking the function run-fetcher-one-iteration."
    (testing "the function run-fetcher-one-iteration."
        (with-redefs [results/reload-all-results (fn [configuration] :ok)]
            (reset! started-on    nil)
            (reset! finished-on   nil)
            (reset! last-duration nil)
            (run-fetcher-one-iteration nil)
            (is (not (nil? @started-on)))
            (is (not (nil? @finished-on)))
            (is (not (nil? @last-duration)))
            (is (re-matches #"\d\d\d\d-\d\d-\d\d \d\d:\d\d:\d\d" @started-on))
            (is (re-matches #"\d\d\d\d-\d\d-\d\d \d\d:\d\d:\d\d" @finished-on))
            (is (>= @last-duration 0)))))

