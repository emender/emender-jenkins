;
;  (C) Copyright 2016  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns emender-jenkins.irc-bot
    "Module containing IRC bot used to inform users about test results.")

(require '[irclj.core :as irc])

(def connection (atom nil))
(def bot-nick (atom nil))

(defn message-to-channel?
    [message]
    (.startsWith (:target message) "#"))

(defn message-for-me?
    [my-name message]
    (or (.startsWith (:target message) my-name)        ; private message
        (.startsWith (:text message) (str my-name ":")); direct message
    ))

(defn create-reply
    [incoming-message]
    (if (message-to-channel? incoming-message)
        incoming-message
        (assoc incoming-message :target (:nick incoming-message))))

(defn prepare-reply-text
    [incomming-message nick input-text]
    (let [in-channel? (message-to-channel? incomming-message)
          input       (if in-channel?
                          (subs input-text (+ 2 (count @bot-nick)))
                          input-text)
          prefix      (if in-channel? (str nick ": "))
          response    (condp = input
                          "help" "commands: schedule users time sunrise sunset"
                                 "?")]
        (str prefix response)))

(defn on-incoming-message
    [connection incoming-message]
    (let [{text    :text
           target  :target
           nick    :nick
           host    :host
           command :command} incoming-message]
           (println "Received message from" nick "to" target ":" text "(" host command ")")
           (println incoming-message)
           (if (message-for-me? @bot-nick incoming-message)
               (irc/reply connection (create-reply incoming-message)
                                     (prepare-reply-text incoming-message nick text)))))

(defn send-message
    [recipients target message-text]
    (let [message {:target target :command "PRIVMSG"}]
        (irc/reply @connection message (str recipients " " message-text))))

(defn start-irc-bot
    [server port channel nick]
    (println "Connecting to" server "on port" port)
    (let [conn (irc/connect server port nick
                            :callbacks {:privmsg on-incoming-message})]
        (println "Connected, joining to channel" channel)
        (reset! connection conn)
        (reset! bot-nick nick)
        (irc/join @connection channel)
        (println "Connected...")))

