
**NB!** This version is going to break current interface because i'm refactoring code to
own namespaces.

# mallet-lda

This project is a minimal Clojure wrapper over the LDA topic modeling
implementation from [MALLET], the MAchine Learning for LanguagE
Toolkit.

[MALLET]:http://mallet.cs.umass.edu/

## Installation

The latest stable release is 0.1.0.

Add this `:dependency` to your Leiningen `project.clj`:

```clojure
["marcliberatore.mallet-lda" "0.1.0"]
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

  ;;TODO training, estimating and validation
```

## TODO

Add protocols and add more implementations for importing data, showing results, serializing models and estimating results

## License

Copyright © 2013 Marc Liberatore

Distributed under the Eclipse Public License, the same as Clojure.
