(ns cryptopals.detect-single-byte-xor-test
  (:require [expectations :refer [expect]]
            [cryptopals.single-byte-xor-cypher :refer :all]
            [clojure.string :as string]
            [cryptopals.detect-single-byte-xor :refer :all]))

; Detect single-character XOR
; One of the 60-character strings in detect-single-byte-xor.txt has been encrypted by single-character XOR.
; Find it.

(def encrypted-strings-file (slurp "resources/detect-single-byte-xor.txt"))
(def encrypted-strings (string/split encrypted-strings-file #"\n"))
encrypted-strings


;; (def decrypts (map most-likely-single-byte-xor-decrypt encrypted-strings))
;; decrypts
;; (last (sort-by :score decrypts))
