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

(ns emender-jenkins.server-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.server :refer :all]))

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


(deftest test-return-file-existence
    "Check that the emender-jenkins.server/return-file definition exists."
    (testing "if the emender-jenkins.server/return-file definition exists."
        (is (callable? 'emender-jenkins.server/return-file))))


(deftest test-render-front-page-existence
    "Check that the emender-jenkins.server/render-front-page definition exists."
    (testing "if the emender-jenkins.server/render-front-page definition exists."
        (is (callable? 'emender-jenkins.server/render-front-page))))


(deftest test-render-error-page-existence
    "Check that the emender-jenkins.server/render-error-page definition exists."
    (testing "if the emender-jenkins.server/render-error-page definition exists."
        (is (callable? 'emender-jenkins.server/render-error-page))))


(deftest test-get-hostname-existence
    "Check that the emender-jenkins.server/get-hostname definition exists."
    (testing "if the emender-jenkins.server/get-hostname definition exists."
        (is (callable? 'emender-jenkins.server/get-hostname))))


(deftest test-get-api-command-existence
    "Check that the emender-jenkins.server/get-api-command definition exists."
    (testing "if the emender-jenkins.server/get-api-command definition exists."
        (is (callable? 'emender-jenkins.server/get-api-command))))


(deftest test-api-call-handler-existence
    "Check that the emender-jenkins.server/api-call-handler definition exists."
    (testing "if the emender-jenkins.server/api-call-handler definition exists."
        (is (callable? 'emender-jenkins.server/api-call-handler))))


(deftest test-restcall-options-handler-existence
    "Check that the emender-jenkins.server/restcall-options-handler definition exists."
    (testing "if the emender-jenkins.server/restcall-options-handler definition exists."
        (is (callable? 'emender-jenkins.server/restcall-options-handler))))


(deftest test-restcall-head-handler-existence
    "Check that the emender-jenkins.server/restcall-head-handler definition exists."
    (testing "if the emender-jenkins.server/restcall-head-handler definition exists."
        (is (callable? 'emender-jenkins.server/restcall-head-handler))))


(deftest test-non-api-call-handler-existence
    "Check that the emender-jenkins.server/non-api-call-handler definition exists."
    (testing "if the emender-jenkins.server/non-api-call-handler definition exists."
        (is (callable? 'emender-jenkins.server/non-api-call-handler))))


(deftest test-handler-existence
    "Check that the emender-jenkins.server/handler definition exists."
    (testing "if the emender-jenkins.server/handler definition exists."
        (is (callable? 'emender-jenkins.server/handler))))

;(get-api-command nil "/api")

;(get-api-command "/" "/api")
;(get-api-command "xyzzy/" "/api")
;(get-api-command "xyzzy/xyzzy" "/api")

;(get-api-command "/api" "/api")

;(get-api-command "/api/create_job", "/api")

;(get-api-command "/api/system/banners" "/api")


