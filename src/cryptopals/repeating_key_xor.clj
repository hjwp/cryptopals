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
  (let [blocks (partition (* 2 n) (hex->bin cyphertext))]
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
        possible-keysizes (range 2 (+ 1 (min 40 max-keysize)))]
    (take 3 (reverse (sort-by
                      #(keysize-score % cyphertext)
                      possible-keysizes)))))
