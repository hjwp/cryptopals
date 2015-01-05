(ns cryptopals.single-byte-xor-cypher
  (:require
   [cryptopals.numbers :refer :all]))

(def standard-frequencies
  {\a 8.167
   \b 1.492
   \c 2.782
   \d 4.253
   \e 12.702
   \f 2.228
   \g 2.015
   \h 6.094
   \i 6.966
   \j 0.153
   \k 0.772
   \l 4.025
   \m 2.406
   \n 6.749
   \o 7.507
   \p 1.929
   \q 0.095
   \r 5.987
   \s 6.327
   \t 9.056
   \u 2.758
   \v 0.978
   \w 2.360
   \x 0.150
   \y 1.974
   \z 0.074})

(def letters (keys standard-frequencies))

(defn map-over-hash [function hashmap]
  (into {} (for [[key, val] hashmap]
             [key (function val)])))


(defn expected-frequencies [string-length]
    (into {} (for [[letter frequency] standard-frequencies]
               [letter
                (* frequency string-length)])))

(defn in [haystack needle]
  (some #(= % needle) haystack))

(defn count-letters [string]
  (let [grouped (group-by identity string)]
    (into {} (for [letter letters]
               [letter
                (if (in string letter)
                    (count (get grouped letter))
                    0)]))))

(defn letter-frequencies [string]
  (map-over-hash
   (fn [letter-count] (/ (* 100 letter-count) (count string)))
   (count-letters string)))

(defn score [string]
  (let [actual-frequencies (letter-frequencies string)]
    (- 100
       (reduce +
               (for [letter letters]
                   (- (standard-frequencies letter) (actual-frequencies letter)))))))
