(ns marcliberatore.core
  (:import [cc.mallet.topics ParallelTopicModel]))

;;TODO: ask more documentation && finish it
;;(defprotocol MLModelEstimator
;;  (estimate [this doc]))

(defprotocol MLTopicModelOps
  "Protocol to read results of topicModel"
  (get-alphabet [this] "returns index of label and objects features")
  (get-topic-alphabet [this] "returns index of top")
  (get-topic-bits [this] "TODO: add it")
  (get-num-types [this] "TODO: ")
  (get-top-words [this n] "returns top words per topics"))

