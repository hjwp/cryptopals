(ns cryptopals.detect-single-byte-xor-test
  (:require [expectations :refer [expect]]
            [cryptopals.detect-single-byte-xor :refer :all]))

; Detect single-character XOR
; One of the 60-character strings in detect-single-byte-xor.txt has been encrypted by single-character XOR.
; Find it.
