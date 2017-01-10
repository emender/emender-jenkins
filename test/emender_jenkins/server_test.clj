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

(require '[clojure.tools.logging :as log])
(require '[clojure.pprint        :as pprint])
(require '[ring.util.response    :as http-response])

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


(deftest test-log-request-existence
    "Check that the emender-jenkins.server/log-request definition exists."
    (testing "if the emender-jenkins.server/log-request definition exists."
        (is (callable? 'emender-jenkins.server/log-request))))

;
; Function behaviours
;
(deftest test-get-api-command-null-uri
    "Check the function emender-jenkins.server/get-api-command."
    (testing "the function emender-jenkins.server/get-api-command."
        (are [x y] (= x y)
            nil (get-api-command nil nil)
            nil (get-api-command nil "")
            nil (get-api-command nil "/api")
            nil (get-api-command nil "/api/endpoint"))))

(deftest test-get-api-command-not-found
    "Check the function emender-jenkins.server/get-api-command."
    (testing "the function emender-jenkins.server/get-api-command."
        (are [x y] (= x y)
            nil (get-api-command "/" "/api")
            nil (get-api-command "xyzzy/" "/api")
            nil (get-api-command "xyzzy/xyzzy/" "/api"))))

(deftest test-get-api-command-correct-url
    "Check the function emender-jenkins.server/get-api-command."
    (testing "the function emender-jenkins.server/get-api-command."
        (are [x y] (= x y)
            ""           (get-api-command "/api" "/api")
            "create_job" (get-api-command "/api/create_job" "/api")
            "system"     (get-api-command "/api/system/banners" "/api"))))

(deftest test-get-api-part-from-uri-NPE
    "Check the function emender-jenkins.server/get-api-part-from-uri."
    (testing "the function emender-jenkins.server/get-api-part-from-uri."
        (is (thrown? NullPointerException (get-api-part-from-uri nil nil)))))

(deftest test-get-api-part-from-uri
    "Check the function emender-jenkins.server/get-api-command."
    (testing "the function emender-jenkins.server/get-api-command."
        (are [x y] (= x y)
            "create_job" (get-api-part-from-uri "/api/create_job" "/api")
            "system"     (get-api-part-from-uri "/api/system/banners" "/api")
            ""           (get-api-part-from-uri "/api/" "/api")
            ""           (get-api-part-from-uri "/api/" "/api")
            "create_job" (get-api-part-from-uri "/api/create_job/" "/api")
            "system"     (get-api-part-from-uri "/api/system/banners/" "/api"))))

(deftest test-get-api-part-from-uri-wrong-api
    "Check the function emender-jenkins.server/get-api-command."
    (testing "the function emender-jenkins.server/get-api-command."
        (are [x y] (= x y)
            nil (get-api-part-from-uri "/wring-api" "/api"))))

(deftest test-log-request
    "Check the function emender-jenkins.server/log-request."
    (testing "the function emender-jenkins.server/log-request."
        (with-redefs [log/log* (fn [logger level throwable message] message)]
            (are [x y] (= x y)
                "Handling request:  nil nil"                 (log-request {})
                "Handling request:  http://test nil"         (log-request {:uri "http://test"})
                "Handling request:  nil 10.20.30.40"         (log-request {:remote-addr "10.20.30.40"})
                "Handling request:  http://test 10.20.30.40" (log-request {:uri "http://test" :remote-addr "10.20.30.40"})))))

(deftest test-log-request-verbose-mode
    "Check the function emender-jenkins.server/log-request."
    (testing "the function emender-jenkins.server/log-request."
        (with-redefs [log/log* (fn [logger level throwable message] message)]
            (let [request  {:uri "http://test" :remote-addr "10.20.30.40" :configuration {:config {:verbose true :pretty-print false}}}]
                (is (.startsWith (log-request request) "Handling request:  {"))))))

(deftest test-log-request-pretty-print-mode-1
    "Check the function emender-jenkins.server/log-request."
    (testing "the function emender-jenkins.server/log-request."
        (with-redefs [log/log* (fn [logger level throwable message] message)]
            (let [request  {:uri "http://test" :remote-addr "10.20.30.40" :configuration {:config {:verbose true :pretty-print true}}}]
                (is (nil? (log-request request)))))))

(deftest test-log-request-pretty-print-mode-2
    "Check the function emender-jenkins.server/log-request."
    (testing "the function emender-jenkins.server/log-request."
        (with-redefs [pprint/pprint (fn [value] (str value))]
            (let [request  {:uri "http://test" :remote-addr "10.20.30.40" :configuration {:config {:verbose true :pretty-print true}}}]
                (is (log-request request))))))

(deftest test-non-api-call-handler
    "Check the function emender-jenkins.server/non-api-call-handler."
    (testing "the function emender-jenkins.server/non-api-call-handler."
        (with-redefs [render-front-page (fn [request] :front-page)
                      render-error-page (fn [request] :error-page)]
            (are [x y] (= x (non-api-call-handler :request y))
                :front-page "/"
                :error-page ""
                :error-page "/info"
                :error-page "something/else"))))

(deftest test-restcall-options-handler
    "Check the function emender-jenkins.server/restcall-options-handler."
    (testing "the function emender-jenkins.server/restcall-options-handler."
        (with-redefs [http-response/response     (fn [resp] {:response resp})
                      http-response/content-type (fn [resp content-type] (assoc resp :content-type content-type))]
            (is (= {:response "", :content-type "application/json"} (restcall-options-handler))))))

(deftest test-restcall-head-handler
    "Check the function emender-jenkins.server/restcall-head-handler."
    (testing "the function emender-jenkins.server/restcall-head-handler."
        (with-redefs [http-response/response     (fn [resp] {:response resp})
                      http-response/content-type (fn [resp content-type] (assoc resp :content-type content-type))]
            (is (= {:response "", :content-type "application/json"} (restcall-head-handler))))))

(deftest test-api-call-handler-1
    "Check the function emender-jenkins.server/api-call-handler."
    (testing "the function emender-jenkins.server/api-call-handler."
        (let [request {:configuration {
                          :api  {:prefix "/api"}
                          :info {:version "1.0"}}}]
            (with-redefs [get-hostname    (fn [] "")]
            (is (= (api-call-handler request "/api" :get)
                   {:status 200
                    :headers {"Content-Type" "application/json"}
                    :body "{\"name\":\"Emender Jenkins Service\",\"version\":\"1.0\",\"api_prefix\":\"\\/api\",\"hostname\":\"\",\"test\":\"\\/api\"}"}))))))

(deftest test-api-call-handler-2
    "Check the function emender-jenkins.server/api-call-handler."
    (testing "the function emender-jenkins.server/api-call-handler."
        (let [request {:configuration {
                          :api  {:prefix "/api"}
                          :info {:version "1.0"}}}]
            (are [uri result] (= (api-call-handler request uri :get) result)
                "/api/"
                {:status 200
                 :headers {"Content-Type" "application/json"}
                 :body "{\"name\":\"Emender Jenkins Service\",\"version\":\"1.0\",\"api_prefix\":\"\\/api\",\"hostname\":\"dhcp-lab-190.englab.brq.redhat.com\",\"test\":\"\\/api\"}"}
                "/api/configuration"
                {:status 200
                 :headers {"Content-Type" "application/json"}
                 :body "{\"api\":{\"prefix\":\"\\/api\"},\"info\":{\"version\":\"1.0\"},\"jenkins\":{\"jenkins-auth\":\"********\"}}"}
                "/api/system/banners"
                {:status 200
                 :headers {"Content-Type" "application/json"}
                 :body "{\"message\":\"Alpha version\",\"type\":\"Warning\"}"}))))

