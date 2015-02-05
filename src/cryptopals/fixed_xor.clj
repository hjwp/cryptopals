(ns cryptopals.fixed-xor
  (:require
   [clojure.string :as string]
   [cryptopals.bytes :refer :all]))


(defn hexor [string1 string2]
  (string/join
   (map int->hexbyte
        (map bit-xor
             (hexstring->byteseq string1)
             (hexstring->byteseq string2)))))


(defn hexor-single-byte [character hexstring]
  (string/join
   (map int->hexbyte
    (map bit-xor
      (hexstring->byteseq hexstring)
      (repeat (int character))))))
