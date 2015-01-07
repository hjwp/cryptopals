(ns cryptopals.single-byte-xor-cypher-test
  (:require
   [expectations :refer [expect]]
   [clojure.string :as string]
   [cryptopals.bytes :refer :all]
   [cryptopals.single-byte-xor-cypher :refer :all]
   [cryptopals.fixed-xor :refer :all]))


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


(def helloworldfreqs
  {\a 0   \b 0   \c 0   \d 1   \e 1   \f 0   \g 0   \h 1   \i 0   \j 0   \k 0
   \l 3   \m 0   \n 0   \o 2   \p 0   \q 0   \r 1   \s 0   \t 0   \u 0   \v 0
   \w 1   \x 0   \y 0   \z 0})

(expect helloworldfreqs
 (count-letters "helloworld"))

; count should ignore casing
(expect helloworldfreqs
 (count-letters "HelloWorld"))

(expect 100 ((letter-frequencies "a") \a))
(expect 0   ((letter-frequencies "a") \b))
(expect 50  ((letter-frequencies "ab") \a))
(expect 50  ((letter-frequencies "ab") \b))


(def almost-perfect-string
  (string/join (for [letter letters]
                 [(string/join (repeat
                                (int (* 1000 (standard-frequencies letter)))
                                letter))])))

(expect (* 10 (standard-frequencies \a))
        ((expected-frequencies 10) \a))

(expect (* 12 (standard-frequencies \b))
        ((expected-frequencies 12) \b))


(expect 99 (int (score almost-perfect-string)))

(def secret "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736")

(def decrypts (map hex->string (map #(hexor-single-byte secret %) "ABCDEFGHIJKLMNOPQRSTUVWXYZ")))
decrypts

(last (sort (zipmap (map score decrypts) decrypts)))

