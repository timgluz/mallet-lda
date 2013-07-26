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
  (:import [cc.mallet.topics ParallelTopicModel]))

(extend-type cc.mallet.topics.ParallelTopicModel
  MLModelOps
    (add-instances [this instances]
      (when-not (nil? instances)
        (.addInstances this instances)))
    (get-instances [this instances]
      (.getData this))
    (train-run [this]
      (.estimate this))
    (train [this training-data]
      (do
        (add-instances this training-data)
        (.estimate this)))
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
          (print  (str "Doc id was bigger than index: " (.getMessage e)))))))

(defn init-model [num-topics] 
  (ParallelTopicModel. num-topics))

(comment
    
  (require '[marcliberatore.mallet.lda :as lda])
  (require '[marcliberatore.mallet.utils :as lda-utils])
  (require '[marcliberatore.mallet.alphabet :as lda-abc])
  (require '[marcliberatore.mallet.instance :as instance :refer [make-instance-list]])

  (def the-model (lda/make-model))

  (def training-data (lda/make-instance-list 
                       [
                          [1 ["ruby" "lda" "test" "rails"]]
                          [2 ["python" "web" "django"]]
                          [3 ["c" "http" "server"]]
                        ]))

  (lda/train the-model training-data)
  (lda/get-top-words the-model 5)
  (lda/get-topic-probabilities the-model 0)

  (the-i (nth training-data 0))
  (instance/get-name the-i)
  (instance/get-data the-i)

)

(defn make-model
  "Return a topic model (ParallelTopicModel) on the given
  instance-list, using the optional parameters if specified. The
  default parameters will run fairly quickly, but will not return
  high-quality topics."
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

