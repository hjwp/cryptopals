(ns cryptopals.fixed-xor
  (:require
   [cryptopals.numbers :refer :all]))

(defn ziplists [list1 list2]
  (map vector list1 list2))


(defn hexor [string1 string2]
  (bin->hex (map #(apply bit-xor %) (ziplists (hex->bin string1) (hex->bin string2)))))
