(ns marcliberatore.mallet.alphabet
  (:import [cc.mallet.types Alphabet FeatureSequence]))

(extend-type cc.mallet.types.Alphabet
  MLAlphabet
  (get-index [this the-entry]
    (.lookupIndex this the-entry))
  (get-entry [this the-index]
    (.lookupObject this the-index))
  (start-growth [this]
    (.startGrowth this))
  (stop-growth [this]
    (.stopGrowth this))
  clojure.lang.Seqable
  (seq [this] nil)
  ;;TODO: how to make it as clojure seq???
  MLAlphabetCount 
  (count [this]
    (.size this))
  MLAlphabetLookup
  (nth [the-index]
    (.lookupIndex this the-entry))
  )
