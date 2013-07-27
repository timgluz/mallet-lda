(ns marcliberatore.mallet.instance)

(defprotocol MLLocked
  (locked? [this])
  (lock [this])
  (unlock [this]))

(defprotocol MLAlphabetAccessor
  (get-alphabet [this])
  (get-data-alphabet [this] "returns Alphabet that maps features of the data to integers")
  (get-target-alphabet [this] "returns the alphabet that maps labels of target output to integers"))

(defprotocol MLInstanceList
  "Protocol for mallets instancelist"
  (add [this the-instance])
  (add-weighted [this the-instance the-weight])
  (split [this proportions] "divides instances to sublist propotionally, acceptable format: [4 2.0] ")
  (split-by-counts [this counts] "divides instance list to sub-lists each has nth counts elements in it")
  (sub-list [this start end] "returns instance list which includes elements from start to end prosition.")
  (remove-instance [this the-instance])
  (remove-index [this the-index])
  (clear [this]))

(defprotocol MLInstance
  "Protocol for mallets instance"
  (get-data [this])
  (set-data [this the-val])
  (get-name [this])
  (set-name [this the-val])
  (get-target [this])
  (set-target [this the-val]))
