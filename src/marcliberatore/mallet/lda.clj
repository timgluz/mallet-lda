;; ## Overview
;;
;; This namespace provides functions that thinly wrap the MALLET
;; toolkit's LDA topic modeling. Notably, it does not currently wrap
;; any of the tokenizing or other "pipe" based features of MALLET.
;;
;; Topic models are built on sets of documents, and MALLET requires
;; that documents be given an arbitrary id. A (document, id) pair is
;; called an instance, and the topic modeling algorithms run on
;; instance lists. To create a topic model, you must first make an
;; instance list.

(ns marcliberatore.mallet.lda)

(load "lda/protocols")
(load "lda/core")
