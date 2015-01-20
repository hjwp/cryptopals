(ns cryptopals.score-test
  (:require
   [expectations :refer [expect]]
   [clojure.string :as string]
   [cryptopals.bytes :refer :all]
   [cryptopals.fixed-xor :refer :all]
   [cryptopals.score :refer :all]))

;; Devise some method for "scoring" a piece of English plaintext.
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
