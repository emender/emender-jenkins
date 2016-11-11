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

(defproject emender-jenkins "0.1.0-SNAPSHOT"
    :description "FIXME: write description"
    :url "http://github.com/emender/emender-jenkins"
    :license {:name "Eclipse Public License"
              :url "http://www.eclipse.org/legal/epl-v10.html"}
    :dependencies [[org.clojure/clojure "1.7.0"]
                   [org.clojure/tools.cli "0.3.1"]
                   [org.clojure/data.csv  "0.1.2"]
                   [ring/ring-core "1.3.2"]
                   [ring/ring-jetty-adapter "1.3.2"]
                   [org.clojure/data.json "0.2.5"]
                   [clj-http "2.0.0"]
                   [clj-jgit "0.8.8"]
                   [clj-rm-rf "1.0.0-SNAPSHOT"]
                   [clojure-ini "0.0.1"]
                   [irclj "0.5.0-alpha4"]
                   [org.clojure/tools.logging "0.3.1"]
                   [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                      javax.jms/jms
                                                      com.sun.jmdk/jmxtools
                                                      com.sun.jmx/jmxri]]
                   [org.slf4j/slf4j-log4j12 "1.6.6"]
                   [org.clojars.tisnik/clj-jenkins-api "0.10.0-SNAPSHOT"]
                   [org.clojars.tisnik/clj-calendar "0.3.0-SNAPSHOT"]
                   [org.clojars.tisnik/clj-fileutils "0.2.0-SNAPSHOT"]]
    :dev-dependencies [[lein-ring "0.8.10"]]
    :plugins [[lein-ring "0.8.10"]
              [codox "0.8.11"]
              [test2junit "1.1.0"]
              [lein-cloverage "1.0.6"]]
    :main ^:skip-aot emender-jenkins.core
    :ring {:handler emender-jenkins.core/app
           :ssl? true
           :ssl-port 8443
           :keystore "keystore"
           :key-password "changeit"}
    :target-path "target/%s"
    :profiles {:uberjar {:aot :all}})

