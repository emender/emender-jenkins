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

(ns emender-jenkins.config-test
  (:require [clojure.test   :refer :all]
            [clojure.pprint :as     pprint]
            [emender-jenkins.config :refer :all]))

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

(deftest test-update-configuration-existence
    "Check that the emender-jenkins.config/update-configuration definition exists."
    (testing "if the emender-jenkins.config/update-configuration definition exists."
        (is (callable? 'emender-jenkins.config/update-configuration))))


(deftest test-load-configuration-from-ini-existence
    "Check that the emender-jenkins.config/load-configuration-from-ini definition exists."
    (testing "if the emender-jenkins.config/load-configuration-from-ini definition exists."
        (is (callable? 'emender-jenkins.config/load-configuration-from-ini))))


(deftest test-assoc-in-if-not-nil-existence
    "Check that the emender-jenkins.config/assoc-in-if-not-nil definition exists."
    (testing "if the emender-jenkins.config/assoc-in-if-not-nil definition exists."
        (is (callable? 'emender-jenkins.config/assoc-in-if-not-nil))))


(deftest test-override-options-by-cli-existence
    "Check that the emender-jenkins.config/override-options-by-cli definition exists."
    (testing "if the emender-jenkins.config/override-options-by-cli definition exists."
        (is (callable? 'emender-jenkins.config/override-options-by-cli))))


(deftest test-override-runtime-params-existence
    "Check that the emender-jenkins.config/override-runtime-params definition exists."
    (testing "if the emender-jenkins.config/override-runtime-params definition exists."
        (is (callable? 'emender-jenkins.config/override-runtime-params))))


(deftest test-print-configuration-existence
    "Check that the emender-jenkins.config/print-configuration definition exists."
    (testing "if the emender-jenkins.config/print-configuration definition exists."
        (is (callable? 'emender-jenkins.config/print-configuration))))


(deftest test-get-api-prefix-existence
    "Check that the emender-jenkins.config/get-api-prefix definition exists."
    (testing "if the emender-jenkins.config/get-api-prefix definition exists."
        (is (callable? 'emender-jenkins.config/get-api-prefix))))


(deftest test-verbose?-existence
    "Check that the emender-jenkins.config/verbose? definition exists."
    (testing "if the emender-jenkins.config/verbose? definition exists."
        (is (callable? 'emender-jenkins.config/verbose?))))


(deftest test-get-version-existence
    "Check that the emender-jenkins.config/get-version definition exists."
    (testing "if the emender-jenkins.config/get-version definition exists."
        (is (callable? 'emender-jenkins.config/get-version))))


(deftest test-get-jenkins-url-existence
    "Check that the emender-jenkins.config/get-jenkins-url definition exists."
    (testing "if the emender-jenkins.config/get-jenkins-url definition exists."
        (is (callable? 'emender-jenkins.config/get-jenkins-url))))


(deftest test-get-jenkins-auth-existence
    "Check that the emender-jenkins.config/get-jenkins-auth definition exists."
    (testing "if the emender-jenkins.config/get-jenkins-auth definition exists."
        (is (callable? 'emender-jenkins.config/get-jenkins-auth))))


(deftest test-pretty-print?-existence
    "Check that the emender-jenkins.config/pretty-print? definition exists."
    (testing "if the emender-jenkins.config/pretty-print? definition exists."
        (is (callable? 'emender-jenkins.config/pretty-print?))))


(deftest test-verbose-show-configuration?-existence
    "Check that the emender-jenkins.config/verbose-show-configuration? definition exists."
    (testing "if the emender-jenkins.config/verbose-show-configuration? definition exists."
        (is (callable? 'emender-jenkins.config/verbose-show-configuration?))))


(deftest test-include-jenkins-reply?-existence
    "Check that the emender-jenkins.config/include-jenkins-reply? definition exists."
    (testing "if the emender-jenkins.config/include-jenkins-reply? definition exists."
        (is (callable? 'emender-jenkins.config/include-jenkins-reply?))))


(deftest test-get-credentials-id-existence
    "Check that the emender-jenkins.config/get-credentials-id definition exists."
    (testing "if the emender-jenkins.config/get-credentials-id definition exists."
        (is (callable? 'emender-jenkins.config/get-credentials-id))))


(deftest test-get-test-jobs-prefix-existence
    "Check that the emender-jenkins.config/get-test-jobs-prefix definition exists."
    (testing "if the emender-jenkins.config/get-test-jobs-prefix definition exists."
        (is (callable? 'emender-jenkins.config/get-test-jobs-prefix))))


(deftest test-get-preview-test-jobs-suffix-existence
    "Check that the emender-jenkins.config/get-preview-test-jobs-suffix definition exists."
    (testing "if the emender-jenkins.config/get-preview-test-jobs-suffix definition exists."
        (is (callable? 'emender-jenkins.config/get-preview-test-jobs-suffix))))


(deftest test-get-stage-test-jobs-suffix-existence
    "Check that the emender-jenkins.config/get-stage-test-jobs-suffix definition exists."
    (testing "if the emender-jenkins.config/get-stage-test-jobs-suffix definition exists."
        (is (callable? 'emender-jenkins.config/get-stage-test-jobs-suffix))))


(deftest test-get-prod-test-jobs-suffix-existence
    "Check that the emender-jenkins.config/get-prod-test-jobs-suffix definition exists."
    (testing "if the emender-jenkins.config/get-prod-test-jobs-suffix definition exists."
        (is (callable? 'emender-jenkins.config/get-prod-test-jobs-suffix))))


(deftest test-get-started-on-str-existence
    "Check that the emender-jenkins.config/get-started-on-str definition exists."
    (testing "if the emender-jenkins.config/get-started-on-str definition exists."
        (is (callable? 'emender-jenkins.config/get-started-on-str))))


(deftest test-get-started-on-ms-existence
    "Check that the emender-jenkins.config/get-started-on-ms definition exists."
    (testing "if the emender-jenkins.config/get-started-on-ms definition exists."
        (is (callable? 'emender-jenkins.config/get-started-on-ms))))
;
; Test for function behaviours
;

(deftest test-assoc-in-if-not-nil-1
    "Check the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (assoc-in-if-not-nil {:first 1 :second 2} [:third] 3)        {:first 1 :second 2 :third 3}
            (assoc-in-if-not-nil {:first 1 :second 2} [:third] "string") {:first 1 :second 2 :third "string"}
            (assoc-in-if-not-nil {:first 1 :second 2} [:third] '())      {:first 1 :second 2 :third ()}
            (assoc-in-if-not-nil {:first 1 :second 2} [:third] [])       {:first 1 :second 2 :third []}
            (assoc-in-if-not-nil {:first 1 :second 2} [:third] '(1 2 3)) {:first 1 :second 2 :third '(1 2 3)}
            (assoc-in-if-not-nil {:first 1 :second 2} [:third] [1 2 3])  {:first 1 :second 2 :third [1 2 3]})))

(deftest test-assoc-in-if-not-nil-2
    "Check the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (assoc-in-if-not-nil {:first 1 :second 2} [:third] nil)   {:first 1 :second 2}
            (assoc-in-if-not-nil {:first 1 :second 2} [:third] false) {:first 1 :second 2}
            (assoc-in-if-not-nil {:first 1 :second 2} [:third] true)  {:first 1 :second 2 :third true})))

(deftest test-assoc-in-if-not-nil-3
    "Check the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (assoc-in-if-not-nil {:first 1 :second 2} [:first] 3)        {:second 2 :first 3}
            (assoc-in-if-not-nil {:first 1 :second 2} [:first] "string") {:second 2 :first "string"}
            (assoc-in-if-not-nil {:first 1 :second 2} [:first] '())      {:second 2 :first ()}
            (assoc-in-if-not-nil {:first 1 :second 2} [:first] [])       {:second 2 :first []}
            (assoc-in-if-not-nil {:first 1 :second 2} [:first] '(1 2 3)) {:second 2 :first '(1 2 3)}
            (assoc-in-if-not-nil {:first 1 :second 2} [:first] [1 2 3])  {:second 2 :first [1 2 3]})))

(deftest test-assoc-in-if-not-nil-4
    "Check the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (assoc-in-if-not-nil {:first 1 :second 2} [:second] 3)        {:first 1 :second 3}
            (assoc-in-if-not-nil {:first 1 :second 2} [:second] "string") {:first 1 :second "string"}
            (assoc-in-if-not-nil {:first 1 :second 2} [:second] '())      {:first 1 :second ()}
            (assoc-in-if-not-nil {:first 1 :second 2} [:second] [])       {:first 1 :second []}
            (assoc-in-if-not-nil {:first 1 :second 2} [:second] '(1 2 3)) {:first 1 :second '(1 2 3)}
            (assoc-in-if-not-nil {:first 1 :second 2} [:second] [1 2 3])  {:first 1 :second [1 2 3]})))

(deftest test-assoc-in-if-not-nil-5
    "Check the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (assoc-in-if-not-nil {:first {:left "L" :right "R"} :second {:left "L" :right "R"}} [:first :left] "***")   {:first {:left "***", :right "R"},   :second {:left "L",   :right "R"}}
            (assoc-in-if-not-nil {:first {:left "L" :right "R"} :second {:left "L" :right "R"}} [:first :right] "***")  {:first {:left "L",   :right "***"}, :second {:left "L",   :right "R"}}
            (assoc-in-if-not-nil {:first {:left "L" :right "R"} :second {:left "L" :right "R"}} [:second :left] "***")  {:first {:left "L",   :right "R"},   :second {:left "***", :right "R"}}
            (assoc-in-if-not-nil {:first {:left "L" :right "R"} :second {:left "L" :right "R"}} [:second :right] "***") {:first {:left "L",   :right "R"},   :second {:left "L",   :right "***"}})))

(deftest test-assoc-in-if-not-nil-6
    "Check the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (assoc-in-if-not-nil {:first {:left "L" :right "R"} :second {:left "L" :right "R"}} [:first :left] nil)   {:first {:left "L", :right "R"}, :second {:left "L", :right "R"}}
            (assoc-in-if-not-nil {:first {:left "L" :right "R"} :second {:left "L" :right "R"}} [:first :right] nil)  {:first {:left "L", :right "R"}, :second {:left "L", :right "R"}}
            (assoc-in-if-not-nil {:first {:left "L" :right "R"} :second {:left "L" :right "R"}} [:second :left] nil)  {:first {:left "L", :right "R"}, :second {:left "L", :right "R"}}
            (assoc-in-if-not-nil {:first {:left "L" :right "R"} :second {:left "L" :right "R"}} [:second :right] nil) {:first {:left "L", :right "R"}, :second {:left "L", :right "R"}})))

(deftest test-print-configuration
    "Check the behaviour of function emender-jenkins.config/print-configuration."
        ; use mock instead of clojure.pprint/pprint
        (with-redefs [pprint/pprint (fn [configuration] (str configuration))]
            (is (not (nil? (print-configuration {:first 1 :second 2}))))
            (is (= (type (print-configuration   {:first 1 :second 2})) java.lang.String))))

(deftest test-load-configuration-from-ini-1
    "Check the behaviour of function emender-jenkins.config/load-configuration-from-ini."
    (let [cfg (load-configuration-from-ini "test/test1.ini")]
        (is (not (nil? cfg)))))

(deftest test-load-configuration-from-ini-2
    "Check the behaviour of function emender-jenkins.config/load-configuration-from-ini."
    (let [cfg (load-configuration-from-ini "test/test1.ini")]
        (is (not (nil? (:info    cfg))))
        (is (not (nil? (:jenkins cfg))))
        (is (not (nil? (:jobs    cfg))))
        (is (not (nil? (:config  cfg))))
        (is (not (nil? (:api     cfg))))
        (is (not (nil? (:fetcher cfg))))
        (is (nil? (:other cfg)))))

(deftest test-load-configuration-from-ini-3
    "Check the behaviour of function emender-jenkins.config/load-configuration-from-ini."
    (let [cfg (load-configuration-from-ini "test/test1.ini")]
        (are [x y] (= x y)
            (-> cfg :info  :version)                   "0.1.0"
            (-> cfg :jenkins :jenkins-url)             "http://10.20.30.40:8080/"
            (-> cfg :jenkins :jenkins-job-prefix-url)  "job/"
            (-> cfg :jenkins :jenkins-job-list-url)    "api/json?tree=jobs[name,url,color,scm[userRemoteConfigs[url]],buildable,lastSuccessfulBuild[description]]"
            (-> cfg :jenkins :jenkins-auth)            "")))

(deftest test-get-api-prefix-1
    "Check the behaviour of function emender-jenkins.config/get-api-prefix."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (get-api-prefix {:configuration {:api {:prefix nil}}}) nil
            (get-api-prefix {:configuration {:api {:prefix "xyzzy"}}}) "xyzzy")))

(deftest test-get-api-prefix-2
    "Check the behaviour of function emender-jenkins.config/get-api-prefix."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (get-api-prefix {:configuration {:api nil}}) nil
            (get-api-prefix {:configuration nil}) nil
            (get-api-prefix nil) nil)))

(deftest test-get-version-1
    "Check the behaviour of function emender-jenkins.config/get-version."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (get-version {:configuration {:info {:version nil}}}) nil
            (get-version {:configuration {:info {:version "1.0"}}}) "1.0")))

(deftest test-get-version-2
    "Check the behaviour of function emender-jenkins.config/get-version."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (get-version {:configuration {:info nil}}) nil
            (get-version {:configuration nil}) nil
            (get-version nil) nil)))

(deftest test-get-jenkins-url-1
    "Check the behaviour of function emender-jenkins.config/get-jenkins-url."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (get-jenkins-url {:configuration {:jenkins {:jenkins-url nil}}}) nil
            (get-jenkins-url {:configuration {:jenkins {:jenkins-url "http://10.20.30.40:8080"}}}) "http://10.20.30.40:8080")))

(deftest test-get-jenkins-url-2
    "Check the behaviour of function emender-jenkins.config/get-jenkins-url."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (get-jenkins-url {:configuration {:jenkins nil}}) nil
            (get-jenkins-url {:configuration nil}) nil
            (get-jenkins-url nil) nil)))

(deftest test-get-jenkins-auth-1
    "Check the behaviour of function emender-jenkins.config/get-jenkins-auth."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (get-jenkins-auth {:configuration {:jenkins {:jenkins-auth nil}}}) nil
            (get-jenkins-auth {:configuration {:jenkins {:jenkins-auth "user:password"}}}) "user:password")))

(deftest test-get-jenkins-auth-2
    "Check the behaviour of function emender-jenkins.config/get-jenkins-auth."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (get-jenkins-auth {:configuration {:jenkins nil}}) nil
            (get-jenkins-auth {:configuration nil}) nil
            (get-jenkins-auth nil) nil)))

(deftest test-verbose?-1
    "Check the behaviour of function emender-jenkins.config/verbose?."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (verbose? {:configuration {:config {:verbose nil}}})   nil
            (verbose? {:configuration {:config {:verbose false}}}) false
            (verbose? {:configuration {:config {:verbose true}}})  true
            (verbose? {:configuration {:config {:verbose "xyzzy"}}}) "xyzzy")))

(deftest test-verbose?-2
    "Check the behaviour of function emender-jenkins.config/verbose?."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (verbose? {:configuration {:config nil}}) nil
            (verbose? {:configuration nil}) nil
            (verbose? nil) nil)))

(deftest test-pretty-print?-1
    "Check the behaviour of function emender-jenkins.config/pretty-print?."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (pretty-print? {:configuration {:config {:pretty-print nil}}})   nil
            (pretty-print? {:configuration {:config {:pretty-print false}}}) false
            (pretty-print? {:configuration {:config {:pretty-print true}}})  true
            (pretty-print? {:configuration {:config {:pretty-print "xyzzy"}}}) "xyzzy")))

(deftest test-pretty-print?-2
    "Check the behaviour of function emender-jenkins.config/pretty-print?."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (pretty-print? {:configuration {:config nil}}) nil
            (pretty-print? {:configuration nil}) nil
            (pretty-print? nil) nil)))

(deftest test-verbose-show-configuration?-1
    "Check the behaviour of function emender-jenkins.config/verbose-show-configuration?."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (verbose-show-configuration? {:configuration {:config {:verbose-show-configuration nil}}})   nil
            (verbose-show-configuration? {:configuration {:config {:verbose-show-configuration false}}}) false
            (verbose-show-configuration? {:configuration {:config {:verbose-show-configuration true}}})  true
            (verbose-show-configuration? {:configuration {:config {:verbose-show-configuration "xyzzy"}}}) "xyzzy")))

(deftest test-verbose-show-configuration?-2
    "Check the behaviour of function emender-jenkins.config/verbose-show-configuration?."
    (testing "the behaviour of function emender-jenkins.config/assoc-in-if-not-nil."
        (are [x y] (= x y)
            (verbose-show-configuration? {:configuration {:config nil}}) nil
            (verbose-show-configuration? {:configuration nil}) nil
            (verbose-show-configuration? nil) nil)))

(deftest test-include-jenkins-reply?-1
    "Check the behaviour of function emender-jenkins.config/include-jenkins-reply?."
    (testing "the behaviour of function emender-jenkins.config/include-jenkins-reply?.."
        (are [x y] (= x y)
            (include-jenkins-reply? {:configuration {:config {:include-jenkins-reply nil}}})   nil
            (include-jenkins-reply? {:configuration {:config {:include-jenkins-reply false}}}) false
            (include-jenkins-reply? {:configuration {:config {:include-jenkins-reply true}}})  true
            (include-jenkins-reply? {:configuration {:config {:include-jenkins-reply "xyzzy"}}}) "xyzzy")))

(deftest test-include-jenkins-reply?-2
    "Check the behaviour of function emender-jenkins.config/include-jenkins-reply?."
    (testing "the behaviour of function emender-jenkins.config/include-jenkins-reply?.."
        (are [x y] (= x y)
            (include-jenkins-reply? {:configuration {:config nil}}) nil
            (include-jenkins-reply? {:configuration nil}) nil
            (include-jenkins-reply? nil) nil)))

(deftest test-get-credentials-id-1
    "Check the behaviour of function emender-jenkins.config/get-credentials-id."
    (testing "the behaviour of function emender-jenkins.config/get-credentials-id."
        (are [x y] (= x y)
            (get-credentials-id {:configuration {:jenkins {:credentials-id nil}}})   nil
            (get-credentials-id {:configuration {:jenkins {:credentials-id false}}}) false
            (get-credentials-id {:configuration {:jenkins {:credentials-id true}}})  true
            (get-credentials-id {:configuration {:jenkins {:credentials-id "xyzzy"}}}) "xyzzy")))

(deftest test-get-credentials-id-2
    "Check the behaviour of function emender-jenkins.config/get-credentials-id."
    (testing "the behaviour of function emender-jenkins.config/get-credentials-id."
        (are [x y] (= x y)
            (get-credentials-id {:configuration {:jenkins nil}}) nil
            (get-credentials-id {:configuration nil}) nil
            (get-credentials-id nil) nil)))

(deftest test-get-test-jobs-prefix-1
    "Check the behaviour of function emender-jenkins.config/get-test-jobs-prefix."
    (testing "the behaviour of function emender-jenkins.config/get-test-jobs-prefix."
        (are [x y] (= x y)
            (get-test-jobs-prefix {:configuration {:jobs {:test-jobs-prefix nil}}})   nil
            (get-test-jobs-prefix {:configuration {:jobs {:test-jobs-prefix false}}}) false
            (get-test-jobs-prefix {:configuration {:jobs {:test-jobs-prefix true}}})  true
            (get-test-jobs-prefix {:configuration {:jobs {:test-jobs-prefix "xyzzy"}}}) "xyzzy")))

(deftest test-get-test-jobs-prefix-2
    "Check the behaviour of function emender-jenkins.config/get-test-jobs-prefix."
    (testing "the behaviour of function emender-jenkins.config/get-test-jobs-prefix."
        (are [x y] (= x y)
            (get-test-jobs-prefix {:configuration {:jobs nil}}) nil
            (get-test-jobs-prefix {:configuration nil}) nil
            (get-test-jobs-prefix nil) nil)))

(deftest test-get-preview-test-jobs-suffix-1
    "Check the behaviour of function emender-jenkins.config/get-preview-test-jobs-suffix."
    (testing "the behaviour of function emender-jenkins.config/get-preview-test-jobs-suffix."
        (are [x y] (= x y)
            (get-preview-test-jobs-suffix {:configuration {:jobs {:preview-test-jobs-suffix nil}}})   nil
            (get-preview-test-jobs-suffix {:configuration {:jobs {:preview-test-jobs-suffix false}}}) false
            (get-preview-test-jobs-suffix {:configuration {:jobs {:preview-test-jobs-suffix true}}})  true
            (get-preview-test-jobs-suffix {:configuration {:jobs {:preview-test-jobs-suffix "xyzzy"}}}) "xyzzy")))

(deftest test-get-preview-test-jobs-suffix-2
    "Check the behaviour of function emender-jenkins.config/get-preview-test-jobs-suffix."
    (testing "the behaviour of function emender-jenkins.config/get-preview-test-jobs-suffix."
        (are [x y] (= x y)
            (get-preview-test-jobs-suffix {:configuration {:jobs nil}}) nil
            (get-preview-test-jobs-suffix {:configuration nil}) nil
            (get-preview-test-jobs-suffix nil) nil)))

(deftest test-get-stage-test-jobs-suffix-1
    "Check the behaviour of function emender-jenkins.config/get-stage-test-jobs-suffix."
    (testing "the behaviour of function emender-jenkins.config/get-stage-test-jobs-suffix."
        (are [x y] (= x y)
            (get-stage-test-jobs-suffix {:configuration {:jobs {:stage-test-jobs-suffix nil}}})   nil
            (get-stage-test-jobs-suffix {:configuration {:jobs {:stage-test-jobs-suffix false}}}) false
            (get-stage-test-jobs-suffix {:configuration {:jobs {:stage-test-jobs-suffix true}}})  true
            (get-stage-test-jobs-suffix {:configuration {:jobs {:stage-test-jobs-suffix "xyzzy"}}}) "xyzzy")))

(deftest test-get-stage-test-jobs-suffix-2
    "Check the behaviour of function emender-jenkins.config/get-stage-test-jobs-suffix."
    (testing "the behaviour of function emender-jenkins.config/get-stage-test-jobs-suffix."
        (are [x y] (= x y)
            (get-stage-test-jobs-suffix {:configuration {:jobs nil}}) nil
            (get-stage-test-jobs-suffix {:configuration nil}) nil
            (get-stage-test-jobs-suffix nil) nil)))

(deftest test-get-prod-test-jobs-suffix-1
    "Check the behaviour of function emender-jenkins.config/get-prod-test-jobs-suffix."
    (testing "the behaviour of function emender-jenkins.config/get-prod-test-jobs-suffix."
        (are [x y] (= x y)
            (get-prod-test-jobs-suffix {:configuration {:jobs {:prod-test-jobs-suffix nil}}})   nil
            (get-prod-test-jobs-suffix {:configuration {:jobs {:prod-test-jobs-suffix false}}}) false
            (get-prod-test-jobs-suffix {:configuration {:jobs {:prod-test-jobs-suffix true}}})  true
            (get-prod-test-jobs-suffix {:configuration {:jobs {:prod-test-jobs-suffix "xyzzy"}}}) "xyzzy")))

(deftest test-get-prod-test-jobs-suffix-2
    "Check the behaviour of function emender-jenkins.config/get-prod-test-jobs-suffix."
    (testing "the behaviour of function emender-jenkins.config/get-prod-test-jobs-suffix."
        (are [x y] (= x y)
            (get-prod-test-jobs-suffix {:configuration {:jobs nil}}) nil
            (get-prod-test-jobs-suffix {:configuration nil}) nil
            (get-prod-test-jobs-suffix nil) nil)))

(deftest test-get-started-on-str-1
    "Check the behaviour of function emender-jenkins.config/get-started-on-str."
    (testing "the behaviour of function emender-jenkins.config/get-started-on-str."
        (are [x y] (= x y)
            (get-started-on-str {:configuration {:started-on nil}})   nil
            (get-started-on-str {:configuration {:started-on false}}) false
            (get-started-on-str {:configuration {:started-on true}})  true
            (get-started-on-str {:configuration {:started-on "10:20:30"}}) "10:20:30")))

(deftest test-get-started-on-str-2
    "Check the behaviour of function emender-jenkins.config/get-started-on-str."
    (testing "the behaviour of function emender-jenkins.config/get-started-on-str."
        (are [x y] (= x y)
            (get-started-on-str {:configuration {:started-on nil}}) nil
            (get-started-on-str {:configuration nil})               nil
            (get-started-on-str {:configuration nil})               nil)))

(deftest test-get-started-on-ms-1
    "Check the behaviour of function emender-jenkins.config/get-started-on-ms."
    (testing "the behaviour of function emender-jenkins.config/get-started-on-ms."
        (are [x y] (= x y)
            (get-started-on-ms {:configuration {:started-ms nil}})   nil
            (get-started-on-ms {:configuration {:started-ms false}}) false
            (get-started-on-ms {:configuration {:started-ms true}})  true
            (get-started-on-ms {:configuration {:started-ms 12345}}) 12345)))

(deftest test-get-started-on-ms-2
    "Check the behaviour of function emender-jenkins.config/get-started-on-ms."
    (testing "the behaviour of function emender-jenkins.config/get-started-on-ms."
        (are [x y] (= x y)
            (get-started-on-ms {:configuration {:started-ms nil}}) nil
            (get-started-on-ms {:configuration nil})               nil
            (get-started-on-ms {:configuration nil})               nil)))

(deftest test-get-in-queue-view-1
    "Check the behaviour of function emender-jenkins.config/get-in-queue-view."
    (testing "the behaviour of function emender-jenkins.config/get-in-queue-view."
        (are [x y] (= x y)
            (get-in-queue-view {:configuration {:jenkins {:in-queue-view nil}}}) nil
            (get-in-queue-view {:configuration {:jenkins {:in-queue-view false}}}) false
            (get-in-queue-view {:configuration {:jenkins {:in-queue-view true}}}) true
            (get-in-queue-view {:configuration {:jenkins {:in-queue-view "Queue"}}}) "Queue")))

(deftest test-get-in-queue-view-2
    "Check the behaviour of function emender-jenkins.config/get-in-queue-view."
    (testing "the behaviour of function emender-jenkins.config/get-in-queue-view."
        (are [x y] (= x y)
            (get-in-queue-view {:configuration {:jenkins {:in-queue-view nil}}}) nil
            (get-in-queue-view {:configuration {:jenkins nil}})                  nil
            (get-in-queue-view {:configuration nil})                             nil)))

(deftest test-get-currently-building-view-1
    "Check the behaviour of function emender-jenkins.config/get-currently-building-view."
    (testing "the behaviour of function emender-jenkins.config/get-currently-building-view."
        (are [x y] (= x y)
            (get-currently-building-view {:configuration {:jenkins {:currently-building-view nil}}}) nil
            (get-currently-building-view {:configuration {:jenkins {:currently-building-view false}}}) false
            (get-currently-building-view {:configuration {:jenkins {:currently-building-view true}}}) true
            (get-currently-building-view {:configuration {:jenkins {:currently-building-view "Queue"}}}) "Queue")))

(deftest test-get-currently-building-view-2
    "Check the behaviour of function emender-jenkins.config/get-currently-building-view."
    (testing "the behaviour of function emender-jenkins.config/get-currently-building-view."
        (are [x y] (= x y)
            (get-currently-building-view {:configuration {:jenkins {:currently-building-view nil}}}) nil
            (get-currently-building-view {:configuration {:jenkins nil}})                            nil
            (get-currently-building-view {:configuration nil})                                       nil)))

(deftest test-get-in-queue-url-1
    "Check the behaviour of function emender-jenkins.config/get-in-queue-url."
    (testing "the behaviour of function emender-jenkins.config/get-in-queue-url."
        (are [x y] (= x y)
            (get-in-queue-url {:configuration {:jenkins {:in-queue-url nil}}}) nil
            (get-in-queue-url {:configuration {:jenkins {:in-queue-url false}}}) false
            (get-in-queue-url {:configuration {:jenkins {:in-queue-url true}}}) true
            (get-in-queue-url {:configuration {:jenkins {:in-queue-url "Queue"}}}) "Queue")))

(deftest test-get-in-queue-url-2
    "Check the behaviour of function emender-jenkins.config/get-in-queue-url."
    (testing "the behaviour of function emender-jenkins.config/get-in-queue-url."
        (are [x y] (= x y)
            (get-in-queue-url {:configuration {:jenkins {:in-queue-url nil}}}) nil
            (get-in-queue-url {:configuration {:jenkins nil}})                 nil
            (get-in-queue-url {:configuration nil})                            nil)))

(deftest test-override-options-by-cli-1
    "Check the behaviour of function emender-jenkins.config/override-options-by-cli."
    (testing "the behaviour of function emender-jenkins.config/override-options-by-cli."
    (let [cfg (-> (load-configuration-from-ini "test/test1.ini")
                  (override-options-by-cli nil nil))]
        (are [x y] (= x y)
            (-> cfg :info  :version)                   "0.1.0"
            (-> cfg :jenkins :jenkins-url)             "http://10.20.30.40:8080/"
            (-> cfg :jenkins :jenkins-job-prefix-url)  "job/"
            (-> cfg :jenkins :jenkins-job-list-url)    "api/json?tree=jobs[name,url,color,scm[userRemoteConfigs[url]],buildable,lastSuccessfulBuild[description]]"
            (-> cfg :jenkins :jenkins-auth)            ""))))

(deftest test-override-options-by-cli-2
    "Check the behaviour of function emender-jenkins.config/override-options-by-cli."
    (testing "the behaviour of function emender-jenkins.config/override-options-by-cli."
    (let [cfg (-> (load-configuration-from-ini "test/test1.ini")
                  (override-options-by-cli "new-jenkins-url" nil))]
        (are [x y] (= x y)
            (-> cfg :info  :version)                   "0.1.0"
            (-> cfg :jenkins :jenkins-url)             "new-jenkins-url"
            (-> cfg :jenkins :jenkins-job-prefix-url)  "job/"
            (-> cfg :jenkins :jenkins-job-list-url)    "api/json?tree=jobs[name,url,color,scm[userRemoteConfigs[url]],buildable,lastSuccessfulBuild[description]]"
            (-> cfg :jenkins :jenkins-auth)            ""))))

(deftest test-override-options-by-cli-3
    "Check the behaviour of function emender-jenkins.config/override-options-by-cli."
    (testing "the behaviour of function emender-jenkins.config/override-options-by-cli."
    (let [cfg (-> (load-configuration-from-ini "test/test1.ini")
                  (override-options-by-cli nil "jobs-suffix"))]
        (are [x y] (= x y)
            (-> cfg :info  :version)                   "0.1.0"
            (-> cfg :jenkins :jenkins-url)             "http://10.20.30.40:8080/"
            (-> cfg :jenkins :jenkins-job-list-url)    "api/json?tree=jobs[name,url,color,scm[userRemoteConfigs[url]],buildable,lastSuccessfulBuild[description]]"
            (-> cfg :jenkins :jenkins-auth)            ""))))

(deftest test-override-options-by-cli-4
    "Check the behaviour of function emender-jenkins.config/override-options-by-cli."
    (testing "the behaviour of function emender-jenkins.config/override-options-by-cli."
    (let [cfg (-> (load-configuration-from-ini "test/test1.ini")
                  (override-options-by-cli "new-jenkins-url" "jobs-suffix"))]
        (are [x y] (= x y)
            (-> cfg :info  :version)                   "0.1.0"
            (-> cfg :jenkins :jenkins-url)             "new-jenkins-url"
            (-> cfg :jenkins :jenkins-job-list-url)    "api/json?tree=jobs[name,url,color,scm[userRemoteConfigs[url]],buildable,lastSuccessfulBuild[description]]"
            (-> cfg :jenkins :jenkins-auth)            ""))))

(deftest test-override-runtime-params-1
    "Check the behaviour of function emender-jenkins.config/override-runtime-params."
    (testing "the behaviour of function emender-jenkins.config/override-runtime-params."
    (let [cfg (-> (load-configuration-from-ini "test/test1.ini")
                  (override-runtime-params nil nil))]
        (are [x y] (= x y)
            (-> cfg :info  :version)                   "0.1.0"
            (-> cfg :jenkins :jenkins-url)             "http://10.20.30.40:8080/"
            (-> cfg :jenkins :jenkins-job-prefix-url)  "job/"
            (-> cfg :jenkins :jenkins-job-list-url)    "api/json?tree=jobs[name,url,color,scm[userRemoteConfigs[url]],buildable,lastSuccessfulBuild[description]]"
            (-> cfg :jenkins :jenkins-auth)            ""
            (-> cfg :started-on)                       nil
            (-> cfg :started-ms)                       nil))))

(deftest test-override-runtime-params-2
    "Check the behaviour of function emender-jenkins.config/override-runtime-params."
    (testing "the behaviour of function emender-jenkins.config/override-runtime-params."
    (let [cfg (-> (load-configuration-from-ini "test/test1.ini")
                  (override-runtime-params "10:20:30" nil))]
        (are [x y] (= x y)
            (-> cfg :info  :version)                   "0.1.0"
            (-> cfg :jenkins :jenkins-url)             "http://10.20.30.40:8080/"
            (-> cfg :jenkins :jenkins-job-prefix-url)  "job/"
            (-> cfg :jenkins :jenkins-job-list-url)    "api/json?tree=jobs[name,url,color,scm[userRemoteConfigs[url]],buildable,lastSuccessfulBuild[description]]"
            (-> cfg :jenkins :jenkins-auth)            ""
            (-> cfg :started-on)                       "10:20:30"
            (-> cfg :started-ms)                       nil))))

(deftest test-override-runtime-params-3
    "Check the behaviour of function emender-jenkins.config/override-runtime-params."
    (testing "the behaviour of function emender-jenkins.config/override-runtime-params."
    (let [cfg (-> (load-configuration-from-ini "test/test1.ini")
                  (override-runtime-params nil 123456))]
        (are [x y] (= x y)
            (-> cfg :info  :version)                   "0.1.0"
            (-> cfg :jenkins :jenkins-url)             "http://10.20.30.40:8080/"
            (-> cfg :jenkins :jenkins-job-prefix-url)  "job/"
            (-> cfg :jenkins :jenkins-job-list-url)    "api/json?tree=jobs[name,url,color,scm[userRemoteConfigs[url]],buildable,lastSuccessfulBuild[description]]"
            (-> cfg :jenkins :jenkins-auth)            ""
            (-> cfg :started-on)                       nil
            (-> cfg :started-ms)                       123456))))

