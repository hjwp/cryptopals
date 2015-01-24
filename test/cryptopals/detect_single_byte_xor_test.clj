(ns cryptopals.detect-single-byte-xor-test
  (:require [expectations :refer [expect]]
            [clojure.string :as string]
            [cryptopals.decrypt-single-byte-xor :refer :all]))

; Detect single-character XOR
; One of the 60-character strings in detect-single-byte-xor.txt has been encrypted by single-character XOR.
; Find it.

(def encrypted-strings-file (slurp "resources/detect-single-byte-xor.txt"))
(def encrypted-strings (string/split encrypted-strings-file #"\n"))

(def decrypts (map most-likely-single-byte-xor-decrypt encrypted-strings))

(def answer (time (first (sort-by :score decrypts))))

answer

(expect
 "jumping"
 (re-find  #"jumping" (answer :plaintext)))
