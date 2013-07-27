(ns marcliberatore.mallet.lda)

;;TODO: ask more documentation && finish it

(defprotocol MLModelOps
  "Some generic MLmodel ops (do i need that?)"
  (add-instances [this instances])
  (get-instances [this])
  (train-run [this] "trains model, which data is already initialized")
  (train [this training-instances] "sets training data before it runs training iterations")
  (estimate [this instances settings] "estimates topic probabilities for given instances; returns lazy-seq")
  (validate [this test-instances] "validates performance of model"))

(defprotocol MLTopicModelOps
  "Protocol to read results of topicModel"
  (get-alphabet [this] "returns index of label and objects features")
  (get-topic-alphabet [this] "returns index of top")
  (get-topic-bits [this] "TODO: add it")
  (get-topic-doc-counts [this] "TODO: add doc")
  (get-num-types [this] "TODO: ")
  (get-num-topics [this] "Returns number of topics")
  (get-top-words [this n] "returns top words per topics")
  (get-topic-probabilities [this doc-id] "return list of probabilities over topics"))
