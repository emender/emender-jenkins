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

(ns emender-jenkins.irc-bot-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.irc-bot :refer :all]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))

;
; Tests for function existence
;

(deftest test-message-to-channel?
    "Check that the emender-jenkins.irc-bot/message-to-channel? definition exists."
    (testing "if the emender-jenkins.irc-bot/message-to-channel? definition exists."
        (is (callable? 'emender-jenkins.irc-bot/message-to-channel?))))

(deftest test-message-for-me?
    "Check that the emender-jenkins.irc-bot/message-for-me? definition exists."
    (testing "if the emender-jenkins.irc-bot/message-for-me? definition exists."
        (is (callable? 'emender-jenkins.irc-bot/message-for-me?))))

(deftest test-create-reply
    "Check that the emender-jenkins.irc-bot/create-reply definition exists."
    (testing "if the emender-jenkins.irc-bot/create-reply definition exists."
        (is (callable? 'emender-jenkins.irc-bot/create-reply))))

(deftest test-prepare-reply-text
    "Check that the emender-jenkins.irc-bot/prepare-reply-text definition exists."
    (testing "if the emender-jenkins.irc-bot/prepare-reply-text definition exists."
        (is (callable? 'emender-jenkins.irc-bot/prepare-reply-text))))

