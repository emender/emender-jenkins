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

(ns emender-jenkins.results-test
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [emender-jenkins.results :refer :all]
            [clj-jenkins-api.jenkins-api :as jenkins-api]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))


(deftest test-render-edn-data-existence
    "Check that the emender-jenkins.results/render-edn-data definition exists."
    (testing "if the emender-jenkins.results/render-edn-data definition exists."
        (is (callable? 'emender-jenkins.results/render-edn-data))))


(deftest test-add-new-results-existence
    "Check that the emender-jenkins.results/add-new-results definition exists."
    (testing "if the emender-jenkins.results/add-new-results definition exists."
        (is (callable? 'emender-jenkins.results/add-new-results))))


(deftest test-store-results-existence
    "Check that the emender-jenkins.results/store-results definition exists."
    (testing "if the emender-jenkins.results/store-results definition exists."
        (is (callable? 'emender-jenkins.results/store-results))))


(deftest test-job-name->product-name-existence
    "Check that the emender-jenkins.results/job-name->product-name definition exists."
    (testing "if the emender-jenkins.results/job-name->product-name definition exists."
        (is (callable? 'emender-jenkins.results/job-name->product-name))))


(deftest test-job-name->version-existence
    "Check that the emender-jenkins.results/job-name->version definition exists."
    (testing "if the emender-jenkins.results/job-name->version definition exists."
        (is (callable? 'emender-jenkins.results/job-name->version))))


(deftest test-job-name->book-name-existence
    "Check that the emender-jenkins.results/job-name->book-name definition exists."
    (testing "if the emender-jenkins.results/job-name->book-name definition exists."
        (is (callable? 'emender-jenkins.results/job-name->book-name))))


(deftest test-job-name->environment-existence
    "Check that the emender-jenkins.results/job-name->environment definition exists."
    (testing "if the emender-jenkins.results/job-name->environment definition exists."
        (is (callable? 'emender-jenkins.results/job-name->environment))))


(deftest test-compute-job-status-existence
    "Check that the emender-jenkins.results/compute-job-status definition exists."
    (testing "if the emender-jenkins.results/compute-job-status definition exists."
        (is (callable? 'emender-jenkins.results/compute-job-status))))


(deftest test-compute-job-disabled-existence
    "Check that the emender-jenkins.results/compute-job-disabled definition exists."
    (testing "if the emender-jenkins.results/compute-job-disabled definition exists."
        (is (callable? 'emender-jenkins.results/compute-job-disabled))))


(deftest test-parse-test-results-existence
    "Check that the emender-jenkins.results/parse-test-results definition exists."
    (testing "if the emender-jenkins.results/parse-test-results definition exists."
        (is (callable? 'emender-jenkins.results/parse-test-results))))


(deftest test-test-summary-existence
    "Check that the emender-jenkins.results/test-summary definition exists."
    (testing "if the emender-jenkins.results/test-summary definition exists."
        (is (callable? 'emender-jenkins.results/test-summary))))


(deftest test-read-update-job-info-existence
    "Check that the emender-jenkins.results/read-update-job-info definition exists."
    (testing "if the emender-jenkins.results/read-update-job-info definition exists."
        (is (callable? 'emender-jenkins.results/read-update-job-info))))


(deftest test-test-job?-existence
    "Check that the emender-jenkins.results/test-job? definition exists."
    (testing "if the emender-jenkins.results/test-job? definition exists."
        (is (callable? 'emender-jenkins.results/test-job?))))


(deftest test-filter-test-jobs-existence
    "Check that the emender-jenkins.results/filter-test-jobs definition exists."
    (testing "if the emender-jenkins.results/filter-test-jobs definition exists."
        (is (callable? 'emender-jenkins.results/filter-test-jobs))))


(deftest test-read-list-of-test-jobs-existence
    "Check that the emender-jenkins.results/read-list-of-test-jobs definition exists."
    (testing "if the emender-jenkins.results/read-list-of-test-jobs definition exists."
        (is (callable? 'emender-jenkins.results/read-list-of-test-jobs))))


(deftest test-reload-all-results-existence
    "Check that the emender-jenkins.results/reload-all-results definition exists."
    (testing "if the emender-jenkins.results/reload-all-results definition exists."
        (is (callable? 'emender-jenkins.results/reload-all-results))))


(deftest test-select-jobs-existence
    "Check that the emender-jenkins.results/select-jobs definition exists."
    (testing "if the emender-jenkins.results/select-jobs definition exists."
        (is (callable? 'emender-jenkins.results/select-jobs))))


(deftest test-read-job-results-existence
    "Check that the emender-jenkins.results/read-job-results definition exists."
    (testing "if the emender-jenkins.results/read-job-results definition exists."
        (is (callable? 'emender-jenkins.results/read-job-results))))


(deftest test-all-products-existence
    "Check that the emender-jenkins.results/all-products definition exists."
    (testing "if the emender-jenkins.results/all-products definition exists."
        (is (callable? 'emender-jenkins.results/all-products))))


(deftest test-versions-per-products-existence
    "Check that the emender-jenkins.results/versions-per-products definition exists."
    (testing "if the emender-jenkins.results/versions-per-products definition exists."
        (is (callable? 'emender-jenkins.results/versions-per-products))))


(deftest test-books-for-product-version-existence
    "Check that the emender-jenkins.results/books-for-product-version definition exists."
    (testing "if the emender-jenkins.results/books-for-product-version definition exists."
        (is (callable? 'emender-jenkins.results/books-for-product-version))))


(deftest test-job-for-environment-existence
    "Check that the emender-jenkins.results/job-for-environment definition exists."
    (testing "if the emender-jenkins.results/job-for-environment definition exists."
        (is (callable? 'emender-jenkins.results/job-for-environment))))


(deftest test-select-results-for-book-existence
    "Check that the emender-jenkins.results/select-results-for-book definition exists."
    (testing "if the emender-jenkins.results/select-results-for-book definition exists."
        (is (callable? 'emender-jenkins.results/select-results-for-book))))


(deftest test-select-results-for-product-version-existence
    "Check that the emender-jenkins.results/select-results-for-product-version definition exists."
    (testing "if the emender-jenkins.results/select-results-for-product-version definition exists."
        (is (callable? 'emender-jenkins.results/select-results-for-product-version))))


(deftest test-select-results-for-product-existence
    "Check that the emender-jenkins.results/select-results-for-product definition exists."
    (testing "if the emender-jenkins.results/select-results-for-product definition exists."
        (is (callable? 'emender-jenkins.results/select-results-for-product))))


(deftest test-get-job-results-existence
    "Check that the emender-jenkins.results/get-job-results definition exists."
    (testing "if the emender-jenkins.results/get-job-results definition exists."
        (is (callable? 'emender-jenkins.results/get-job-results))))


(deftest test-get-job-results-as-tree-existence
    "Check that the emender-jenkins.results/get-job-results-as-tree definition exists."
    (testing "if the emender-jenkins.results/get-job-results-as-tree definition exists."
        (is (callable? 'emender-jenkins.results/get-job-results-as-tree))))


(deftest test-find-job-with-name-existence
    "Check that the emender-jenkins.results/find-job-with-name definition exists."
    (testing "if the emender-jenkins.results/find-job-with-name definition exists."
        (is (callable? 'emender-jenkins.results/find-job-with-name))))


(deftest test-job-exists?-existence
    "Check that the emender-jenkins.results/job-exists? definition exists."
    (testing "if the emender-jenkins.results/job-exists? definition exists."
        (is (callable? 'emender-jenkins.results/job-exists?))))

;
; Test behaviour of functions
;

(deftest test-job-name->product-name-1
    "Check the emender-jenkins.results/job-name->product-name function."
    (testing "the emender-jenkins.results/job-name->product-name function."
        (are [x y] (= x y)
            "Product"                  (job-name->product-name "test-Product-1-Book1-en-US (preview)")
            "Product Name"             (job-name->product-name "test-Product_Name-1-Book1-en-US (preview)")
            "Red Hat Enterprise Linux" (job-name->product-name "test-Red_Hat_Enterprise_Linux-6.2-Book_Name_1-en-US (preview)")
            "Red Hat Enterprise Linux" (job-name->product-name "test-Red_Hat_Enterprise_Linux-7-Book_Name_1-en-US (preview)"))))

(deftest test-job-name->product-name-2
    "Check the emender-jenkins.results/job-name->product-name function."
    (testing "the emender-jenkins.results/job-name->product-name function."
        (is (= "unknown"   (job-name->product-name nil)))))

(deftest test-job-name->version-1
    "Check the emender-jenkins.results/job-name->version function."
    (testing "the emender-jenkins.results/job-name->version function."
        (are [x y] (= x y)
            "1"   (job-name->version "test-Product-1-Book1-en-US (preview)")
            "1"   (job-name->version "test-Product_Name-1-Book1-en-US (preview)")
            "6.2" (job-name->version "test-Red_Hat_Enterprise_Linux-6.2-Book_Name_1-en-US (preview)")
            "7"   (job-name->version "test-Red_Hat_Enterprise_Linux-7-Book_Name_1-en-US (preview)"))))

(deftest test-job-name->version-2
    "Check the emender-jenkins.results/job-name->version function."
    (testing "the emender-jenkins.results/job-name->version function."
        (are [x y] (= x y)
            "unknown" (job-name->version "test-Product-Book1-en-US (preview)")
            "unknown" (job-name->version "test-Product_Name-bad-Book1-en-US (preview)")
            "unknown" (job-name->version "test-Red_Hat_Enterprise_Linux-6.beta-Book_Name_1-en-US (preview)")
            "unknown" (job-name->version "test-Red_Hat_Enterprise_Linux-Book_Name_1-en-US (preview)"))))

(deftest test-job-name->version-3
    "Check the emender-jenkins.results/job-name->version function."
    (testing "the emender-jenkins.results/job-name->version function."
        (are [x y] (= x y)
            "unknown" (job-name->version "test-Product-Book1-en-US")
            "unknown" (job-name->version "test-Product_Book1")
            "unknown" (job-name->version "test-Product")
            "unknown" (job-name->version "test"))))

(deftest test-job-name->version-4
    "Check the emender-jenkins.results/job-name->version function."
    (testing "the emender-jenkins.results/job-name->version function."
        (is (= "unknown" (job-name->version nil)))))

(deftest test-job-name->book-name-1
    "Check the emender-jenkins.results/job-name->book-name function."
    (testing "the emender-jenkins.results/job-name->book-name function."
        (are [x y] (= x y)
            "Book1"       (job-name->book-name "test-Product-1-Book1-en-US (preview)")
            "Book1"       (job-name->book-name "test-Product_Name-1-Book1-en-US (preview)")
            "Book Name 1" (job-name->book-name "test-Red_Hat_Enterprise_Linux-6.2-Book_Name_1-en-US (preview)")
            "Book Name 1" (job-name->book-name "test-Red_Hat_Enterprise_Linux-7-Book_Name_1-en-US (preview)"))))

(deftest test-job-name->book-name-2
    "Check the emender-jenkins.results/job-name->book-name function."
    (testing "the emender-jenkins.results/job-name->book-name function."
        (are [x y] (= x y)
            "unknown"     (job-name->book-name "test-Product-1 (preview)")
            "unknown"     (job-name->book-name nil))))

(deftest test-job-name->environment-preview
    "Check the emender-jenkins.results/job-name->environment function."
    (testing "the emender-jenkins.results/job-name->environment function."
        (are [x y] (= x y)
            :preview (job-name->environment "test-Product-1-Book1-en-US (preview)" "(preview)" "(stage)" "(prod)")
            :preview (job-name->environment "test-Product_Name-1-Book1-en-US (preview)" "(preview)" "(stage)" "(prod)")
            :preview (job-name->environment "test-Red_Hat_Enterprise_Linux-6.2-Book_Name_1-en-US (preview)" "(preview)" "(stage)" "(prod)")
            :preview (job-name->environment "test-Red_Hat_Enterprise_Linux-7-Book_Name_1-en-US (preview)" "(preview)" "(stage)" "(prod)"))))

(deftest test-job-name->environment-stage
    "Check the emender-jenkins.results/job-name->environment function."
    (testing "the emender-jenkins.results/job-name->environment function."
        (are [x y] (= x y)
            :stage (job-name->environment "test-Product-1-Book1-en-US (stage)" "(preview)" "(stage)" "(prod)")
            :stage (job-name->environment "test-Product_Name-1-Book1-en-US (stage)" "(preview)" "(stage)" "(prod)")
            :stage (job-name->environment "test-Red_Hat_Enterprise_Linux-6.2-Book_Name_1-en-US (stage)" "(preview)" "(stage)" "(prod)")
            :stage (job-name->environment "test-Red_Hat_Enterprise_Linux-7-Book_Name_1-en-US (stage)" "(preview)" "(stage)" "(prod)"))))

(deftest test-job-name->environment-prod
    "Check the emender-jenkins.results/job-name->environment function."
    (testing "the emender-jenkins.results/job-name->environment function."
        (are [x y] (= x y)
            :prod (job-name->environment "test-Product-1-Book1-en-US (prod)" "(preview)" "(stage)" "(prod)")
            :prod (job-name->environment "test-Product_Name-1-Book1-en-US (prod)" "(preview)" "(stage)" "(prod)")
            :prod (job-name->environment "test-Red_Hat_Enterprise_Linux-6.2-Book_Name_1-en-US (prod)" "(preview)" "(stage)" "(prod)")
            :prod (job-name->environment "test-Red_Hat_Enterprise_Linux-7-Book_Name_1-en-US (prod)" "(preview)" "(stage)" "(prod)"))))

(deftest test-job-name->environment-unknown
    "Check the emender-jenkins.results/job-name->environment function."
    (testing "the emender-jenkins.results/job-name->environment function."
        (are [x y] (= x y)
            nil (job-name->environment "test-Product-1-Book1-en-US (unknown)" "(preview)" "(stage)" "(prod)")
            nil (job-name->environment "test-Product_Name-1-Book1-en-US (unknown)" "(preview)" "(stage)" "(prod)")
            nil (job-name->environment "test-Red_Hat_Enterprise_Linux-6.2-Book_Name_1-en-US (unknown)" "(preview)" "(stage)" "(prod)")
            nil (job-name->environment "test-Red_Hat_Enterprise_Linux-7-Book_Name_1-en-US (unknown)" "(preview)" "(stage)" "(prod)"))))

(deftest test-compute-job-status
    "Check the emender-jenkins.results/compute-job-status function."
    (testing "the emender-jenkins.results/compute-job-status function."
        (are [x y] (= x y)
            :disabled        (compute-job-status true nil)
            :ok              (compute-job-status "blue" true)
            :unstable        (compute-job-status "yellow" true)
            :disabled        (compute-job-status "disabled" true)
            :failure         (compute-job-status "xyzzy" true)
            :does-not-exists (compute-job-status nil true))))

(deftest test-compute-job-disabled
    "Check the emender-jenkins.results/compute-job-disabled function."
    (testing "the emender-jenkins.results/compute-job-disabled function."
        (are [x y] (= x y)
            true  (compute-job-disabled true nil)
            false (compute-job-disabled "blue" true)
            false (compute-job-disabled "yellow" true)
            true  (compute-job-disabled "disabled" true)
            false (compute-job-disabled "xyzzy" true)
            true  (compute-job-disabled nil true))))

(deftest test-parse-test-results-negative
    "Check the emender-jenkins.results/parse-test-results function."
    (testing "the emender-jenkins.results/parse-test-results function."
        (are [x y] (= x y)
            nil   (parse-test-results "")
            nil   (parse-test-results "xyzzy")
            nil   (parse-test-results "Total: "))))

(deftest test-get-building-jobs-url
    "Check the function emender-jenkins.results/get-building-jobs-url."
    (let [configuration {:jenkins {:currently-building-view "Building"
                                             :jenkins-url "http://10.20.30.40:8080/"}}]
         (is (= "http://10.20.30.40:8080/view/Building/" (get-building-jobs-url configuration)))))

(deftest test-get-job-in-queue-url
    "Check the function emender-jenkins.results/get-job-in-queue-url."
    (let [configuration {:jenkins {:in-queue-url "queue/api/json?tree=items[task[name]]"
                                             :jenkins-url  "http://10.20.30.40:8080/"}}]
         (is (= "http://10.20.30.40:8080/queue/api/json?tree=items[task[name]]" (get-jobs-in-queue-url configuration)))))

(deftest test-get-job-name-from-queue-info
    "Check the function emender-jenkins.results/get-job-name-from-queue-info."
    (are [x y] (= x (get-job-name-from-queue-info y))
        nil        nil
        nil        {"something" "else"}
        nil        {"task" nil}
        nil        {"task" {"name" nil}}
        ""         {"task" {"name" ""}}
        "job-name" {"task" {"name" "job-name"}}))


(def building-jobs-jenkins-response
    (str "{\"_class\":\"hudson.model.ListView\","
          "\"jobs\":["
          "{\"_class\":\"hudson.model.FreeStyleProject\",\"name\":\"test-Example_Documentation-1.0-Guide-en-US (preview)\",\"url\":\"http://10.20.30.40:8080/job/test-Example_Documentation-1.0-Guide-en-US%20(preview)/\",\"buildable\":true,\"color\":\"yellow_anime\",\"lastSuccessfulBuild\":{\"_class\":\"hudson.model.FreeStyleBuild\",\"description\":\"Total: 4  Passed: 1  Failed: 3\"},\"scm\":{\"_class\":\"hudson.plugins.git.GitSCM\",\"userRemoteConfigs\":[{\"url\":\"git@git.domain.name:example-documentation/guide.git\"}]}},"
          "{\"_class\":\"hudson.model.FreeStyleProject\",\"name\":\"test-Example_Documentation-1.0-Guide-en-US (stage)\",\"url\":\"http://10.20.30.40:8080/job/test-Example_Documentation-1.0-Guide-en-US%20(stage)/\",\"buildable\":true,\"color\":\"yellow_anime\",\"lastSuccessfulBuild\":{\"_class\":\"hudson.model.FreeStyleBuild\",\"description\":\"Total: 4  Passed: 1  Failed: 3\"},\"scm\":{\"_class\":\"hudson.plugins.git.GitSCM\",\"userRemoteConfigs\":[{\"url\":\"git@git.domain.name:example-documentation/guide.git\"}]}}"
          "]}"))

(def jobs-in-queue-expected-jenkins-response
    (str "{\"_class\":\"hudson.model.Queue\","
          "\"items\":["
             "{\"_class\":\"hudson.model.Queue$BlockedItem\",\"task\":{\"_class\":\"hudson.model.FreeStyleProject\",\"name\":\"test-Example_Documentation-1.0-Guide-en-US (preview)\"}}"
             "{\"_class\":\"hudson.model.Queue$BlockedItem\",\"task\":{\"_class\":\"hudson.model.FreeStyleProject\",\"name\":\"test-Example_Documentation-1.0-Guide-en-US (stage)  \"}}"
         "]}"))

(def read-currently-building-jobs-expected-result
   [{"_class" "hudson.model.FreeStyleProject",
     "name" "test-Example_Documentation-1.0-Guide-en-US (preview)",
     "url"
     "http://10.20.30.40:8080/job/test-Example_Documentation-1.0-Guide-en-US%20(preview)/",
     "buildable" true,
     "color" "yellow_anime",
     "lastSuccessfulBuild"
     {"_class" "hudson.model.FreeStyleBuild",
      "description" "Total: 4  Passed: 1  Failed: 3"},
      "scm"
     {"_class" "hudson.plugins.git.GitSCM",
      "userRemoteConfigs"
      [{"url" "git@git.domain.name:example-documentation/guide.git"}]}}
    {"_class" "hudson.model.FreeStyleProject",
     "name" "test-Example_Documentation-1.0-Guide-en-US (stage)",
     "url"
     "http://10.20.30.40:8080/job/test-Example_Documentation-1.0-Guide-en-US%20(stage)/",
     "buildable" true,
     "color" "yellow_anime",
     "lastSuccessfulBuild"
     {"_class" "hudson.model.FreeStyleBuild",
      "description" "Total: 4  Passed: 1  Failed: 3"},
     "scm"
     {"_class" "hudson.plugins.git.GitSCM",
      "userRemoteConfigs"
      [{"url" "git@git.domain.name:example-documentation/guide.git"}]}}])

(deftest test-read-queue-info-from-jenkins
    "Check the function emender-jenkins.results/read-queue-info-from-jenkins."
    (testing "the function emender-jenkins.results/read-queue-info-from-jenkins."
    (with-redefs [jenkins-api/get-command (fn [url] jobs-in-queue-expected-jenkins-response)]
        (is (= (read-queue-info-from-jenkins "url")
             [{"_class" "hudson.model.Queue$BlockedItem" "task" {"_class" "hudson.model.FreeStyleProject" "name" "test-Example_Documentation-1.0-Guide-en-US (preview)"}}
              {"_class" "hudson.model.Queue$BlockedItem" "task" {"_class" "hudson.model.FreeStyleProject" "name" "test-Example_Documentation-1.0-Guide-en-US (stage)  "}}]
    )))))

(deftest test-read-building-jobs-from-jenkins
    "Check the function emender-jenkins.results/read-building-jobs-from-jenkins."
    (with-redefs [jenkins-api/get-command (fn [all-jobs-url] building-jobs-jenkins-response)]
        (is (= (read-building-jobs-from-jenkins "url" "job-list-part")
               [{"_class" "hudson.model.FreeStyleProject",
                 "name" "test-Example_Documentation-1.0-Guide-en-US (preview)",
                 "url"
                 "http://10.20.30.40:8080/job/test-Example_Documentation-1.0-Guide-en-US%20(preview)/",
                 "buildable" true,
                 "color" "yellow_anime",
                 "lastSuccessfulBuild"
                 {"_class" "hudson.model.FreeStyleBuild",
                  "description" "Total: 4  Passed: 1  Failed: 3"},
                  "scm"
                 {"_class" "hudson.plugins.git.GitSCM",
                  "userRemoteConfigs"
                  [{"url" "git@git.domain.name:example-documentation/guide.git"}]}}
                {"_class" "hudson.model.FreeStyleProject",
                 "name" "test-Example_Documentation-1.0-Guide-en-US (stage)",
                 "url"
                 "http://10.20.30.40:8080/job/test-Example_Documentation-1.0-Guide-en-US%20(stage)/",
                 "buildable" true,
                 "color" "yellow_anime",
                 "lastSuccessfulBuild"
                 {"_class" "hudson.model.FreeStyleBuild",
                  "description" "Total: 4  Passed: 1  Failed: 3"},
                 "scm"
                 {"_class" "hudson.plugins.git.GitSCM",
                  "userRemoteConfigs"
                  [{"url" "git@git.domain.name:example-documentation/guide.git"}]}}]))))

(deftest test-read-building-jobs-from-jenkins-exception-catching
    "Check the function emender-jenkins.results/read-building-jobs-from-jenkins."
    (with-redefs [jenkins-api/read-list-of-all-jobs (fn [url job-list-part] (throw (new Exception "Exception!")))]
        (is (= (read-building-jobs-from-jenkins "url" "job-list-part") nil))))

(deftest test-create-currently-building-jobs-response
    "Check the function emender-jenkins.results/create-currently-building-jobs-response"
    (with-redefs [jenkins-api/get-command (fn [all-jobs-url] building-jobs-jenkins-response)]
        (is (= (create-currently-building-jobs-response (read-building-jobs-from-jenkins "url" "job-list-part"))
               ["test-Example_Documentation-1.0-Guide-en-US (preview)" "test-Example_Documentation-1.0-Guide-en-US (stage)"]))))

(deftest test-read-currently-building-jobs
    "Check the function emender-jenkins.results/read-building-jobs-from-jenkins."
    (with-redefs [jenkins-api/get-command (fn [all-jobs-url] building-jobs-jenkins-response)]
        (let [configuration {:jenkins {:currently-building-view "Building"
                                       :jenkins-url "http://10.20.30.40:8080/"}}]
            (is (= (read-currently-building-jobs configuration)
                    read-currently-building-jobs-expected-result)))))

(deftest test-read-queue-info-from-jenkins-negative
    "Check the function emender-jenkins.results/read-queue-info-from-jenkins."
    (testing "the function emender-jenkins.results/read-queue-info-from-jenkins."
    (with-redefs [jenkins-api/get-command (fn [url] nil)]
        (is (= (read-queue-info-from-jenkins "url") nil)))))

(deftest test-create-jobs-in-queue-response
    "Check the function emender-jenkins.results/create-jobs-in-queue-response."
    (testing "the function emender-jenkins.results/create-jobs-in-queue-response."
        (let [items (-> (json/read-str jobs-in-queue-expected-jenkins-response) (get "items"))]
            (is (= (create-jobs-in-queue-response items)
                   [{"queuePos" 2
                     "jobName" "test-Example_Documentation-1.0-Guide-en-US (preview)"}
                    {"queuePos" 1
                     "jobName" "test-Example_Documentation-1.0-Guide-en-US (stage)  "}]))))) 

(deftest test-read-jobs-in-queue-1
    "Check the function emender-jenkins.results/read-jobs-in-queue."
    (testing "the function emender-jenkins.results/read-jobs-in-queue."
    (with-redefs [jenkins-api/get-command (fn [url] nil)]
        (let [configuration {:jenkins {:currently-building-view "Building"
                                       :jenkins-url "http://10.20.30.40:8080/"}}]
            (is (= (read-jobs-in-queue configuration) nil))))))

(deftest test-read-jobs-in-queue-2
    "Check the function emender-jenkins.results/read-jobs-in-queue."
    (testing "the function emender-jenkins.results/read-jobs-in-queue."
    (with-redefs [jenkins-api/get-command (fn [url] jobs-in-queue-expected-jenkins-response)]
        (let [configuration {:jenkins {:currently-building-view "Building"
                                       :jenkins-url "http://10.20.30.40:8080/"}}]
            (is (= (read-jobs-in-queue configuration)
             [{"_class" "hudson.model.Queue$BlockedItem" "task" {"_class" "hudson.model.FreeStyleProject" "name" "test-Example_Documentation-1.0-Guide-en-US (preview)"}}
              {"_class" "hudson.model.Queue$BlockedItem" "task" {"_class" "hudson.model.FreeStyleProject" "name" "test-Example_Documentation-1.0-Guide-en-US (stage)  "}}]
    ))))))

(deftest test-prepare-jobs-in-queue
    "Check the function emender-jenkins.results/prepare-jobs-in-queue."
    (testing "the function emender-jenkins.results/prepare-jobs-in-queue."
        (let [items (-> (json/read-str jobs-in-queue-expected-jenkins-response) (get "items"))]
            (is (= (prepare-jobs-in-queue items)
                   [{"queuePos" 2
                     "jobName" "test-Example_Documentation-1.0-Guide-en-US (preview)"
                     "state"   "QUEUED"}
                    {"queuePos" 1
                     "jobName" "test-Example_Documentation-1.0-Guide-en-US (stage)  "
                     "state"   "QUEUED"}])))))

(deftest test-prepare-building-jobs
    "Check the function emender-jenkins.results/prepare-building-jobs."
    (testing "the function emender-jenkins.results/prepare-building-jobs."
    (with-redefs [jenkins-api/get-command (fn [all-jobs-url] building-jobs-jenkins-response)]
        (let [configuration {:jenkins {:currently-building-view "Building"
                                       :jenkins-url "http://10.20.30.40:8080/"}}]
            (is (= (prepare-building-jobs (read-currently-building-jobs configuration))
               [{"state"   "BUILDING"
                 "jobName" "test-Example_Documentation-1.0-Guide-en-US (preview)"}
                {"state"   "BUILDING"
                 "jobName" "test-Example_Documentation-1.0-Guide-en-US (stage)"}]))))))

(deftest test-create-running-jobs-response
    "Check the function emender-jenkins.results/create-running-jobs-response."
    (testing "the function emender-jenkins.results/create-running-jobs-response."
    (with-redefs [jenkins-api/get-command (fn [all-jobs-url] building-jobs-jenkins-response)]
        (let [configuration    {:jenkins {:currently-building-view "Building"
                                          :jenkins-url "http://10.20.30.40:8080/"}}
              jobs-in-queue (-> (json/read-str jobs-in-queue-expected-jenkins-response) (get "items"))
              building-jobs (read-currently-building-jobs configuration)]
            (is (= (create-running-jobs-response jobs-in-queue building-jobs)
                  [{"queuePos" 2, "jobName" "test-Example_Documentation-1.0-Guide-en-US (preview)", "state" "QUEUED"}
                   {"queuePos" 1, "jobName" "test-Example_Documentation-1.0-Guide-en-US (stage)  ", "state" "QUEUED"}
                   {"state" "BUILDING", "jobName" "test-Example_Documentation-1.0-Guide-en-US (preview)"}
                   {"state" "BUILDING", "jobName" "test-Example_Documentation-1.0-Guide-en-US (stage)"}]))))))

