(ns cryptopals.fixed-xor
  (:require
   [clojure.string :as string]
   [cryptopals.bytes :refer :all]))

(defn hexor [string1 string2]
  (bin->hex (map bit-xor (hex->bin string1) (hex->bin string2))))

(defn hexor-single-byte [string1 character]
  (let [mask (string/join (repeat (/ (count string1) 2) (char->hex character)))]
    (hexor string1 mask)))
