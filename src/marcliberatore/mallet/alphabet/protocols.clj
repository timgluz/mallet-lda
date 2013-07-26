(ns marcliberatore.mallet.alphabet)

(defprotocol MLAlphabet
  "Interface for topic models alphabets"
  (start-growth [this] "opens alphabet")
  (stop-growth [this] "closes alphabet")
  (growth-stopped? [this] "checks does alphabet allow adding new entries ")
  ;;TODO: smells like persistentArray - is possible interface it?  
  (get-index [this the-entry] "Returns id for entry if it exists") ;;~> (nth this)
  (get-entry [this the-index] "Returns entry by index")) ;;~> (get this key)
  (get-size [this] "Size of alphabet") ;; ~> (count this)


