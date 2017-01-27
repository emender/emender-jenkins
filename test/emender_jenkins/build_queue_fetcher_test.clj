;
;  (C) Copyright 2017  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns emender-jenkins.build-queue-fetcher-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.build-queue-fetcher :refer :all]
            [emender-jenkins.results             :as results]
            [clj-jenkins-api.jenkins-api         :as jenkins-api]
            [emender-jenkins.common-fetcher      :as common-fetcher]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))



(deftest test-fetch-data-existence
    "Check that the emender-jenkins.build-queue-fetcher/fetch-data definition exists."
    (testing "if the emender-jenkins.build-queue-fetcher/fetch-data definition exists."
        (is (callable? 'emender-jenkins.build-queue-fetcher/fetch-data))))

(deftest test-run-fetcher-in-thread-existence
    "Check that the emender-jenkins.build-queue-fetcher/run-fetcher-in-thread definition exists."
    (testing "if the emender-jenkins.build-queue-fetcher/run-fetcher-in-thread definition exists."
        (is (callable? 'emender-jenkins.build-queue-fetcher/run-fetcher-in-thread))))


;
; Function behaviours
;

(deftest test-fetch-data
    "Check the function emender-jenkins.build-queue-fetcher/fetch-data."
    (testing "the function emender-jenkins.build-queue-fetcher/fetch-data."
        (let [configuration    {:jenkins {:currently-building-view "Building"
                                          :jenkins-url "http://10.20.30.40:8080/"}}]
        (with-redefs [jenkins-api/get-command (fn [url] nil)]
            (fetch-data configuration)
            (is (nil? (results/get-currently-building-jobs)))
            (is (nil? (results/get-jobs-in-queue)))
            (is (nil? (results/get-running-jobs)))))))

(deftest test-run-fetcher-in-thread
    "Check the function emender-jenkins.build-queue-fetcher/run-fetcher-in-thread"
    (testing "the function emender-jenkins.build-queue-fetcher/run-fetcher-in-thread"
        (let [started (atom nil)]
        (with-redefs [common-fetcher/run-fetcher (fn [name config sleep-amount func status] (reset! started true))]
            (run-fetcher-in-thread {:fetcher {:run-build-queue-fetcher false}})
            (is (nil? @started))
            (run-fetcher-in-thread {:fetcher {:run-build-queue-fetcher true}})
            (Thread/sleep 1)
            (is (not (nil? @started)))))))

