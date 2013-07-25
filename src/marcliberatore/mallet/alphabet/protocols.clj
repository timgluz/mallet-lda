(ns marcliberatore.mallet.alphabet)

(defprotocol MLAlphabet
  "Interface for topic models alphabets"
  (get-index [this the-entry] "Returns id for entry if it exists")
  (get-entry [this the-index] "Returns entry by index")
  (start-growth [this] "opens alphabet")
  (stop-growth [this] "closes alphabet")
  (growth-stopped? [this] "checks does alphabet allow adding new entries "))


(defprotocol MLAlphabetCount
  (count [this]))

(defprotocol MLAlphabetLookup
  (nth [this]))
