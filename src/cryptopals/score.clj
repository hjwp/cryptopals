(ns cryptopals.score
  (:require
   [clojure.string :as string]
   [cryptopals.fixed-xor :refer :all]
   [cryptopals.bytes :refer :all]))

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

(def letters (set (keys standard-frequencies)))

(defn map-over-hash [function hashmap]
  (into {} (for [[key, val] hashmap]
             [key (function val)])))

(defmulti lowercase (fn [string-or-char] (if (char? string-or-char) :char :string)))
(defmethod lowercase :char [character] (first (string/lower-case character)))
(defmethod lowercase :string [string] (string/lower-case string))

(defn abs [integer]
  (if (> integer 0) integer (- integer)))

(defn count-letters [string]
  (map-over-hash count (group-by identity string)))

(defn letter-frequencies [string]
  (map-over-hash
   (fn [letter-count] (* 100 (/ letter-count (count string))))
   (count-letters string)))

(defn count-lowercase [string]
  (let [letters-or-space (conj letters \space)]
  (count (filter letters-or-space string)))
  )

(defn fix-string [string]
  (lowercase (reduce str (string/split string #" "))))

(defn letter-frequency-variances [fixed-string]
  (let
    [actual-frequencies (letter-frequencies fixed-string)]
    (for [letter (into #{} (concat letters fixed-string))]
      (abs (-
            (get standard-frequencies letter 0)
            (get actual-frequencies letter 0))))))

(defn score [string]
  (let [fixed-string (fix-string string)]
    (+ (reduce + (letter-frequency-variances fixed-string))
       (* 2 (- (count string) (count-lowercase string))))))


;; (time (dotimes [_ 100000] (score "abcdefghijklmnopqrstuvwxyz")))
