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

(ns emender-jenkins.metadata-reader)

(require '[clojure.tools.logging   :as log])

(require '[emender-jenkins.results     :as results])
(require '[clj-jenkins-api.jenkins-api :as jenkins-api])

(def GuideStatisticResultNames {
    :commiters-list                   "results.GuideStatistic.Commiters.freq"
    :docbook-versions                 "results.GuideStatistic.DocBookVersions.txt"
    :entities-count                   "results.GuideStatistic.Entities.cnt"
    :entities-uniq                    "results.GuideStatistic.Entities.uniq"
    :other-parts-count                "results.GuideStatistic.OtherPartsCount.cnt"
    :program-listing-tag-frequencies  "results.GuideStatistic.ProgramListingTag.freq"
    :tag-frequencies                  "results.GuideStatistic.TagFrequencies.freq"
    :tags-with-condition-count        "results.GuideStatistic.TagsWithCondition.cnt"
    :tags-with-condition-list         "results.GuideStatistic.TagsWithCondition.list"
    :used-graphics-count              "results.GuideStatistic.UsedGraphicsCount.cnt"
    :word-count                       "results.GuideStatistic.WordCount.cnt"
    :xincludes-count                  "results.GuideStatistic.XiIncludes.cnt"
    :zpage-count                      "results.GuideStatistic.ZPageCount.cnt"})

(def CustomerPortalRequirements {
    :chunkable-tags-ids               "results.CustomerPortalRequirements.testChunkableTagsIDsTag.cnt"})

(def metadata (atom nil))

(defn parse-int
    ([^String string]
     (parse-int string nil)) ; nil instead
    ([^String string default-value]
     (if (and string (re-matches #"[0-9]+" string))
         (java.lang.Integer/parseInt string)
         default-value)))

(defn parse-commiter
    [line]
    (let [parts (re-matches #"\s+(\d+)(.*)" line)]
        (if parts
            {:name    (-> (nth parts 2) clojure.string/trim)
             :commits (-> (second parts) (parse-int 0))})))

(defn parse-commiters
    [lines]
    (map #(parse-commiter %) lines))

(defn count-total-commits
    [commiters]
    (reduce + (map #(:commits %) commiters)))

(defn read-and-parse-list-of-commiters
    [jenkins-url job-name]
    (let [raw-data (jenkins-api/read-file-from-artifact jenkins-url job-name (:commiters-list GuideStatisticResultNames) nil)
          lines    (if raw-data (clojure.string/split-lines raw-data))
          commiters (parse-commiters lines)]
          {:commiters-count (count lines)
           :commiters       commiters
           :total-commits   (count-total-commits commiters)}))

(defn read-and-parse-chunkable-tags-ids
    [jenkins-url job-name]
    (let [raw-data  (jenkins-api/read-file-from-artifact jenkins-url job-name (:chunkable-tags-ids CustomerPortalRequirements) nil)
          lines     (if raw-data (clojure.string/split-lines raw-data))]
          {:total   (-> (first lines) parse-int)
           :missing (-> (second lines) parse-int)}))

(defn read-and-parse-docbook-versions
    [jenkins-url job-name]
)

(defn find-value-on-line
    [lines prefix]
    (let [line (first (filter #(.startsWith % prefix) lines))]
        (if line
            (-> (subs line (count prefix))
                clojure.string/trim))))

(defn read-and-parse-other-parts-count
    [jenkins-url job-name]
    (let [raw-data  (jenkins-api/read-file-from-artifact jenkins-url job-name (:other-parts-count GuideStatisticResultNames) nil)
          lines     (if raw-data (clojure.string/split-lines raw-data))]
          {:tables           (-> (find-value-on-line lines "tables")           str)
           :procedures       (-> (find-value-on-line lines "procedures")       str)
           :command-examples (-> (find-value-on-line lines "command examples") str)
           :code-samples     (-> (find-value-on-line lines "code samples")     str)}))

(defn read-and-parse-program-listing
    [jenkins-url job-name]
)

(defn read-and-parse-tag-frequencies
    [jenkins-url job-name]
)

(defn read-and-parse-tags-with-conditions-count
    [jenkins-url job-name]
)

(defn read-and-parse-tags-with-conditions-list
    [jenkins-url job-name]
)

(defn parse-first-number-from-stream
    [input-stream default-value]
    (let [lines (if input-stream (clojure.string/split-lines input-stream))]
          (-> (first lines)
              (parse-int default-value))))

(defn read-file-from-artifact
    [jenkins-url job-name filename]
    (jenkins-api/read-file-from-artifact jenkins-url job-name filename nil))

(defn read-and-parse-first-number-from-jenkins
    [jenkins-url job-name filename default-value]
    (-> (read-file-from-artifact jenkins-url job-name filename)
        (parse-first-number-from-stream default-value)))

(defn read-and-parse-entities-count
    [jenkins-url job-name]
    (read-and-parse-first-number-from-jenkins jenkins-url job-name (:entities-count GuideStatisticResultNames) -1))

(defn read-and-parse-entities-uniq
    [jenkins-url job-name]
    (read-and-parse-first-number-from-jenkins jenkins-url job-name (:entities-uniq GuideStatisticResultNames) -1))

(defn read-and-parse-used-graphics-count
    [jenkins-url job-name]
    (read-and-parse-first-number-from-jenkins jenkins-url job-name (:used-graphics-count GuideStatisticResultNames) -1))

(defn read-and-parse-word-count
    [jenkins-url job-name]
    (read-and-parse-first-number-from-jenkins jenkins-url job-name (:word-count GuideStatisticResultNames) -1))

(defn read-and-parse-xincludes-count
    [jenkins-url job-name]
    (read-and-parse-first-number-from-jenkins jenkins-url job-name (:xincludes-count GuideStatisticResultNames) -1))

(defn read-and-parse-zpage-count
    [jenkins-url job-name]
    (read-and-parse-first-number-from-jenkins jenkins-url job-name (:zpage-count GuideStatisticResultNames) -1))

(defn job-results->job-names
    [job-results]
    (for [job-result job-results]
        (:jobName job-result)))

(defn load-and-parse-metadata
    [jenkins-url job-names]
    (for [job-name job-names]
        {:job-name                  job-name
         :product                   (results/job-name->product-name job-name)
         :version                   (results/job-name->version job-name)
         :book                      (results/job-name->book-name job-name)
         :commits                   (read-and-parse-list-of-commiters          jenkins-url job-name)
         :chunkable-tags-ids        (read-and-parse-chunkable-tags-ids         jenkins-url job-name)
         :docbook-versions          (read-and-parse-docbook-versions           jenkins-url job-name)
         :entities-count            (read-and-parse-entities-count             jenkins-url job-name)
         :entities-uniq             (read-and-parse-entities-uniq              jenkins-url job-name)
         :other-parts-count         (read-and-parse-other-parts-count          jenkins-url job-name)
         :program-listing           (read-and-parse-program-listing            jenkins-url job-name)
         :tag-frequencies           (read-and-parse-tag-frequencies            jenkins-url job-name)
         :tags-with-condition-count (read-and-parse-tags-with-conditions-count jenkins-url job-name)
         :tags-with-condition-list  (read-and-parse-tags-with-conditions-list  jenkins-url job-name)
         :used-graphics-count       (read-and-parse-used-graphics-count        jenkins-url job-name)
         :word-count                (read-and-parse-word-count                 jenkins-url job-name)
         :xincludes-count           (read-and-parse-xincludes-count            jenkins-url job-name)
         :zpage-count               (read-and-parse-zpage-count                jenkins-url job-name)}))

(defn reload-tests-metadata
    [configuration job-results]
    (let [jenkins-url     (-> configuration :jenkins :jenkins-url)
          parsed-metadata (->> (job-results->job-names job-results)
                               (load-and-parse-metadata jenkins-url))]
    (reset! metadata parsed-metadata)
    parsed-metadata))

(defn metadata-count
    []
    (count @metadata))

(defn metadata-filter
    [product version book-regexp item]
    (and (or (nil? product) (= product (:product item)))
         (or (nil? version) (= version (:version item)))
         (or (nil? book-regexp) (re-matches (re-pattern book-regexp) (:book item)))))

(defn get-metadata
    ([]
     @metadata)
    ([product version book-regexp]
     (filter #(metadata-filter product version book-regexp %) @metadata)))

