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
  (flatten (apply map list matrix)))

(transposed-chars 5 [1 2 3 4 5 6 7 8])
(untranspose [[1 4 7] [2 5 8] [3 6]])

(string->hex "abc")

(hexor "x" "t")
(hexor "y" "h")
(hexor "z" "i")
(hexor "A" "s")
(hexor "x" " ")


(let [cyphertext (repeating-key-xor "xyzA" "this is a new secret phrase that is longer mate")
      keysize 4]
  (->>
   cyphertext
   (transposed-chars keysize)
   (map most-likely-single-byte-xor-decrypt)

;;    (map :char)
   (map :plaintext)
   untranspose
   string/join

   ))
;;
(map char [21 65 21 18 4 4])

(defn- decrypt [keysize cyphertext]
  (->>
     cyphertext
     (transposed-chars keysize)
     (map most-likely-single-byte-xor-decrypt)
     (map :plaintext)
     untranspose
     string/join))

(defn decrypt-repeating-key-xor [cyphertext]
  (:plaintext
   (first
    (sort-by :score
             (for [keysize (probable-keysizes cyphertext)]
               (let [decrypted (decrypt keysize cyphertext)]
                 {:plaintext decrypted
                  :score (score decrypted)}))))))
