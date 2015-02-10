(ns cryptopals.fixed-xor
  (:require
   [clojure.string :as string]
   [cryptopals.bytes :refer :all]))


(defn hexor [string1 string2]
  (map bit-xor (map int string1) (map int string2)))

(defn hexor-single-byte [character string]
  (map bit-xor (map int string) (repeat (int character))))
