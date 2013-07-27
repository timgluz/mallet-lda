;; ## Overview
;;
;; This namespace provides functions that thinly wrap the MALLET
;; toolkit's LDA topic modeling. Notably, it does not currently wrap
;; any of the tokenizing or other "pipe" based features of MALLET.
;;
;; Topic models are built on sets of documents, and MALLET requires
;; that documents be given an arbitrary id. A (document, id) pair is
;; called an instance, and the topic modeling algorithms run on
;; instance lists. To create a topic model, you must first make an
;; instance list.

(ns marcliberatore.mallet.lda
  (:require [clojure.java.io :as io])
  (:import [cc.mallet.topics ParallelTopicModel]))

(extend-type cc.mallet.topics.ParallelTopicModel
  MLModelOps
    (add-instances [this instances]
      (when-not (nil? instances)
        (.addInstances this instances)))
    (get-instances [this instances]
      (.getData this))
    (train-run [this]
      (.estimate this)
      this)
    (train [this training-data]
      (do
        (add-instances this training-data)
        (.estimate this)
        this))
    (estimate [this instances settings]
      (let [the-inferencer (.getInferencer this)
            {:keys [num-iters thinning burnin]
             :or {num-iters 10
                  thinning 5
                  burnin 1}} settings]
        (for [the-instance (seq instances)]
          (into []
                (.getSampledDistribution the-inferencer the-instance num-iters thinning burnin)))))
  MLTopicModelOps
    (get-alphabet [this] (.getAlphabet this))
    (get-topic-alphabet [this] (.topicAlphabet this))
    (get-topic-bits [this] (.topicBits this))
    (get-num-types [this] (.numTypes this))
    (get-num-topics [this] (.getNumTopics this))
    (get-topic-doc-counts [this]
      (for [doc-list (.topicDocCounts this)]
        (into [] doc-list)))
    (get-top-words [this max-word-count]
      (vec
        (for [word-list-per-topic (.getTopWords this max-word-count)] 
          (into [] word-list-per-topic))))
    (get-topic-probabilities [this doc-id]
      (try
        (into [] (.getTopicProbabilities this doc-id))
        (catch IndexOutOfBoundsException e 
          (print  (str "Doc id was bigger than index: " (.getMessage e))))))
  MLModelWriter
  (spit-model [this the-target]
    (.write this (io/as-file the-target))
    the-target))

(extend-type java.io.File
  MLModelReader
  (slurp-model [this]
    (try
      (ParallelTopicModel/read this)
      (catch Exception e
        (print "Cant read model: " (.getMessage e))))   
    ))

(defn init-model [num-topics]
  "initializes plain parallel LDA
  Arguments:
    num-topics - (required) number of topics [int]" 
  (ParallelTopicModel. num-topics))

(defn make-model
  "Return a topic model (ParallelTopicModel) on the given
  instance-list, using the optional parameters if specified. The
  default parameters will run fairly quickly, but will not return
  high-quality topics.
  Arguments: 
    :num-topics        - (optional) number of topics
    :num-iter          - (optional) number of iterations
    :optimize-interval - (optional)
    :optimize-burn     - (optional)
    :num-threads       - (optional) number of threads
    :random-seed       - (optional) random seed to use fixed random generator
  Returns:
    the initialized model's object
  "
  ([& {:keys [num-topics num-iter optimize-interval optimize-burn-in 
            num-threads random-seed]
     :or {num-topics 10
          num-iter 10
          optimize-interval 10
          optimize-burn-in 20
          num-threads (.availableProcessors (Runtime/getRuntime))
          random-seed -1 }}]
     (doto (init-model num-topics)    
       (.setNumIterations num-iter)
       (.setOptimizeInterval optimize-interval)
       (.setBurninPeriod optimize-burn-in)
       (.setNumThreads num-threads)
       (.setRandomSeed random-seed)
       )))

