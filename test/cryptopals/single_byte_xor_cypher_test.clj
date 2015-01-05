(ns cryptopals.single-byte-xor-cypher-test
  (:require [expectations :refer :all]
            [cryptopals.single-byte-xor-cypher :refer :all]))


;; Single-byte XOR cipher

;; The hex encoded string:
;; 1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736
;; ... has been XOR'd against a single character. Find the key, decrypt the message.
;; You can do this by hand. But don't: write code to do it for you.
;; How? Devise some method for "scoring" a piece of English plaintext.
;; Character frequency is a good metric. Evaluate each output and choose the one with the best score.

;; Achievement Unlocked
;; You now have our permission to make "ETAOIN SHRDLU" jokes on Twitter.


;; counting letter frequencies:

(expect
  {\a 0
   \b 0
   \c 0
   \d 1
   \e 1
   \f 0
   \g 0
   \h 1
   \i 0
   \j 0
   \k 0
   \l 3
   \m 0
   \n 0
   \o 2`
   \p 0
   \q 0
   \r 1
   \s 0
   \t 0
   \u 0
   \v 0
   \w 1
   \x 0
   \y 0
   \z 0}
 (count-letters "helloworld"))