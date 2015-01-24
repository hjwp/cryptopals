(ns cryptopals.bytes
  (:require [clojure.string :as string]))


(def hexchar->bin
  {
   \0 [0 0 0 0]
   \1 [0 0 0 1]
   \2 [0 0 1 0]
   \3 [0 0 1 1]
   \4 [0 1 0 0]
   \5 [0 1 0 1]
   \6 [0 1 1 0]
   \7 [0 1 1 1]
   \8 [1 0 0 0]
   \9 [1 0 0 1]
   \a [1 0 1 0]
   \b [1 0 1 1]
   \c [1 1 0 0]
   \d [1 1 0 1]
   \e [1 1 1 0]
   \f [1 1 1 1]})

(def hexchar->int
  {
   \0 0, \1 1, \2 2, \3 3, \4 4, \5 5, \6 6, \7 7, \8, 8, \9 9,
   \a 10, \b 11, \c 12, \d 13, \e 14, \f 15})

(defn ziplists [list1 list2]
  (map vector list1 list2))


(defn zero-pad [binary-digits desired-num]
  (let [pad (- desired-num (rem (count binary-digits) desired-num))]
    (if (= pad desired-num)
      binary-digits
      (concat (repeat pad 0) binary-digits))))


(defn valid-hexchar? [character]
  (hexchar->bin character))

(defn valid-hexstring? [string]
  (every? valid-hexchar? string))


(defn hexchars->int [hexchars]
  (when (valid-hexstring? hexchars)
    (if (= 1 (count hexchars))
      (hexchar->int (last hexchars))
      (+
       (* 16 (hexchar->int (first hexchars)))
       (hexchar->int (last hexchars))))))


(defn int->bin [number]
  (cond
   (< number 2) [number]
   :else (concat
          (int->bin (int (/ number 2)))
          [(mod number 2)])))


(defn hex->bin [hex-string]
  (if (valid-hexstring? hex-string)
    (reduce concat (map hexchar->bin hex-string))))


(defn two-to-the-n [n]
  (reduce * (repeat n 2N)))


(defn bin->int [binary-digits]
  (let [first-digit (first binary-digits)
        remainder (rest binary-digits)]
    (if (empty? binary-digits) 0
      (+ (* first-digit (two-to-the-n (count remainder)))
       (bin->int remainder)))))


(def A-offset (int \A))
(def a-offset (int \a))
(def zero-offset (int \0))

(defn bin->hexchar [binary-digits]
  (let [number (bin->int binary-digits)]
    (cond
     (< number 10) (char (+ zero-offset number))
     (< number 16) (char (+ a-offset (- number 10))))))

(defn bin->hex [binary-digits]
  (string/join (map bin->hexchar (partition 4 (zero-pad binary-digits 4)))))

(defn int->hex [number]
  (bin->hex (int->bin number)))


(defn hexchars->char [hex-digit]
  (char (hexchars->int hex-digit)))

(defn hexstring->hexchars [hex-string]
  (map string/join (partition 2 hex-string)))

(defn hex->string [hex-string]
  (string/join (map hexchars->char (hexstring->hexchars hex-string))))


(defn char->hex [character]
  (string/join (bin->hex (int->bin (int character)))))

(defn string->hex [string]
  (string/join (map char->hex string)))
