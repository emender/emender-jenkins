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

