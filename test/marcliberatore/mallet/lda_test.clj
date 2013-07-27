(ns marcliberatore.mallet.lda-test
  (:use [clojure.test])
  (:require [marcliberatore.mallet.lda :as lda]
            [marcliberatore.mallet.instance :refer [make-instance-list]]))

(def example-instances (make-instance-list [[1 ["python" "sklearn" "requests" "django"]]
                                            [2 ["ruby" "rails" "grape" "grape-swagger"]]]))

(deftest lda-model-operations
  (print "#-- Going to test operations of MLModel.")
  (testing "adding new instances should return correct number of instances"
    (let [the-model (lda/init-model 5)]
      (lda/add-instances the-model example-instances)
      (is (= 2
            (-> the-model lda/get-instances count)))))
  
  (testing "train-run on empty model should return model aka skips null-exception"
    (let [the-model (lda/make-model :num-topics 2)]
      (is (= (class the-model)
             (class (lda/train-run the-model))))))

  (testing "train-run on initialized model should return model"
    (let [the-model (lda/make-model :num-topics 2)]
      (lda/add-instances the-model example-instances)
      (is (= (class the-model)
             (class (lda/train-run the-model))))))

  (testing "train with empty instance-list should be successful"
    (let [the-model (lda/make-model :num-topics 2)
          empty-instance-list (make-instance-list [])]
      (is (= (class the-model)
             (class (lda/train the-model empty-instance-list))))))
  (testing "train with example data should be successful"
    (let [the-model (lda/make-model :num-topics 2)]
      (is (= (class the-model)
             (class (lda/train the-model example-instances))))))
)

(deftest lda-topic-model-operations
  (print "#-- Going to test operations of MLTopicModelOps.")
  (let [empty-model (lda/make-model :num-topics 3)
        filled-model (lda/make-model :num-topics 3)]
    (lda/add-instances filled-model example-instances)
    (testing "get-alphabet on empty model should return nil"
      (is (= nil (lda/get-alphabet empty-model))))
    (testing "get-alphabet on filled model should return Alphabet object with 8 words"
      (is (= 8 (.size (lda/get-alphabet filled-model)))))
    (testing "get-topic-alphabet on empty model should return alphabet with 3 topics"
      (is (= 3 (.size (lda/get-topic-alphabet empty-model)))))
    (testing "get-topic-alphabet on filled model should return alphabet with 3 topics"
      (is (= 3 (.size (lda/get-topic-alphabet filled-model)))))
    (testing "get-topic-bits on empty model should return 2"
      (is (= 2 (lda/get-topic-bits empty-model))))
    (testing "get-topic-bits on empty model should return 2"
      (is (= 2 (lda/get-topic-bits filled-model))))
    (testing "get-num-types on empty model should return 0"
      (is (= 0 (lda/get-num-types empty-model))))
    (testing "get-num-types on empty model should return 8"
      (is (= 8 (lda/get-num-types filled-model))))
    (testing "get-topic-doc-counts on empty model should return empty list "
      (is (= true (empty? (lda/get-topic-doc-counts empty-model)))))
    (testing "get-topic-doc-counts on filled model should not return empty list"
      (is (= false (empty? (lda/get-topic-doc-counts filled-model)))))
    (testing "get-top-words on empty model should return empty list"
      (is (= true (empty? (flatten (lda/get-top-words empty-model 3))))))
    (testing "get-top-words on filled model should return empty list"
      (is (= false (empty? (flatten (lda/get-top-words filled-model 3)))))
      (is (= 3 (count (lda/get-top-words filled-model 3)))))
    (testing "get-topic-probabilities on empty model should return empty list"
      (is (= nil (seq (flatten (lda/get-topic-probabilities empty-model 0)))))) 
    (testing "get-topic-probabilities on filled model should return correct values"
      (is (= false (empty? (flatten (lda/get-topic-probabilities filled-model 0)))))) 
    ))
