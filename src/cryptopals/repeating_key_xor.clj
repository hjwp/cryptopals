(ns cryptopals.repeating-key-xor
  (:require
   [clojure.string :as string]
   [cryptopals.fixed-xor :refer :all]
   [cryptopals.decrypt-single-byte-xor :refer [most-likely-single-byte-xor-decrypt]]
   [cryptopals.score :refer [score]]
   [cryptopals.bytes :refer :all]))


(defn repeating-key-xor [k string]
  (map bit-xor
       (map int string)
       (flatten (repeat (map int k)))))


(defn keysize-score [n cyphertext]
  (let [blocks (partition n (hex->bytes (string->hex cyphertext)))]
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

(defn transposed-chars [keysize cyphertext]
  (for [pos (range keysize)]
    (take-nth keysize (nthrest cyphertext pos))))

(defn untranspose [matrix]
  (let [length (count (first matrix))]
    (flatten (for [n (range length)]
               (map #(get % n) matrix)))))

(defn- decrypt [keysize cyphertext]
  (->>
     cyphertext
     (transposed-chars keysize)
     (map most-likely-single-byte-xor-decrypt)
     (map :plaintext)
     untranspose
     string/join))


(defn decrypt-repeating-key-xor [cyphertext]
  (first
   (sort-by :score
            (for [keysize (probable-keysizes cyphertext)]
              (let [decrypted (decrypt keysize cyphertext)]
                {:keysize keysize
                 :plaintext decrypted
                 :score (score decrypted)})))))
