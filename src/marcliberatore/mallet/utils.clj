(ns marcliberatore.mallet.utils
  (:require [clojure.string :as string]))

(defn tokenize [item]
  "tokenizes one string with simple white-space and comma"
  (-> item str string/lower-case (string/split #"[\s|\,]+")))

(defn tokenize-map [the-doc]
  "tokenizes clojure map and concates results into one lazy-vec"
  (mapcat (fn[[k v]] (tokenize v))
          the-doc))

