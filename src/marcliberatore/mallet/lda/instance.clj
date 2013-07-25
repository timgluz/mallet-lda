(ns marcliberatore.mallet.lda
  (:import [cc.mallet.types Alphabet FeatureSequence Instance InstanceList]))

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

(defn- make-instance
  "Instantiate an Instance with an id and token collection,
  using the provided Alphabet to handle symbols."
  ([id tokens alphabet]
     (let [feature-sequence (make-feature-sequence tokens alphabet)]
       (Instance. feature-sequence nil id nil))))

(defn make-instance-list
  "Make an InstanceList using a collection of documents. A document is
  a pairing of a document id and the collection of tokens for that
  document. Document ids must be unique, and tokens must be strings."
  ([documents]
     (let [alphabet (Alphabet.)
           instance-list (InstanceList. alphabet nil)]
       (doseq [[id tokens] documents]
         (.add instance-list (make-instance id tokens alphabet)))
       instance-list)))


