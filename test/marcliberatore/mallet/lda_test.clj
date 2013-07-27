(ns marcliberatore.mallet.lda-test
  (:use [clojure.test])
  (:require [marcliberatore.mallet.lda :as lda]
            [marcliberatore.mallet.instance :refer [make-instance-list]]))

(def example-instances (make-instance-list [[1 ["python" "sklearn" "requests" "django"]]
                                            [2 ["ruby" "rails" "grape" "grape-swagger"]]]))

(deftest lda-model-operations
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
