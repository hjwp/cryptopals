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

