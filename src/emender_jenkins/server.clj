(ns emender-jenkins.server)

(require '[ring.util.response              :as http-response])

(defn handler
    "Handler that is called by Ring for all requests received from user(s)."
    [request]
    ;(config/load-all-configuration-options-if-necessary)
    ;(product-list/read-product-list-if-necessary config/load-product-list-from-database)
    ;(job-data-fetcher/load-books-if-necessary)
    (println "request URI: " (request :uri)))

