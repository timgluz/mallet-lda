(ns marcliberatore.mallet.lda)

;;TODO: ask more documentation && finish it
;;(defprotocol MLModelEstimator
;;  (estimate [this doc]))

(defprotocol MLModelOps
  "Some generic MLmodel ops (do i need that?)")

(defprotocol MLModelEstimator
  "Protocol for predictors"
  (estimate [this]))

(defprotocol MLTopicModelOps
  "Protocol to read results of topicModel"
  (get-alphabet [this] "returns index of label and objects features")
  (get-topic-alphabet [this] "returns index of top")
  (get-topic-bits [this] "TODO: add it")
  (get-topic-doc-counts [this] "TODO: add doc")
  (get-num-types [this] "TODO: ")
  (get-top-words [this n] "returns top words per topics"))


(defprotocol MLModelReader
  "Protocol for reading existing models from resource")
(defprotocol MLModelWriter
  "Protocol for saving current model to specific resource")

(defprotocol MLModelDataReader
  "Protocol for reading instances from specific resource"
  ())

(defprotocol MLModelDataWriter
  "Protocol for saving models data into specific resource")
