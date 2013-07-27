(ns marcliberatore.test.examples)

"Simple usage on repl."

(comment    
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
)

