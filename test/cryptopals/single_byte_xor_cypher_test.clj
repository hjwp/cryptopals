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
  {\d 1, \e 1, \h 1, \l 3, \o 2, \r 1, \w 1})

(expect helloworldfreqs
 (count-letters "helloworld"))

(expect 100 ((letter-frequencies "a") \a))
(expect 50  ((letter-frequencies "ab") \a))
(expect 50  ((letter-frequencies "ab") \b))
(expect 10  ((letter-frequencies "abcdefghik") \b))


(def almost-perfect-string
  (string/join (for [letter letters]
                 [(string/join (repeat
                                (int (* 1000 (standard-frequencies letter)))
                                letter))])))

(def almost-worst-string
  (string/join (for [letter letters]
                 [(string/join (repeat
                                (int (* 1000 (- 13 (standard-frequencies letter))))
                                letter))])))


(expect 0 (int (score almost-perfect-string)))
(expect 100 (int (score almost-worst-string)))

(expect (score "abc")
        (score "a b c"))

(def all-letters-score (score "some letters n stuff"))
(def letters-and-junk-score (score "some letters n junk !@#$ 1@#$% &*%# ~!#$%"))

(expect #(> letters-and-junk-score %) all-letters-score)

(expect (>
        (score "abcdef")
        (score "hello there")))
(expect (>
        (score "abcdef")
        (score "a reasonably normal english sentence")))


(expect (score "abcdef") (score "a b c def"))

(def secret "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736")
(get-all-decrypts secret)
(def answer (most-likely-single-byte-xor-decrypt secret))
answer

(expect #(< % 100) (answer :score))

(expect
 "bacon"
 (re-find  #"bacon" (answer :plaintext)))
