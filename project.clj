(defproject emender-jenkins "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                   [org.clojure/tools.cli "0.3.1"]
                   [ring/ring-core "1.3.2"]
                   [ring/ring-jetty-adapter "1.3.2"]
                   [org.clojure/data.json "0.2.5"]
                   [clj-http "2.0.0"]
                   [clj-jgit "0.8.0"]
                   [clj-rm-rf "1.0.0-SNAPSHOT"]
                   [clojure-ini "0.0.1"]
                   [hiccup "1.0.4"]]
    :dev-dependencies [[lein-ring "0.8.10"]]
    :plugins [[lein-ring "0.8.10"]
              [codox "0.8.11"]
              [test2junit "1.1.0"]
              [lein-cloverage "1.0.2"]]
    :main ^:skip-aot emender-jenkins.core
    :ring {:handler emender-jenkins.core/app
           :ssl? true
           :ssl-port 8443
           :keystore "keystore"
           :key-password "changeit"}
    :target-path "target/%s"
    :profiles {:uberjar {:aot :all}})
