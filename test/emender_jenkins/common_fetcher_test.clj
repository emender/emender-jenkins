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

(ns emender-jenkins.common-fetcher-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.common-fetcher :refer :all]
            [emender-jenkins.results        :as results]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))



(deftest test-try-to-fetch-data-existence
    "Check that the emender-jenkins.common-fetcher/try-to-fetch-data definition exists."
    (testing "if the emender-jenkins.common-fetcher/try-to-fetch-data definition exists."
        (is (callable? 'emender-jenkins.common-fetcher/try-to-fetch-data))))


(deftest test-run-fetcher-in-a-loop-existence
    "Check that the emender-jenkins.common-fetcher/run-fetcher-in-a-loop definition exists."
    (testing "if the emender-jenkins.common-fetcher/run-fetcher-in-a-loop definition exists."
        (is (callable? 'emender-jenkins.common-fetcher/run-fetcher-in-a-loop))))


(deftest test-run-fetcher-existence
    "Check that the emender-jenkins.common-fetcher/run-fetcher definition exists."
    (testing "if the emender-jenkins.common-fetcher/run-fetcher definition exists."
        (is (callable? 'emender-jenkins.common-fetcher/run-fetcher))))


;
; Function behaviours
;

(deftest test-try-to-fetch-data
    "Checking the function try-to-fetch-data."
    (testing "the function try-to-fetch-data."
        (with-redefs [results/reload-all-results (fn [configuration] (throw (new Exception)))]
            (is (nil? (try-to-fetch-data nil (fn [configuration] (results/reload-all-results configuration)))))
        (with-redefs [results/reload-all-results (fn [configuration] :ok)]
            (is (= :ok (try-to-fetch-data nil (fn [configuration] (results/reload-all-results configuration)))))))))

(deftest test-run-fetcher-one-iteration
    "Checking the function run-fetcher-one-iteration."
    (testing "the function run-fetcher-one-iteration."
        (with-redefs [results/reload-all-results (fn [configuration] :ok)]
            (let [status (atom {})]
            (run-fetcher-one-iteration "Fetcher name" nil (fn [x]) status)
            (is (not (nil? (:started-on @status))))
            (is (not (nil? (:finished-on @status))))
            (is (not (nil? (:last-duration @status))))
            (is (re-matches #"\d\d\d\d-\d\d-\d\d \d\d:\d\d:\d\d" (:started-on  @status)))
            (is (re-matches #"\d\d\d\d-\d\d-\d\d \d\d:\d\d:\d\d" (:finished-on @status)))
            (is (>= (:last-duration @status) 0))))))

(deftest test-run-fetcher
    "Checking the function run-fetcher."
    (testing "the function run-fetcher."
        (with-redefs [run-fetcher-in-a-loop (fn [name sleep config func status] name)]
            (= "Fetcher name" (run-fetcher "Fetcher name" 0 nil nil nil)))))

(deftest test-run-fetcher-in-a-loop
    "Checking the function run-fetcher-in-a-loop."
    (testing "the function run-fetcher-in-a-loop."
        (let [counter (atom 10)]
        (with-redefs [run-fetcher-one-iteration (fn [name config func status]
                                                    (swap! counter dec)
                                                    (if (zero? @counter) (throw (RuntimeException. name))))]
            (is (thrown-with-msg? RuntimeException #"Fetcher name" (run-fetcher-in-a-loop "Fetcher name" 0 nil nil nil)))))))

