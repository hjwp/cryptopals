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
  (string/join
   (flatten (for [letter letters]
              [(string/join (repeat
                             (int (* 100 (standard-frequencies letter)))
                             letter))]))))

(def almost-worst-string
  (string/join
   (flatten (for [letter letters]
              [(string/join (repeat
                             (int (* 100 (- 13 (standard-frequencies letter))))
                             letter))]))))


;; perfect / worst scores based on letter freqs
(expect 0 (int (score almost-perfect-string)))
(expect 100 (int (score almost-worst-string)))

;; spaces don't count
(expect (score "abc")
        (score "a b c"))
(expect (score "abcdef") (score "a b c def"))

;; junk penalises
(def all-letters-score (score "some letters n stuff"))
(def letters-and-junk-score (score "some letters n junk !@#$ 1@#$% &*%# ~!#$%"))
(expect (> letters-and-junk-score all-letters-score))


;; sanity-check real words are better than random letters
(expect (>
         (score "abcdef")
         (score "hello there")))
(expect (>
         (score "abcdef")
         (score "a reasonably normal english sentence")))

;; uppercase letters should penalise
(expect (<
         (score "this sentence")
         (score "ThiS sEntenCE")))
(expect (<
         (score "abc")
         (score "ABC")))
(expect (+ 1 (score "abc") (score "Abc")))


(expect (<
         (score "this is the secret text")
         (score "HionNO!ruEOdeoES``1uceT")))

