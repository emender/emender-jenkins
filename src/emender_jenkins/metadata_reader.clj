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

(require '[emender-jenkins.jenkins-api :as jenkins-api])

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
    :zpage-count                      "results.GuideStatistic.ZPageCount.cn"})

(defn read-and-parse-list-of-commiters
    [jenkins-url job-name]
    (let [raw-data (jenkins-api/read-file-from-artifact jenkins-url job-name (:commiters-list GuideStatisticResultNames))]
        (count raw-data)))

(defn job-results->job-names
    [job-results]
    (for [job-result job-results]
        (:job-name job-result)))

(defn load-and-parse-metadata
    [jenkins-url job-names]
    (for [job-name job-names]
        {:job-name job-name
         :commiters-list (read-and-parse-list-of-commiters jenkins-url job-name)}))

(defn reload-tests-metadata
    [configuration job-results]
    (let [jenkins-url   (-> configuration :jenkins :jenkins-url)
          metadata      (->> (job-results->job-names job-results)
                             (load-and-parse-metadata jenkins-url))]
    (println metadata)
    nil))

(defn metadata-count
    []
    42)


