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

(ns emender-jenkins.core-test
  (:require [clojure.test         :refer :all]
            [ring.adapter.jetty   :as jetty]
            [emender-jenkins.core :refer :all]))

(require '[clojure.tools.cli       :as cli])

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

(deftest test-get-cors-headers-existence
    "Check that the emender-jenkins.core/get-cors-headers definition exists."
    (testing "if the emender-jenkins.core/get-cors-headers definition exists."
        (is (callable? 'emender-jenkins.core/get-cors-headers))))


(deftest test-origin-allowed?-existence
    "Check that the emender-jenkins.core/origin-allowed? definition exists."
    (testing "if the emender-jenkins.core/origin-allowed? definition exists."
        (is (callable? 'emender-jenkins.core/origin-allowed?))))


(deftest test-all-cors-existence
    "Check that the emender-jenkins.core/all-cors definition exists."
    (testing "if the emender-jenkins.core/all-cors definition exists."
        (is (callable? 'emender-jenkins.core/all-cors))))


(deftest test-start-server-on-regular-machine-existence
    "Check that the emender-jenkins.core/start-server-on-regular-machine definition exists."
    (testing "if the emender-jenkins.core/start-server-on-regular-machine definition exists."
        (is (callable? 'emender-jenkins.core/start-server-on-regular-machine))))


(deftest test-start-server-on-openshift-existence
    "Check that the emender-jenkins.core/start-server-on-openshift definition exists."
    (testing "if the emender-jenkins.core/start-server-on-openshift definition exists."
        (is (callable? 'emender-jenkins.core/start-server-on-openshift))))


(deftest test-start-server-existence
    "Check that the emender-jenkins.core/start-server definition exists."
    (testing "if the emender-jenkins.core/start-server definition exists."
        (is (callable? 'emender-jenkins.core/start-server))))


(deftest test-get-and-check-port-existence
    "Check that the emender-jenkins.core/get-and-check-port definition exists."
    (testing "if the emender-jenkins.core/get-and-check-port definition exists."
        (is (callable? 'emender-jenkins.core/get-and-check-port))))


(deftest test-get-port-existence
    "Check that the emender-jenkins.core/get-port definition exists."
    (testing "if the emender-jenkins.core/get-port definition exists."
        (is (callable? 'emender-jenkins.core/get-port))))


(deftest test-show-help-existence
    "Check that the emender-jenkins.core/show-help definition exists."
    (testing "if the emender-jenkins.core/show-help definition exists."
        (is (callable? 'emender-jenkins.core/show-help))))


(deftest test-print-environment-configuration-existence
    "Check that the emender-jenkins.core/print-environment-configuration definition exists."
    (testing "if the emender-jenkins.core/print-environment-configuration definition exists."
        (is (callable? 'emender-jenkins.core/print-environment-configuration))))


(deftest test-fetch-jobs-only-existence
    "Check that the emender-jenkins.core/fetch-jobs-only definition exists."
    (testing "if the emender-jenkins.core/fetch-jobs-only definition exists."
        (is (callable? 'emender-jenkins.core/fetch-jobs-only))))


(deftest test-show-config-existence
    "Check that the emender-jenkins.core/show-config definition exists."
    (testing "if the emender-jenkins.core/show-config definition exists."
        (is (callable? 'emender-jenkins.core/show-config))))


(deftest test-run-app-existence
    "Check that the emender-jenkins.core/run-app definition exists."
    (testing "if the emender-jenkins.core/run-app definition exists."
        (is (callable? 'emender-jenkins.core/run-app))))


(deftest test--main-existence
    "Check that the emender-jenkins.core/-main definition exists."
    (testing "if the emender-jenkins.core/-main definition exists."
        (is (callable? 'emender-jenkins.core/-main))))

;
; Tests for function behaviours
;

(deftest test-get-port
    "Check the function emender-jenkins.core/get-port."
    (testing "the function emender-jenkins.core/get-port."
        (is (= (get-port "1")     "1"))
        (is (= (get-port "2")     "2"))
        (is (= (get-port "3000")  "3000"))
        (is (= (get-port "65534") "65534"))
        (is (= (get-port "65535") "65535"))))

(deftest test-get-port-special-cases
    "Check the function emender-jenkins.core/get-port."
    (testing "the function emender-jenkins.core/get-port."
        (is (= (get-port nil)     "3000"))
        (is (= (get-port "")      "3000"))
        (is (= (get-port 0)       "3000"))
        (is (= (get-port 1)       "3000"))
        (is (= (get-port 65535)   "3000"))
        (is (= (get-port 65536)   "3000"))))

(deftest test-get-port-negative
    "Check the function emender-jenkins.core/get-port."
    (testing "the function emender-jenkins.core/get-port."
        (is (thrown? AssertionError (get-port "0")))
        (is (thrown? AssertionError (get-port "-1")))
        (is (thrown? AssertionError (get-port "-2")))
        (is (thrown? AssertionError (get-port "65536")))
        (is (thrown? AssertionError (get-port "65537")))
        (is (thrown? AssertionError (get-port "1000000")))))

(deftest test-get-and-check-port
    "Check the function emender-jenkins.core/get-and-check-port."
    (testing "the function emender-jenkins.core/get-and-check-port."
        (is (= (get-and-check-port "1")     "1"))
        (is (= (get-and-check-port "2")     "2"))
        (is (= (get-and-check-port "65534") "65534"))
        (is (= (get-and-check-port "65535") "65535"))))

(deftest test-get-and-check-port-negative
    "Check the function emender-jenkins.core/get-and-check-port."
    (testing "the function emender-jenkins.core/get-and-check-port."
        (is (thrown? AssertionError (get-and-check-port "-1")))
        (is (thrown? AssertionError (get-and-check-port "0")))
        (is (thrown? AssertionError (get-and-check-port "65536")))
        (is (thrown? AssertionError (get-and-check-port "65537")))
        (is (thrown? AssertionError (get-and-check-port "1000000")))))

(deftest test-start-server-on-regular-machine-positive-1
    (testing "emender-jenkins.core/start-server"
        ; use mock instead of jetty/run-jetty
        (with-redefs [jetty/run-jetty (fn [app port] port)]
            (is (= {:port 1}     (start-server-on-regular-machine app "1")))
            (is (= {:port 2}     (start-server-on-regular-machine app "2")))
            (is (= {:port 1000}  (start-server-on-regular-machine app "1000")))
            (is (= {:port 65534} (start-server-on-regular-machine app "65534")))
            (is (= {:port 65535} (start-server-on-regular-machine app "65535"))))))

(deftest test-start-server-on-regular-machine-positive-2
    (testing "emender-jenkins.core/start-server"
        ; use mock instead of jetty/run-jetty
        (with-redefs [jetty/run-jetty (fn [app port] app)]
            (is (= app (start-server-on-regular-machine app "1")))
            (is (= app (start-server-on-regular-machine app "2")))
            (is (= app (start-server-on-regular-machine app "1000")))
            (is (= app (start-server-on-regular-machine app "65534")))
            (is (= app (start-server-on-regular-machine app "65535"))))))

(deftest test-start-server-on-openshift-positive-1
    (testing "emender-jenkins.core/start-server"
        ; use mock instead of jetty/run-jetty
        (with-redefs [jetty/run-jetty (fn [app params] params)]
            (is (= {:host "host" :port 1}     (start-server-on-openshift app "1"     "host")))
            (is (= {:host "host" :port 2}     (start-server-on-openshift app "2"     "host")))
            (is (= {:host "host" :port 1000}  (start-server-on-openshift app "1000"  "host")))
            (is (= {:host "host" :port 65534} (start-server-on-openshift app "65534" "host")))
            (is (= {:host "host" :port 65535} (start-server-on-openshift app "65535" "host"))))))

(deftest test-start-server-on-openshift-positive-2
    (testing "emender-jenkins.core/start-server"
        ; use mock instead of jetty/run-jetty
        (with-redefs [jetty/run-jetty (fn [app params] app)]
            (is (= app (start-server-on-openshift app "1" "host")))
            (is (= app (start-server-on-openshift app "2" "host")))
            (is (= app (start-server-on-openshift app "1000" "host")))
            (is (= app (start-server-on-openshift app "65534" "host")))
            (is (= app (start-server-on-openshift app "65535" "host"))))))

(deftest test-start-server-1
    (testing "emender-jenkins.core/start-server"
        ; use mock instead of jetty/run-jetty
        (with-redefs [jetty/run-jetty (fn [app port] port)]
            (is (= {:port 1}     (start-server nil "1" nil nil)))
            (is (= {:port 2}     (start-server nil "2" nil nil)))
            (is (= {:port 1000}  (start-server nil "1000" nil nil)))
            (is (= {:port 65534} (start-server nil "65534" nil nil)))
            (is (= {:port 65535} (start-server nil "65535" nil nil))))))

(deftest test-start-server-2
    (testing "emender-jenkins.core/start-server"
        ; use mock instead of jetty/run-jetty
        (with-redefs [jetty/run-jetty (fn [app port] port)]
            (is (= {:host "host" :port 1}     (start-server nil "1"     "1"     "host")))
            (is (= {:host "host" :port 2}     (start-server nil "2"     "2"     "host")))
            (is (= {:host "host" :port 1000}  (start-server nil "1000"  "1000"  "host")))
            (is (= {:host "host" :port 65534} (start-server nil "65534" "65534" "host")))
            (is (= {:host "host" :port 65535} (start-server nil "65535" "65535" "host"))))))

(deftest test-show-help
    "Check the function emender-jenkins.core/show-help."
    (testing "the function emender-jenkins.core/show-help.")
        (let [options (cli/parse-opts nil cli-options)]
            (is (= (with-out-str (show-help (:summary options)))
                   (str "Usage:\n"
                        "  -h, --help                     show help\n"
                        "  -p, --port   PORT              port number on which Emender Jenkins should accepts requests\n"
                        "  -j, --jenkins-url url          url to Jenkins, for example: http://10.20.30.40:8080/\n"
                        "  -t, --test-jobs-suffix suffix  test jobs suffix, for example 'test'\n"
                        "  -f, --fetch-only               just start job fetcher once, then stop processing\n"
                        "  -c, --config                   just show the actual configuration\n")))))

(deftest test-main-1
    "Check the function emender-jenkins.core/main."
    (testing "the function emender-jenkins.core/main.")
        (are [x y] (= x y)
           (with-out-str (-main "-h"))
                   (str "Usage:\n"
                        "  -h, --help                     show help\n"
                        "  -p, --port   PORT              port number on which Emender Jenkins should accepts requests\n"
                        "  -j, --jenkins-url url          url to Jenkins, for example: http://10.20.30.40:8080/\n"
                        "  -t, --test-jobs-suffix suffix  test jobs suffix, for example 'test'\n"
                        "  -f, --fetch-only               just start job fetcher once, then stop processing\n"
                        "  -c, --config                   just show the actual configuration\n")
           (with-out-str (-main "--help"))
                   (str "Usage:\n"
                        "  -h, --help                     show help\n"
                        "  -p, --port   PORT              port number on which Emender Jenkins should accepts requests\n"
                        "  -j, --jenkins-url url          url to Jenkins, for example: http://10.20.30.40:8080/\n"
                        "  -t, --test-jobs-suffix suffix  test jobs suffix, for example 'test'\n"
                        "  -f, --fetch-only               just start job fetcher once, then stop processing\n"
                        "  -c, --config                   just show the actual configuration\n")))

