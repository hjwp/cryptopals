(ns cryptopals.fixed-xor
  (:require
   [clojure.string :as string]
   [cryptopals.numbers :refer :all]))

(defn ziplists [list1 list2]
  (map vector list1 list2))


(defn hexor [string1 string2]
  (bin->hex (map #(apply bit-xor %) (ziplists (hex->bin string1) (hex->bin string2)))))

(defn hexor-single-byte [string1 char]
  (hexor string1 (string/join (repeat (count string1) char))))
