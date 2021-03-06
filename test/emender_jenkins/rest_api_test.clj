;
;  (C) Copyright 2016, 2017  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns emender-jenkins.rest-api-test
  (:require [clojure.test                :refer :all]
            [clojure.data.json           :as json]
            [emender-jenkins.rest-api    :refer :all]
            [emender-jenkins.results     :as results]
            [clj-jenkins-api.jenkins-api :as jenkins-api]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))


(deftest test-read-request-body-existence
    "Check that the emender-jenkins.rest-api/read-request-body definition exists."
    (testing "if the emender-jenkins.rest-api/read-request-body definition exists."
        (is (callable? 'emender-jenkins.rest-api/read-request-body))))


(deftest test-body->results-existence
    "Check that the emender-jenkins.rest-api/body->results definition exists."
    (testing "if the emender-jenkins.rest-api/body->results definition exists."
        (is (callable? 'emender-jenkins.rest-api/body->results))))


(deftest test-body->job-info-existence
    "Check that the emender-jenkins.rest-api/body->job-info definition exists."
    (testing "if the emender-jenkins.rest-api/body->job-info definition exists."
        (is (callable? 'emender-jenkins.rest-api/body->job-info))))


(deftest test-get-job-name-existence
    "Check that the emender-jenkins.rest-api/get-job-name definition exists."
    (testing "if the emender-jenkins.rest-api/get-job-name definition exists."
        (is (callable? 'emender-jenkins.rest-api/get-job-name))))


(deftest test-get-job-name-from-body-existence
    "Check that the emender-jenkins.rest-api/get-job-name-from-body definition exists."
    (testing "if the emender-jenkins.rest-api/get-job-name-from-body definition exists."
        (is (callable? 'emender-jenkins.rest-api/get-job-name-from-body))))


(deftest test-test-job?-existence
    "Check that the emender-jenkins.rest-api/test-job? definition exists."
    (testing "if the emender-jenkins.rest-api/test-job? definition exists."
        (is (callable? 'emender-jenkins.rest-api/test-job?))))


(deftest test-send-response-existence
    "Check that the emender-jenkins.rest-api/send-response definition exists."
    (testing "if the emender-jenkins.rest-api/send-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/send-response))))


(deftest test-send-error-response-existence
    "Check that the emender-jenkins.rest-api/send-error-response definition exists."
    (testing "if the emender-jenkins.rest-api/send-error-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/send-error-response))))


(deftest test-send-plain-response-existence
    "Check that the emender-jenkins.rest-api/send-plain-response definition exists."
    (testing "if the emender-jenkins.rest-api/send-plain-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/send-plain-response))))


(deftest test-info-handler-existence
    "Check that the emender-jenkins.rest-api/info-handler definition exists."
    (testing "if the emender-jenkins.rest-api/info-handler definition exists."
        (is (callable? 'emender-jenkins.rest-api/info-handler))))


(deftest test-configuration-handler-existence
    "Check that the emender-jenkins.rest-api/configuration-handler definition exists."
    (testing "if the emender-jenkins.rest-api/configuration-handler definition exists."
        (is (callable? 'emender-jenkins.rest-api/configuration-handler))))


(deftest test-system-banners-existence
    "Check that the emender-jenkins.rest-api/system-banners definition exists."
    (testing "if the emender-jenkins.rest-api/system-banners definition exists."
        (is (callable? 'emender-jenkins.rest-api/system-banners))))


(deftest test-status-handler-existence
    "Check that the emender-jenkins.rest-api/status-handler definition exists."
    (testing "if the emender-jenkins.rest-api/status-handler definition exists."
        (is (callable? 'emender-jenkins.rest-api/status-handler))))


(deftest test-reload-job-list-existence
    "Check that the emender-jenkins.rest-api/reload-job-list definition exists."
    (testing "if the emender-jenkins.rest-api/reload-job-list definition exists."
        (is (callable? 'emender-jenkins.rest-api/reload-job-list))))


(deftest test-reload-all-results-existence
    "Check that the emender-jenkins.rest-api/reload-all-results definition exists."
    (testing "if the emender-jenkins.rest-api/reload-all-results definition exists."
        (is (callable? 'emender-jenkins.rest-api/reload-all-results))))


(deftest test-create-error-response-existence
    "Check that the emender-jenkins.rest-api/create-error-response definition exists."
    (testing "if the emender-jenkins.rest-api/create-error-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/create-error-response))))


(deftest test-create-bad-request-response-existence
    "Check that the emender-jenkins.rest-api/create-bad-request-response definition exists."
    (testing "if the emender-jenkins.rest-api/create-bad-request-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/create-bad-request-response))))


(deftest test-job-does-not-exist-response-existence
    "Check that the emender-jenkins.rest-api/job-does-not-exist-response definition exists."
    (testing "if the emender-jenkins.rest-api/job-does-not-exist-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/job-does-not-exist-response))))


(deftest test-wrong-job-name-existence
    "Check that the emender-jenkins.rest-api/wrong-job-name definition exists."
    (testing "if the emender-jenkins.rest-api/wrong-job-name definition exists."
        (is (callable? 'emender-jenkins.rest-api/wrong-job-name))))


(deftest test-job-already-exist-response-existence
    "Check that the emender-jenkins.rest-api/job-already-exist-response definition exists."
    (testing "if the emender-jenkins.rest-api/job-already-exist-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/job-already-exist-response))))


(deftest test-send-job-does-not-exist-response-existence
    "Check that the emender-jenkins.rest-api/send-job-does-not-exist-response definition exists."
    (testing "if the emender-jenkins.rest-api/send-job-does-not-exist-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/send-job-does-not-exist-response))))


(deftest test-send-wrong-job-name-response-existence
    "Check that the emender-jenkins.rest-api/send-wrong-job-name-response definition exists."
    (testing "if the emender-jenkins.rest-api/send-wrong-job-name-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/send-wrong-job-name-response))))


(deftest test-send-job-not-specified-response-existence
    "Check that the emender-jenkins.rest-api/send-job-not-specified-response definition exists."
    (testing "if the emender-jenkins.rest-api/send-job-not-specified-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/send-job-not-specified-response))))


(deftest test-send-job-invalid-metadata-response-existence
    "Check that the emender-jenkins.rest-api/send-job-invalid-metadata-response definition exists."
    (testing "if the emender-jenkins.rest-api/send-job-invalid-metadata-response definition exists."
        (is (callable? 'emender-jenkins.rest-api/send-job-invalid-metadata-response))))


(deftest test-reload-tests-metadata-existence
    "Check that the emender-jenkins.rest-api/reload-tests-metadata definition exists."
    (testing "if the emender-jenkins.rest-api/reload-tests-metadata definition exists."
        (is (callable? 'emender-jenkins.rest-api/reload-tests-metadata))))


(deftest test-perform-job-command-existence
    "Check that the emender-jenkins.rest-api/perform-job-command definition exists."
    (testing "if the emender-jenkins.rest-api/perform-job-command definition exists."
        (is (callable? 'emender-jenkins.rest-api/perform-job-command))))


(deftest test-start-job-existence
    "Check that the emender-jenkins.rest-api/start-job definition exists."
    (testing "if the emender-jenkins.rest-api/start-job definition exists."
        (is (callable? 'emender-jenkins.rest-api/start-job))))


(deftest test-enable-job-existence
    "Check that the emender-jenkins.rest-api/enable-job definition exists."
    (testing "if the emender-jenkins.rest-api/enable-job definition exists."
        (is (callable? 'emender-jenkins.rest-api/enable-job))))


(deftest test-disable-job-existence
    "Check that the emender-jenkins.rest-api/disable-job definition exists."
    (testing "if the emender-jenkins.rest-api/disable-job definition exists."
        (is (callable? 'emender-jenkins.rest-api/disable-job))))


(deftest test-delete-job-existence
    "Check that the emender-jenkins.rest-api/delete-job definition exists."
    (testing "if the emender-jenkins.rest-api/delete-job definition exists."
        (is (callable? 'emender-jenkins.rest-api/delete-job))))


(deftest test-job-invalid-input-existence
    "Check that the emender-jenkins.rest-api/job-invalid-input definition exists."
    (testing "if the emender-jenkins.rest-api/job-invalid-input definition exists."
        (is (callable? 'emender-jenkins.rest-api/job-invalid-input))))


(deftest test-job-metadata-ok?-existence
    "Check that the emender-jenkins.rest-api/job-metadata-ok? definition exists."
    (testing "if the emender-jenkins.rest-api/job-metadata-ok? definition exists."
        (is (callable? 'emender-jenkins.rest-api/job-metadata-ok?))))


(deftest test-create-job-existence
    "Check that the emender-jenkins.rest-api/create-job definition exists."
    (testing "if the emender-jenkins.rest-api/create-job definition exists."
        (is (callable? 'emender-jenkins.rest-api/create-job))))


(deftest test-uri->job-name-existence
    "Check that the emender-jenkins.rest-api/uri->job-name definition exists."
    (testing "if the emender-jenkins.rest-api/uri->job-name definition exists."
        (is (callable? 'emender-jenkins.rest-api/uri->job-name))))


(deftest test-get-job-name-from-uri-or-params-existence
    "Check that the emender-jenkins.rest-api/get-job-name-from-uri-or-params definition exists."
    (testing "if the emender-jenkins.rest-api/get-job-name-from-uri-or-params definition exists."
        (is (callable? 'emender-jenkins.rest-api/get-job-name-from-uri-or-params))))


(deftest test-get-job-existence
    "Check that the emender-jenkins.rest-api/get-job definition exists."
    (testing "if the emender-jenkins.rest-api/get-job definition exists."
        (is (callable? 'emender-jenkins.rest-api/get-job))))


(deftest test-update-job-existence
    "Check that the emender-jenkins.rest-api/update-job definition exists."
    (testing "if the emender-jenkins.rest-api/update-job definition exists."
        (is (callable? 'emender-jenkins.rest-api/update-job))))


(deftest test-get-jobs-existence
    "Check that the emender-jenkins.rest-api/get-jobs definition exists."
    (testing "if the emender-jenkins.rest-api/get-jobs definition exists."
        (is (callable? 'emender-jenkins.rest-api/get-jobs))))


(deftest test-get-job-results-existence
    "Check that the emender-jenkins.rest-api/get-job-results definition exists."
    (testing "if the emender-jenkins.rest-api/get-job-results definition exists."
        (is (callable? 'emender-jenkins.rest-api/get-job-results))))


(deftest test-job-started-handler-existence
    "Check that the emender-jenkins.rest-api/job-started-handler definition exists."
    (testing "if the emender-jenkins.rest-api/job-started-handler definition exists."
        (is (callable? 'emender-jenkins.rest-api/job-started-handler))))


(deftest test-job-finished-handler-existence
    "Check that the emender-jenkins.rest-api/job-finished-handler definition exists."
    (testing "if the emender-jenkins.rest-api/job-finished-handler definition exists."
        (is (callable? 'emender-jenkins.rest-api/job-finished-handler))))


(deftest test-job-results-existence
    "Check that the emender-jenkins.rest-api/job-results definition exists."
    (testing "if the emender-jenkins.rest-api/job-results definition exists."
        (is (callable? 'emender-jenkins.rest-api/job-results))))


(deftest test-unknown-call-handler-existence
    "Check that the emender-jenkins.rest-api/unknown-call-handler definition exists."
    (testing "if the emender-jenkins.rest-api/unknown-call-handler definition exists."
        (is (callable? 'emender-jenkins.rest-api/unknown-call-handler))))


(deftest test-get-output-format-existence
    "Check that the emender-jenkins.rest-api/get-output-format definition exists."
    (testing "if the emender-jenkins.rest-api/get-output-format definition exists."
        (is (callable? 'emender-jenkins.rest-api/get-output-format))))


(deftest test-mime-type-existence
    "Check that the emender-jenkins.rest-api/mime-type definition exists."
    (testing "if the emender-jenkins.rest-api/mime-type definition exists."
        (is (callable? 'emender-jenkins.rest-api/mime-type))))


(deftest test-get-metadata-existence
    "Check that the emender-jenkins.rest-api/get-metadata definition exists."
    (testing "if the emender-jenkins.rest-api/get-metadata definition exists."
        (is (callable? 'emender-jenkins.rest-api/get-metadata))))

;
; Function behaviours
;

(deftest test-create-error-response
    "Check the function emender-jenkins.rest-api/create-error-response."
    (testing "the function emender-jenkins.rest-api/create-error-response."
        (are [x y] (= x y)
            {:status "error" :jobName "job-name" :command "command" :message "message"} (create-error-response "job-name" "command" "message")
            {:status "error" :jobName nil        :command "command" :message "message"}        (create-error-response nil "command" "message")
            {:status "error" :jobName "job-name" :command nil       :message "message"}       (create-error-response "job-name" nil "message")
            {:status "error" :jobName "job-name" :command "command" :message nil}       (create-error-response "job-name" "command" nil)
            {:status "error" :jobName nil        :command nil       :message nil}       (create-error-response nil nil nil))))

(deftest test-create-bad-request-response-1
    "Check the function emender-jenkins.rest-api/create-bad-request-response."
    (testing "the function emender-jenkins.rest-api/create-bad-request-response."
        (are [x y] (= x y)
            {:status "error" :command "command"} (create-bad-request-response "command")
            {:status "error" :command nil      } (create-bad-request-response nil))))

(deftest test-create-bad-request-response-2
    "Check the function emender-jenkins.rest-api/create-bad-request-response."
    (testing "the function emender-jenkins.rest-api/create-bad-request-response."
        (are [x y] (= x y)
            {:status "error" :command "command" :message "message"} (create-bad-request-response "command" "message")
            {:status "error" :command "command" :message "message"} (create-bad-request-response "command" "message")
            {:status "error" :command nil       :message "message"} (create-bad-request-response nil "message")
            {:status "error" :command "command" :message nil}       (create-bad-request-response "command" nil)
            {:status "error" :command nil       :message nil}       (create-bad-request-response nil nil))))

(deftest test-job-does-not-exist-response
    "Check the function emender-jenkins.rest-api/job-does-not-exist-response."
    (testing "the function emender-jenkins.rest-api/job-does-not-exist-response."
        (are [x y] (= x y)
            {:status "error" :jobName "jobName" :command "create_job" :message "Job does not exist"} (job-does-not-exist-response "jobName" :create-job)
            {:status "error" :jobName "jobName" :command nil          :message "Job does not exist"} (job-does-not-exist-response "jobName" nil)
            {:status "error" :jobName nil       :command "create_job" :message "Job does not exist"} (job-does-not-exist-response nil :create-job)
            {:status "error" :jobName nil       :command nil          :message "Job does not exist"} (job-does-not-exist-response nil nil))))

(deftest test-job-already-exist-response
    "Check the function emender-jenkins.rest-api/job-already-exist-response."
    (testing "the function emender-jenkins.rest-api/job-already-exist-response."
        (are [x y] (= x y)
            {:status "error" :jobName "jobName" :command "create_job" :message "Job already exist"} (job-already-exist-response "jobName" :create-job)
            {:status "error" :jobName "jobName" :command nil          :message "Job already exist"} (job-already-exist-response "jobName" nil)
            {:status "error" :jobName nil       :command "create_job" :message "Job already exist"} (job-already-exist-response nil :create-job)
            {:status "error" :jobName nil       :command nil          :message "Job already exist"} (job-already-exist-response nil nil))))

(deftest test-wrong-job-name
    "Check the function emender-jenkins.rest-api/wrong-job-name."
    (testing "the function emender-jenkins.rest-api/wrong-job-name."
        (are [x y] (= x y)
            {:status "error" :jobName "jobName" :command "create_job" :message "The name of job is wrong"} (wrong-job-name "jobName" :create-job)
            {:status "error" :jobName "jobName" :command nil          :message "The name of job is wrong"} (wrong-job-name "jobName" nil)
            {:status "error" :jobName nil       :command "create_job" :message "The name of job is wrong"} (wrong-job-name nil :create-job)
            {:status "error" :jobName nil       :command nil          :message "The name of job is wrong"} (wrong-job-name nil nil))))

(deftest test-uri->job-name
    "Check the function emender-jenkins.rest-api/uri->job-name."
    (testing "the function emender-jenkins.rest-api/uri->job-name."
        (are [x y] (= x y)
            "test-Product_Name-Product_Version-Book_Name-en-US (prod)"
            (uri->job-name "http://10.20.30.40:8080/test-Product_Name-Product_Version-Book_Name-en-US%20(prod)" "http://10.20.30.40:8080/")
            "test-Product_Name-Product_Version-Book_Name-en-US (prod)"
            (uri->job-name "http://jenkins.server.com:8080/test-Product_Name-Product_Version-Book_Name-en-US%20(prod)" "http://jenkins.server.com:8080/")
            )))

(deftest test-uri->job-name-space-replacement
    "Check the function emender-jenkins.rest-api/uri->job-name."
    (testing "the function emender-jenkins.rest-api/uri->job-name."
        (is (= "   " (uri->job-name "prefix%20%20%20" "prefix")))))

(deftest test-uri->job-name-empty-string-checking
    "Check the function emender-jenkins.rest-api/uri->job-name."
    (testing "the function emender-jenkins.rest-api/uri->job-name."
        (is (nil? (uri->job-name "prefix" "prefix")))
        (is (nil? (uri->job-name "" "prefix")))))

(deftest test-uri->job-name-NPE
    "Check the function emender-jenkins.rest-api/uri->job-name."
    (testing "the function emender-jenkins.rest-api/uri->job-name."
        (is (thrown? NullPointerException (uri->job-name nil "prefix")))))

(deftest test-get-job-name-from-uri-or-param-1
    "Check the function emender-jenkins.rest-api/get-job-name-from-uri-or-params."
    (testing "the function emender-jenkins.rest-api/get-job-name-from-uri-or-params."
        (are [result request uri-prefix uri] (= result (get-job-name-from-uri-or-params request uri-prefix uri))
            nil   nil "prefix" "prefix"
            "   " nil "prefix" "prefix%20%20%20"
            "test-Product_Name-Product_Version-Book_Name-en-US (prod)" nil "http://10.20.30.40:8080/" "http://10.20.30.40:8080/test-Product_Name-Product_Version-Book_Name-en-US%20(prod)"
            "test-Product_Name-Product_Version-Book_Name-en-US (prod)" nil "http://jenkins.server.com:8080/" "http://jenkins.server.com:8080/test-Product_Name-Product_Version-Book_Name-en-US%20(prod)")))

(deftest test-get-job-name-from-uri-or-param-2
    "Check the function emender-jenkins.rest-api/get-job-name-from-uri-or-params."
    (testing "the function emender-jenkins.rest-api/get-job-name-from-uri-or-params."
        (are [result request uri-prefix uri] (= result (get-job-name-from-uri-or-params request uri-prefix uri))
            "job-name" {:params {"name" "job-name"}} "prefix" "prefix"
        )))

(deftest test-body->results
    "Check the function emender-jenkins.rest-api/body->results."
    (testing "the function emender-jenkins.rest-api/body->results."
        (are [x y] (= x (body->results y))
            {}            "{}"
            []            "[]"
            [1 2 3 4]     "[1,2,3,4]"
            {"a" 1 "b" 2} "{\"a\":1, \"b\":2}")))

(deftest test-body->job-info
    "Check the function emender-jenkins.rest-api/body->job-info."
    (testing "the function emender-jenkins.rest-api/body->job-info."
        (are [x y] (= x (body->job-info y))
            {}          "{}"
            []          "[]"
            [1 2 3 4]   "[1,2,3,4]"
            {:a 1 :b 2} "{\"a\":1, \"b\":2}")))

(deftest test-get-job-name
    "Check the function emender-jenkins.rest-api/get-job-name."
    (testing "the function emender-jenkins.rest-api/get-job-name."
        (are [x y] (= x (get-job-name y))
            nil       nil
            nil       {:namex "jobname"}
            "jobname" {:name "jobname"})))

(deftest test-mime-type
    "Check the function emender-jenkins.rest-api/mime-type."
    (testing "the function emender-jenkins.rest-api/mime-type."
        (are [x y] (= x (mime-type y))
            "application/json" :json
            "application/edn"  :edn
            "text/csv"         :csv
            "text/plain"       :txt
            "text/xml"         :xml)))

(deftest test-mime-type-default
    "Check the function emender-jenkins.rest-api/mime-type."
    (testing "the function emender-jenkins.rest-api/mime-type."
        (are [x y] (= x (mime-type y))
            "application/json" nil
            "application/json" :other)))

(deftest test-get-output-format
    "Check the function emender-jenkins.rest-api/get-output-format."
    (testing "the function emender-jenkins.rest-api/get-output-format."
        (are [x y] (= x (get-output-format {:params {"format" y}}))
            :json "json"
            :xml  "xml"
            :csv  "csv"
            :edn  "edn"
            :txt  "text"
            :txt  "txt")))

(deftest test-get-output-format-default
    "Check the function emender-jenkins.rest-api/get-output-format."
    (testing "the function emender-jenkins.rest-api/get-output-format."
        (are [x y] (= x (get-output-format y))
            :json nil
            :json :other)))

(deftest test-test-job?
    "Check the function emender-jenkins.rest-api/test-job."
    (testing "the function emender-jenkins.rest-api/test-job."
        (let [request {:configuration
                          {:jobs
                              {:test-jobs-prefix         "test-"
                               :preview-test-jobs-suffix "(preview)"
                               :stage-test-jobs-suffix   "(stage)"
                               :prod-test-jobs-suffix    "(prod)"}}}]
        (are [x y] (= x (test-job? y request))
            true  "test-Product_Name-Product_Version-Title_Name (preview)"
            false "doc-Product_Name-Product_Version-Title_Name (preview)"
            true  "test-Product_Name-Product_Version-Title_Name (stage)"
            false "doc-Product_Name-Product_Version-Title_Name (stage)"
            true  "test-Product_Name-Product_Version-Title_Name (prod)"
            false "doc-Product_Name-Product_Version-Title_Name (prod)"
            false "test-Product_Name-Product_Version-Title_Name (other)"
            false "test-Product_Name-Product_Version-Title_Name"))))

(deftest test-info-handler
    "Check the function emender-jenkins.rest-api/info-handler."
    (testing "the function emender-jenkins.rest-api/info-handler."
        (with-redefs [send-response (fn [response request] response)]
            (let [request {:configuration
                              {:info
                                  {:version "1.0"}
                               :api
                                  {:prefix "/api"}}}]
                (are [x y] (= x (info-handler request y))
                    {:name       "Emender Jenkins Service"
                     :version    "1.0"
                     :api_prefix "/api"
                     :hostname   "hostname"
                     :test       "/api"} "hostname")))))

(deftest test-configuration-handler
    "Check the function emender-jenkins.rest-api/configuration-handler."
    (testing "the function emender-jenkins.rest-api/configuration-handler."
        (with-redefs [send-response (fn [response request] response)]
            (let [request {:configuration
                              {:config
                                  {:verbose-show-configuration true}
                               :info
                                  {:version "1.0"}
                               :api
                                  {:prefix "/api"}}}]
                (is (= (configuration-handler request)
                       {:config
                           {:verbose-show-configuration true}
                            :info
                                {:version "1.0"}
                                 :api
                                     {:prefix "/api"}}))))))

(deftest test-system-banners
    "Check the function emender-jenkins.rest-api/system-banners."
    (testing "the function emender-jenkins.rest-api/system-banners."
        (with-redefs [send-response (fn [response request] response)]
            (let [request {}]
                (is (= (system-banners request "/api/system/banners")
                    {:message    "Alpha version"
                     :type       "Warning"}))
                (is (= (system-banners request "/api/system/xxx")
                    nil
                    ))))))

(def create-job-postdata
    "{\"name\":\"test-Test_Product-1.0-Test_Book-en-US (preview)\", \"ssh_url_to_repo\":\"https://url-to-repo.git\", \"branch\":\"master\"}")

(def create-job-postdata-bad-job-name-1
    "{\"name\":\"xxx-Test_Product-1.0-Test_Book-en-US (preview)\", \"ssh_url_to_repo\":\"https://url-to-repo.git\", \"branch\":\"master\"}")

(def create-job-postdata-bad-job-name-2
    "{\"name\":\"test-Test_Product-1.0-Test_Book-en-US\", \"ssh_url_to_repo\":\"https://url-to-repo.git\", \"branch\":\"master\"}")

(def create-job-postdata-invalid-metadata-1
    "{\"ssh_url_to_repo\":\"https://url-to-repo.git\", \"branch\":\"master\"}")

(def create-job-postdata-invalid-metadata-2
    "{\"name\":\"test-Test_Product-1.0-Test_Book-en-US (preview)\", \"branch\":\"master\"}")

(def create-job-postdata-invalid-metadata-3
    "{\"name\":\"test-Test_Product-1.0-Test_Book-en-US (preview)\", \"ssh_url_to_repo\":\"https://url-to-repo.git\"}")

(def default-request
    {:configuration
        {:jobs
            {:preview-test-jobs-suffix "(preview)"
             :stage-test-jobs-suffix   "(stage)"
             :prod-test-jobs-suffix    "(prod)"
             :test-jobs-prefix         "test-"}}})

(deftest test-create-job
    "Check the function emender-jenkins.rest-api/create-job."
    (testing "the function emender-jenkins.rest-api/create-job."
        (with-redefs [jenkins-api/create-job (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name git-repo branch credentials-id metadata-directory metadata]
                                                 (jenkins-api/ok-response-structure job-name "create_job" include-jenkins-reply? "created"))
                      jenkins-api/start-job  (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name]
                                                 (jenkins-api/ok-response-structure job-name "build" include-jenkins-reply? "added to queue"))
                      read-request-body      (fn [request] create-job-postdata)
                      reload-job-list        (fn [response request] response)
                      send-response          (fn [response request] response)]
                      (is (= (create-job default-request) {:status  "ok"
                                                           :jobName "test-Test_Product-1.0-Test_Book-en-US (preview)"
                                                           :command "create_job"})))))

(deftest test-create-job-bad-job-name-1
    "Check the function emender-jenkins.rest-api/create-job."
    (testing "the function emender-jenkins.rest-api/create-job."
        (with-redefs [jenkins-api/create-job (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name git-repo branch credentials-id metadata-directory metadata]
                                                 (jenkins-api/ok-response-structure job-name "create_job" include-jenkins-reply? "created"))
                      jenkins-api/start-job  (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name]
                                                 (jenkins-api/ok-response-structure job-name "build" include-jenkins-reply? "added to queue"))
                      read-request-body      (fn [request] create-job-postdata-bad-job-name-1)
                      reload-job-list        (fn [response request] response)
                      send-response          (fn [response request] response)]
                      (is (= (create-job default-request) {:status  400
                                                           :headers {"Content-Type" "application/json"}
                                                           :body "{\"status\":\"error\",\"jobName\":\"xxx-Test_Product-1.0-Test_Book-en-US (preview)\",\"command\":\"create_job\",\"message\":\"The name of job is wrong\"}"})))))

(deftest test-create-job-bad-job-name-2
    "Check the function emender-jenkins.rest-api/create-job."
    (testing "the function emender-jenkins.rest-api/create-job."
        (with-redefs [jenkins-api/create-job (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name git-repo branch credentials-id metadata-directory metadata]
                                                 (jenkins-api/ok-response-structure job-name "create_job" include-jenkins-reply? "created"))
                      jenkins-api/start-job  (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name]
                                                 (jenkins-api/ok-response-structure job-name "build" include-jenkins-reply? "added to queue"))
                      read-request-body      (fn [request] create-job-postdata-bad-job-name-2)
                      reload-job-list        (fn [response request] response)
                      send-response          (fn [response request] response)]
                      (is (= (create-job default-request) {:status  400
                                                           :headers {"Content-Type" "application/json"}
                                                           :body "{\"status\":\"error\",\"jobName\":\"test-Test_Product-1.0-Test_Book-en-US\",\"command\":\"create_job\",\"message\":\"The name of job is wrong\"}"})))))

(deftest test-create-job-invalid-metadata-1
    "Check the function emender-jenkins.rest-api/create-job."
    (testing "the function emender-jenkins.rest-api/create-job."
        (with-redefs [jenkins-api/create-job (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name git-repo branch credentials-id metadata-directory metadata]
                                                 (jenkins-api/ok-response-structure job-name "create_job" include-jenkins-reply? "created"))
                      jenkins-api/start-job  (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name]
                                                 (jenkins-api/ok-response-structure job-name "build" include-jenkins-reply? "added to queue"))
                      read-request-body      (fn [request] create-job-postdata-invalid-metadata-1)
                      reload-job-list        (fn [response request] response)
                      send-response          (fn [response request] response)]
                      (is (= (create-job default-request) {:status  400
                                                           :headers {"Content-Type" "application/json"}
                                                           :body "{\"status\":\"error\",\"command\":\"create_job\",\"message\":\"invalid or missing input\"}"})))))

(deftest test-create-job-invalid-metadata-2
    "Check the function emender-jenkins.rest-api/create-job."
    (testing "the function emender-jenkins.rest-api/create-job."
        (with-redefs [jenkins-api/create-job (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name git-repo branch credentials-id metadata-directory metadata]
                                                 (jenkins-api/ok-response-structure job-name "create_job" include-jenkins-reply? "created"))
                      jenkins-api/start-job  (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name]
                                                 (jenkins-api/ok-response-structure job-name "build" include-jenkins-reply? "added to queue"))
                      read-request-body      (fn [request] create-job-postdata-invalid-metadata-2)
                      reload-job-list        (fn [response request] response)
                      send-response          (fn [response request] response)]
                      (is (= (create-job default-request) {:status  400
                                                           :headers {"Content-Type" "application/json"}
                                                           :body "{\"status\":\"error\",\"command\":\"create_job\",\"message\":\"invalid input: git repo not specified\"}"})))))

(deftest test-create-job-invalid-metadata-3
    "Check the function emender-jenkins.rest-api/create-job."
    (testing "the function emender-jenkins.rest-api/create-job."
        (with-redefs [jenkins-api/create-job (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name git-repo branch credentials-id metadata-directory metadata]
                                                 (jenkins-api/ok-response-structure job-name "create_job" include-jenkins-reply? "created"))
                      jenkins-api/start-job  (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name]
                                                 (jenkins-api/ok-response-structure job-name "build" include-jenkins-reply? "added to queue"))
                      read-request-body      (fn [request] create-job-postdata-invalid-metadata-3)
                      reload-job-list        (fn [response request] response)
                      send-response          (fn [response request] response)]
                      (is (= (create-job default-request) {:status  400
                                                           :headers {"Content-Type" "application/json"}
                                                           :body "{\"status\":\"error\",\"command\":\"create_job\",\"message\":\"invalid input: branch not specified\"}"})))))

(deftest test-create-job-NPE
    "Check the function emender-jenkins.rest-api/create-job."
    (testing "the function emender-jenkins.rest-api/create-job."
        (with-redefs [jenkins-api/create-job (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name git-repo branch credentials-id metadata-directory metadata]
                                                 (jenkins-api/ok-response-structure job-name "create_job" include-jenkins-reply? "created"))
                      jenkins-api/start-job  (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name]
                                                 (jenkins-api/ok-response-structure job-name "build" include-jenkins-reply? "added to queue"))
                      read-request-body      (fn [request] nil)
                      reload-job-list        (fn [response request] response)
                      send-response          (fn [response request] response)]
                      (is (= (create-job default-request) {:status  400
                                                           :headers {"Content-Type" "application/json"}
                                                           :body "{\"status\":\"error\",\"command\":\"create_job\",\"message\":\"invalid or missing input\"}"})))))

(deftest test-create-existing-job
    "Check the function emender-jenkins.rest-api/create-job."
    (testing "the function emender-jenkins.rest-api/create-job."
        (with-redefs [jenkins-api/create-job (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name git-repo branch credentials-id metadata-directory metadata]
                                                 (jenkins-api/ok-response-structure job-name "create_job" include-jenkins-reply? "created"))
                      jenkins-api/start-job  (fn [jenkins-url jenkins-auth include-jenkins-reply? job-name]
                                                 (jenkins-api/ok-response-structure job-name "build" include-jenkins-reply? "added to queue"))
                      read-request-body      (fn [request] create-job-postdata)
                      results/job-exists?    (fn [job-name] true)
                      reload-job-list        (fn [response request] response)
                      send-response          (fn [response request] response)
                      send-error-response    (fn [response request http-code] response)]
                      (is (= (create-job default-request) {:status  "error"
                                                           :command "create_job"
                                                           :jobName "test-Test_Product-1.0-Test_Book-en-US (preview)"
                                                           :message "Job already exist"})))))

(def read-jobs-in-queue-expected-result
    [{"_class" "hudson.model.Queue$BlockedItem" "task" {"_class" "hudson.model.FreeStyleProject" "name" "test-Example_Documentation-1.0-Guide-en-US (preview)"}}
    {"_class" "hudson.model.Queue$BlockedItem" "task" {"_class" "hudson.model.FreeStyleProject" "name" "test-Example_Documentation-1.0-Guide-en-US (stage)  "}}]
)

(def jobs-in-queue-expected-jenkins-response
    (str "{\"_class\":\"hudson.model.Queue\","
          "\"items\":["
             "{\"_class\":\"hudson.model.Queue$BlockedItem\",\"task\":{\"_class\":\"hudson.model.FreeStyleProject\",\"name\":\"test-Example_Documentation-1.0-Guide-en-US (preview)\"}}"
             "{\"_class\":\"hudson.model.Queue$BlockedItem\",\"task\":{\"_class\":\"hudson.model.FreeStyleProject\",\"name\":\"test-Example_Documentation-1.0-Guide-en-US (stage)  \"}}"
         "]}"))

(def building-jobs-jenkins-response
    (str "{\"_class\":\"hudson.model.ListView\","
          "\"jobs\":["
          "{\"_class\":\"hudson.model.FreeStyleProject\",\"name\":\"test-Example_Documentation-1.0-Guide-en-US (preview)\",\"url\":\"http://10.20.30.40:8080/job/test-Example_Documentation-1.0-Guide-en-US%20(preview)/\",\"buildable\":true,\"color\":\"yellow_anime\",\"lastSuccessfulBuild\":{\"_class\":\"hudson.model.FreeStyleBuild\",\"description\":\"Total: 4  Passed: 1  Failed: 3\"},\"scm\":{\"_class\":\"hudson.plugins.git.GitSCM\",\"userRemoteConfigs\":[{\"url\":\"git@git.domain.name:example-documentation/guide.git\"}]}},"
          "{\"_class\":\"hudson.model.FreeStyleProject\",\"name\":\"test-Example_Documentation-1.0-Guide-en-US (stage)\",\"url\":\"http://10.20.30.40:8080/job/test-Example_Documentation-1.0-Guide-en-US%20(stage)/\",\"buildable\":true,\"color\":\"yellow_anime\",\"lastSuccessfulBuild\":{\"_class\":\"hudson.model.FreeStyleBuild\",\"description\":\"Total: 4  Passed: 1  Failed: 3\"},\"scm\":{\"_class\":\"hudson.plugins.git.GitSCM\",\"userRemoteConfigs\":[{\"url\":\"git@git.domain.name:example-documentation/guide.git\"}]}}"
          "]}"))

(deftest test-get-running-jobs
    "Check the function emender-jenkins.rest-api/get-running-jobs."
    (testing "the function emender-jenkins.rest-api/get-running-jobs."
        (let [request       {:configuration {:jenkins {:currently-building-view "Building"
                                                      :jenkins-url "http://10.20.30.40:8080/"}
                                             :fetcher {:currently-building-jobs-cache-max-age 10
                                                       :jobs-in-queue-cache-max-age 20}}}]
             (with-redefs [jenkins-api/get-command (fn [all-jobs-url] jobs-in-queue-expected-jenkins-response)]
                  (results/read-jobs-in-queue (:configuration request)))
             (with-redefs [jenkins-api/get-command (fn [all-jobs-url] building-jobs-jenkins-response)]
                  (results/read-currently-building-jobs (:configuration request)))
             (results/read-running-jobs (:configuration request))
             (with-redefs [results/update-running-jobs-cache (fn [configuration] nil)]
                 (is (= (get-running-jobs request)
                        {:status 200
                         :headers {"Content-Type" "application/json"}
                         :body "[{\"queuePos\":2,\"jobName\":\"test-Example_Documentation-1.0-Guide-en-US (preview)\",\"state\":\"QUEUED\"},{\"queuePos\":1,\"jobName\":\"test-Example_Documentation-1.0-Guide-en-US (stage)  \",\"state\":\"QUEUED\"},{\"state\":\"BUILDING\",\"jobName\":\"test-Example_Documentation-1.0-Guide-en-US (preview)\"},{\"state\":\"BUILDING\",\"jobName\":\"test-Example_Documentation-1.0-Guide-en-US (stage)\"}]"}))))))

(deftest test-get-running-jobs-negative
    "Check the function emender-jenkins.rest-api/get-running-jobs."
    (testing "the function emender-jenkins.rest-api/get-running-jobs."
    (with-redefs [results/get-running-jobs     (fn [configuration] nil)]
        (let [request       {:configuration {:jenkins {:currently-building-view "Building"
                                                      :jenkins-url "http://10.20.30.40:8080/"}}}]
             (is (= (get-running-jobs request)
                    {:status 500
                     :headers {"Content-Type" "application/json"}
                     :body "{\"status\":\"error\",\"command\":\"running_jobs\",\"message\":\"Can not read Jenkins queue and\\/or selected view\"}"}))))))

(deftest test-get-currently-building-jobs
    "Check the function emender-jenkins.rest-api/get-currently-building-jobs."
    (testing "the function emender-jenkins.rest-api/get-currently-building-jobs."
    (with-redefs [jenkins-api/get-command (fn [all-jobs-url] building-jobs-jenkins-response)
                  send-response           (fn [response request] response)
                  results/update-currently-building-jobs-cache (fn [configuration] nil)]
        (let [request {:configuration {:jenkins {:currently-building-view "Building"
                                                 :jenkins-url "http://10.20.30.40:8080/"}}}]
             (results/read-currently-building-jobs (:configuration request))
             (is (= (get-currently-building-jobs request)
                    ["test-Example_Documentation-1.0-Guide-en-US (preview)" "test-Example_Documentation-1.0-Guide-en-US (stage)"]))))))

(deftest test-get-currently-building-jobs-negative
    "Check the function emender-jenkins.rest-api/get-currently-building-jobs."
    (testing "the function emender-jenkins.rest-api/get-currently-building-jobs."
    (with-redefs [results/get-currently-building-jobs (fn [configuration] nil)
                  send-error-response (fn [response request what] response)]
        (let [request {:configuration {:jenkins {:currently-building-view "Building"
                                                 :jenkins-url "http://10.20.30.40:8080/"}}}]
             (is (= (get-currently-building-jobs request)
                    {:status  "error"
                     :command "currently_building_jobs"
                     :message "Can not read Jenkins view"}))))))

(def jobs-in-queue-expected-jenkins-response
    (str "{\"_class\":\"hudson.model.Queue\","
          "\"items\":["
             "{\"_class\":\"hudson.model.Queue$BlockedItem\",\"task\":{\"_class\":\"hudson.model.FreeStyleProject\",\"name\":\"test-Example_Documentation-1.0-Guide-en-US (preview)\"}}"
             "{\"_class\":\"hudson.model.Queue$BlockedItem\",\"task\":{\"_class\":\"hudson.model.FreeStyleProject\",\"name\":\"test-Example_Documentation-1.0-Guide-en-US (stage)  \"}}"
         "]}"))

(deftest test-get-jobs-in-queue
    "Check the function emender-jenkins.rest-api/get-jobs-in-queue."
    (testing "the function emender-jenkins.rest-api/get-jobs-in-queue."
    (with-redefs [jenkins-api/get-command (fn [all-jobs-url] jobs-in-queue-expected-jenkins-response)
                  send-response           (fn [response request] response)
                  results/update-jobs-in-queue-cache (fn [configuration] nil)]
        (let [request {:configuration {:jenkins {:currently-building-view "Building"
                                                 :jenkins-url "http://10.20.30.40:8080/"}}}]
             (results/read-jobs-in-queue (:configuration request))
             (is (= (get-jobs-in-queue request)
                   [{"queuePos" 2
                     "jobName" "test-Example_Documentation-1.0-Guide-en-US (preview)"}
                    {"queuePos" 1
                     "jobName" "test-Example_Documentation-1.0-Guide-en-US (stage)  "}]))))))

(deftest test-get-jobs-in-queue-negative
    "Check the function emender-jenkins.rest-api/get-jobs-in-queue."
    (testing "the function emender-jenkins.rest-api/get-jobs-in-queue."
    (with-redefs [results/get-jobs-in-queue (fn [configuration] nil)
                  send-error-response (fn [response request what] response)]
        (let [request {:configuration {:jenkins {:currently-building-view "Building"
                                                 :jenkins-url "http://10.20.30.40:8080/"}}}]
             (is (= (get-jobs-in-queue request)
                    {:status  "error"
                     :command "jobs_in_queue"
                     :message "Can not read Jenkins queue"}))))))

(deftest test-send-response
    "Check the function emender-jenkins.rest-api/send-response."
    (testing "the function emender-jenkins.rest-api/send-response."
        (let [request       {:configuration {:jenkins {:currently-building-view "Building"
                                                      :jenkins-url "http://10.20.30.40:8080/"}}}]
             (is (= (send-response {:response :ok} request)
                    {:status  200
                     :headers {"Content-Type" "application/json"}
                     :body    "{\"response\":\"ok\"}"}))
             (is (= (send-response {:response {:deep {:structure :ok}}} request)
                    {:status  200
                     :headers {"Content-Type" "application/json"}
                     :body    "{\"response\":{\"deep\":{\"structure\":\"ok\"}}}"})))))

(deftest test-send-response-pretty-print
    "Check the function emender-jenkins.rest-api/send-response."
    (testing "the function emender-jenkins.rest-api/send-response."
        (let [request       {:configuration {:jenkins {:currently-building-view "Building"
                                                      :jenkins-url "http://10.20.30.40:8080/"}
                                             :config  {:pretty-print true}}}]
             (is (= (send-response {:response :ok} request)
                    {:status  200
                     :headers {"Content-Type" "application/json"}
                     :body    "{\"response\":\"ok\"}\n"}))
             (is (= (send-response {:response {:deep {:structure :ok}}} request)
                    {:status  200
                     :headers {"Content-Type" "application/json"}
                     :body    "{\"response\":{\"deep\":{\"structure\":\"ok\"}}}\n"})))))

(deftest test-send-error-response
    "Check the function emender-jenkins.rest-api/send-error-response."
    (testing "the function emender-jenkins.rest-api/send-error-response."
        (let [request       {:configuration {:jenkins {:currently-building-view "Building"
                                                      :jenkins-url "http://10.20.30.40:8080/"}}}]
             (is (= (send-error-response {:response :error} request :ok)
                    {:status  200
                     :headers {"Content-Type" "application/json"}
                     :body    "{\"response\":\"error\"}"}))
             (is (= (send-error-response {:response {:deep {:structure :error}}} request :ok)
                    {:status  200
                     :headers {"Content-Type" "application/json"}
                     :body    "{\"response\":{\"deep\":{\"structure\":\"error\"}}}"})))))

(deftest test-send-error-response-http-codes
    "Check the function emender-jenkins.rest-api/send-error-response."
    (testing "the function emender-jenkins.rest-api/send-error-response."
        (let [request       {:configuration {:jenkins {:currently-building-view "Building"
                                                      :jenkins-url "http://10.20.30.40:8080/"}}}
              response      {:response :error}]
              (are [x y] (= (send-error-response response request x) y)
                  :ok
                    {:status  200
                     :headers {"Content-Type" "application/json"}
                     :body    "{\"response\":\"error\"}"}
                  :bad-request
                    {:status  400
                     :headers {"Content-Type" "application/json"}
                     :body    "{\"response\":\"error\"}"}
                  :not-found
                    {:status  404
                     :headers {"Content-Type" "application/json"}
                     :body    "{\"response\":\"error\"}"}
                  :internal-server-error
                    {:status  500
                     :headers {"Content-Type" "application/json"}
                     :body    "{\"response\":\"error\"}"}
                  :not-implemented
                    {:status  501
                     :headers {"Content-Type" "application/json"}
                     :body    "{\"response\":\"error\"}"}))))

(deftest test-send-error-response-pretty-print
    "Check the function emender-jenkins.rest-api/send-error-response."
    (testing "the function emender-jenkins.rest-api/send-error-response."
        (let [request       {:configuration {:jenkins {:currently-building-view "Building"
                                                      :jenkins-url "http://10.20.30.40:8080/"}
                                             :config  {:pretty-print true}}}]
             (is (= (send-error-response {:response :ok} request :ok)
                    {:status  200
                     :headers {"Content-Type" "application/json"}
                     :body    "{\"response\":\"ok\"}\n"}))
             (is (= (send-error-response {:response {:deep {:structure :ok}}} request :ok)
                    {:status  200
                     :headers {"Content-Type" "application/json"}
                     :body    "{\"response\":{\"deep\":{\"structure\":\"ok\"}}}\n"})))))

(deftest test-send-plain-response
    "Check the function emender-jenkins.rest-api/send-plain-response."
    (testing "the function emender-jenkins.rest-api/send-plain-response."
         (is (= (send-plain-response {:response :error})
                {:status  200
                 :headers {"Content-Type" "application/json"}
                 :body    {:response :error}}))
         (is (= (send-plain-response {:response {:deep {:structure :error}}})
                {:status  200
                 :headers {"Content-Type" "application/json"}
                 :body    {:response {:deep {:structure :error}}}}))))

