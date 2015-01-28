(ns cryptopals.repeating-key-xor
  (:require
   [clojure.string :as string]
   [cryptopals.fixed-xor :refer :all]
  [cryptopals.bytes :refer :all]))

(string/join (repeat 2 "abc"))
(string/join (repeat 7 "abc"))
(hexor "abc" "bcda")
(map int "abc")
(map bit-xor [1 2 3 4] [4 4 4 4 4 4 4 4 4])
(flatten [[1 2 3] [4 5 6]])
(take 5 (map int (flatten (repeat (vec "abc")))))

(defn repeating-key-xor [k plaintext]
  (string/join
   (map int->hex
         (map bit-xor
              (map int plaintext)
              (map int (flatten (repeat (vec k))))))))

(repeating-key-xor "abc" "hello giles")
