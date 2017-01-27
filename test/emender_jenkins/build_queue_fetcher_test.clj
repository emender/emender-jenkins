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
            [emender-jenkins.results          :as results]))

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

