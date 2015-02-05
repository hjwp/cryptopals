(ns cryptopals.fixed-xor
  (:require
   [clojure.string :as string]
   [cryptopals.bytes :refer :all]))


(defn hexor [string1 string2]
  (string/join
   (map int->hexbyte
        (map bit-xor
             (tobytes string1)
             (tobytes string2)))))


(defn hexor-single-byte [character hexstring]
  (string/join
   (map int->hexbyte
    (map bit-xor
      (tobytes hexstring)
      (repeat (int character))))))
