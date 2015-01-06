(ns cryptopals.fixed-xor
  (:require
   [clojure.string :as string]
   [cryptopals.numbers :refer :all]))

(defn ziplists [list1 list2]
  (map vector list1 list2))


(defn hexor [string1 string2]
  (bin->hex (map #(apply bit-xor %) (ziplists (hex->bin string1) (hex->bin string2)))))

(defn hexor-single-byte [string1 character]
  (let [mask (string/join (repeat (/ (count string1) 2) (char->hex character)))]
    (hexor string1 mask)))
