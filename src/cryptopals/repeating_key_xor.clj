(ns cryptopals.repeating-key-xor
  (:require
   [clojure.string :as string]
   [cryptopals.fixed-xor :refer :all]
  [cryptopals.bytes :refer :all]))


(defn repeating-key-xor [k plaintext]
  (string/join
   (map int->hex
        (map bit-xor
             (map int plaintext)
             (map int (flatten (repeat (vec k))))))))

(defn keysize-score [n cyphertext]
  (let [blocks (partition n cyphertext)]
    (/
     (+
      (hamming (first blocks) (second blocks))
      (hamming (first blocks) (nth blocks 2))
      (hamming (first blocks) (nth blocks 3))
      (hamming (second blocks) (nth blocks 2))
      (hamming (second blocks) (nth blocks 3))
      (hamming (nth blocks 2) (nth blocks 3))
      )
     n)))


(defn probable-keysizes [cyphertext]
  (let [max-keysize (int (/ (count cyphertext) 4))
        possible-keysizes (range 2 (+ 1 (min 40 max-keysize)) 2)]
    (take 3 (sort-by
             #(keysize-score % cyphertext)
             possible-keysizes))))


(repeating-key-xor "abc" "the secret text")

(let [cyphertext (repeating-key-xor "bac" "the secret text")
      max-keysize (int (/ (count cyphertext) 4))]
  (->>
    (for [n (range 2 (+ max-keysize 1) 2)]
      {:n n :score (float (keysize-score n cyphertext))})
   (sort-by :score)
   ))

  ;(sort-by #(keysize-score % cyphertext)
   ;      (range 2 (min 41 (+ 1 max-keysize)))))
