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

(def secret (hex->bytes "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"))
(def decrypted (most-likely-single-byte-xor-decrypt secret))

(expect #(< % 100) (decrypted :score))
decrypted

(expect
 "Cooking MC's like a pound of bacon"
  (decrypted :plaintext))


(expect "secret message"
        (:plaintext (most-likely-single-byte-xor-decrypt
                     (hexor-single-byte \x (hex->bytes (string->hex "secret message"))))))

(expect \Y
        (:char (most-likely-single-byte-xor-decrypt
                     (hexor-single-byte \Y (hex->bytes (string->hex "secret message"))))))

; Detect single-character XOR
; One of the 60-character strings in detect-single-byte-xor.txt has been encrypted by single-character XOR.
; Find it.

(def encrypted-strings-file (slurp "resources/detect-single-byte-xor.txt"))
(def encrypted-strings (map hex->string (string/split encrypted-strings-file #"\n")))

(def decrypts (pmap most-likely-single-byte-xor-decrypt encrypted-strings))
(def answer (time (first (sort-by :score decrypts))))

answer

(expect
 "Now that the party is jumping\n"
 (:plaintext answer))
