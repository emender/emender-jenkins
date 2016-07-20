(ns emender-jenkins.config-loader)

(require '[clojure-ini.core :as clojure-ini])

(defn properties->map
    "Convert property entries into map."
    [properties]
    (into {}
        (for [[k v] properties]
              [(keyword k) (read-string v)])))

(defn load-property-file
    "Load configuration from the provided property file."
    [file-name]
    (with-open [reader (clojure.java.io/reader file-name)] 
        (let [properties (java.util.Properties.)]
            (.load properties reader)
            (properties->map properties))))

(defn load-configuration-file
    "Load configuration from the provided INI file."
    [file-name]
    (clojure-ini/read-ini file-name :keywordize? true))

