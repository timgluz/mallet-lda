(ns marcliberatore.mallet.instance
  (:import [cc.mallet.types Alphabet FeatureSequence Instance InstanceList]))

(extend-type Instance
  MLInstance
  (get-data [this] (.getData this))
  (set-data [this the-val] (.setData this the-val) the-val)
  (get-name [this] (.getName this))
  (set-name [this the-val] (.setName this the-val) the-val)
  (get-target [this] (.getTarget this))
  (set-target [this the-val] (.setTarget the-val) the-val)
  MLLocked
  (locked? [this] (.isLocked this))
  (lock [this]
    (do
      (.lock this)
      (locked? this)))
  (unlock [this]
    (.unLock this)
    (locked? this))
  MLAlphabetAccessor
  (get-alphabet [this]
    (.getAlphabet this))
  (get-data-alphabet [this]
    (.getDataAlphabet this))
  (get-target-alphabet [this]
    (.getTargetAlphabet this))) 

(extend-type InstanceList
  MLInstanceList
  (add [this the-instance]
    (.add this the-instance))
  (add-weighted [this the-instance the-weight]
    (.add this the-instance the-weight))
  (split [this proportions]
    (.split this (double-array proportions)))
  (split-by-counts [this counts]
    (.splitInOrder this (int-array count)))
  (sub-list [this start end]
    (.subList this start end))
  (size [this]
    (.size this))
  (remove-instance [this the-instance]
    (.remove this the-instance))
  (remove-index [this the-index]
    (.remove this the-index))
  (clear [this]
    (.clear this))
  MLAlphabetAccessor
  (get-alphabet [this]
    (.getAlphabet this))
  (get-data-alphabet [this]
    (.getDataAlphabet this))
  (get-target-alphabet [this]
    (.getTargetAlphabet this)))

(defn init-instance 
  ([feature-seq the-target the-name] 
    (init-instance feature-seq the-target the-name nil))
  ([feature-seq the-target the-name the-source]
    (Instance. feature-seq the-target the-name the-source)))

(defn init-instance-list 
  ([the-alphabet] (init-instance-list the-alphabet nil))
  ([the-alphabet the-capacity] 
    (InstanceList. the-alphabet the-capacity)))

;; ## Instance and InstanceList Wrappers 
;;
;; All instances in an InstanceList must share a common alphabet,
;; hence the passing around of the Alphabet object in the functions
;; below.
(defn- make-feature-sequence
  "Instantiate a FeatureSequence with a collection of tokens,
  using the provided Alphabet to handle symbols."
  ([tokens alphabet]
     (let [feature-sequence (FeatureSequence. alphabet)]
       (doseq [token tokens]
         (.add feature-sequence token))
       feature-sequence)))

(defn make-instance
  "Instantiate an Instance with an id and token collection,
  using the provided Alphabet to handle symbols."
  ([id tokens alphabet]
     (let [feature-sequence (make-feature-sequence tokens alphabet)]
       (init-instance feature-sequence nil id))))

(defn make-instance-list
  "Make an InstanceList using a collection of documents. A document is
  a pairing of a document id and the collection of tokens for that
  document. Document ids must be unique, and tokens must be strings."
  ([documents]
     (let [alphabet (Alphabet.)
           instance-list (init-instance-list alphabet)]
       (doseq [[id tokens] documents]
         (add instance-list (make-instance id tokens alphabet)))
       instance-list)))


