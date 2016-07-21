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

(ns emender-jenkins.process-info)

(defn get-current-pid
    []
    (-> (java.lang.management.ManagementFactory/getRuntimeMXBean)
        (.getName)
        (clojure.string/split #"@")
        (first)))

(defn read-properties
    []
    {:java-version    (System/getProperty "java.version")
     :java-class-path (System/getProperty "java.class.path")
     :java-home       (System/getProperty "java.home")
     :java-vendor     (System/getProperty "java.vendor")
     :os-arch         (System/getProperty "os.arch")
     :os-name         (System/getProperty "os.name")
     :os-version      (System/getProperty "os.version")
     :user-dir        (System/getProperty "user.dir")
     :user-home       (System/getProperty "user.home")
     :user-name       (System/getProperty "user.name")})

