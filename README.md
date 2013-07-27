
**NB!** This version is going to break current interface because i'm refactoring code to
own namespaces.

# mallet-lda

This project is a minimal Clojure wrapper over the LDA topic modeling
implementation from [MALLET], the MAchine Learning for LanguagE
Toolkit.

[MALLET]:http://mallet.cs.umass.edu/

## Installation
[![Dependency Status](http://www.versioneye.com/clojure/marcliberatore.mallet-lda:marcliberatore.mallet-lda/0.1.1/badge.png)](http://www.versioneye.com/clojure/marcliberatore.mallet-lda:marcliberatore.mallet-lda/0.1.1)

The latest stable release is 0.1.0.

Add this `:dependency` to your Leiningen `project.clj`:

```clojure
["marcliberatore.mallet-lda" "0.1.0"]

to use this branch, use lein-git-deps plugin
```

Feedback and pull requests welcome!

## Usage

```clojure
(ns example
  (:require [marcliberatore.mallet.lda :as lda]))

...

(let [data (lda/make-instance-list 
				[[1 ["a" "little" "lamb"]]  
            	 [2 ["row" "your" "boat"]]])
  	  the-model (lda/make-model instance-list)]
  	  
	(lda/get-top-words the-model))
  
...
```

### Demo workflow on REPL

```clojure
  (require '[marcliberatore.mallet.lda :as lda])
  (require '[marcliberatore.mallet.utils :as lda-utils])
  (require '[marcliberatore.mallet.alphabet :as lda-abc])
  (require '[marcliberatore.mallet.instance :as instance :refer [make-instance-list]])

  (def example-data (make-instance-list 
                       [
                          [1 ["ruby" "lda" "test" "rails"]]
                          [2 ["python" "web" "django"]]
                          [3 ["c" "http" "server"]]
                          [4 ["ruby" "lda" "test" "machine" "learning"]]
                        ]))

  (count example-data) ;;instances in example data
  (first example-data) ;; get first instance
  (nth example-data 3) ;; get last example
  
  (def the-splits (instance/split example-data [0.75 0.25]))
  (def training-data (first the-splits))
  (def validate-data (last the-splits))

  (print "size of training-data: " (count training-data)
         "size of validate-data: " (count validate-data))

  (the-i (nth training-data 0))
  (instance/get-name the-i)
  (instance/get-data the-i)

  (def the-model (lda/make-model :num-topics 2 :num-iter 3))
  (lda/train the-model training-data)
  (lda/get-top-words the-model 5)
  (lda/get-topic-probabilities the-model 0) ;;topic probabilities for first training-instance
  
  (def estimator-settings {:num-iters 10 :thinning 5 :burnin 1}) 
  (lda/estimate the-model validate-data estimator-settings)

  ;;serializing models
  (require '[clojure.java.io :as io])
  (def the-filepath "/Users/mallet/model1.mdl")
  (lda/spit-model the-model the-filepath)
  (lda/slurp-model (io/as-file the-filepath)) ;;returns new model objects

```

## TODO

Add protocols and add more implementations for importing data, showing results, serializing models and estimating results

## License

Copyright © 2013 Marc Liberatore

Distributed under the Eclipse Public License, the same as Clojure.
