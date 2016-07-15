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

