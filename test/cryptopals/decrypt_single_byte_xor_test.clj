(ns cryptopals.decrypt-single-byte-xor-test
  (:require
   [expectations :refer [expect]]
   [clojure.string :as string]
   [cryptopals.bytes :refer :all]
   [cryptopals.fixed-xor :refer :all]
   [cryptopals.decrypt-single-byte-xor :refer :all]))


;; Single-byte XOR cipher

;; The hex encoded string:
;; 1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736
;; ... has been XOR'd against a single character. Find the key, decrypt the message.
;; You can do this by hand. But don't: write code to do it for you.

(def secret "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736")
(def answer (most-likely-single-byte-xor-decrypt secret))

(expect #(< % 100) (answer :score))
answer

(expect
 "bacon"
 (re-find  #"bacon" (answer :plaintext)))


(expect "secret message"
        (:plaintext (most-likely-single-byte-xor-decrypt
                     (hexor-single-byte \x (string->hex "secret message")))))

(expect \y
        (:byte (most-likely-single-byte-xor-decrypt
                     (hexor-single-byte \y (string->hex "secret message")))))
