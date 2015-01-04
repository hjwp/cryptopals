(ns cryptopals.base64
  (:require [clojure.string :as string]))

(def A-offset (int \A))
(def a-offset (int \a))
(def zero-offset (int \0))

(defn all [things predicate]
  (= (count (filter predicate things)) (count things)))

(defn valid-hexchar? [character]
  (re-matches #"[\da-f]+" (str character)))

(defn valid-hexstring? [string]
  (all string valid-hexchar?))

(defn zero-pad [binary-digits desired-num]
  (let [pad (- desired-num (mod (count binary-digits) desired-num))]
    (if (= pad desired-num)
      binary-digits
      (concat (repeat pad 0) binary-digits))))


(defn hexchar->int [character]
  (if (valid-hexchar? character)
    (read-string (str "0x" (str character)))))

(defn int->bin [number]
  (cond
   (< number 2) [number]
   (> number 15) nil
   :else (concat (int->bin (int (/ number 2))) [(mod number 2)])))

(defn hexchar->bin [character]
  (int->bin (hexchar->int character)))

(defn hex->bin [hex-string]
  (if (valid-hexstring? hex-string)
    (reduce concat (map #(zero-pad % 4) (map hexchar->bin hex-string)))))

(defn bin->int [binary-digits]
  (read-string (str "2r" (string/join binary-digits))))


(defn base64char [number]
  (cond
   (< number 26) (str (char (+ A-offset number)))
   (< number 52) (str (char (+ a-offset (- number 26))))
   (< number 62) (str (- number 52))
   (= number 62) "+"
   (= number 63) "/"))


(defn base64 [hex-string]
  (string/join (map base64char (map bin->int (partition 6 (zero-pad (hex->bin hex-string) 6))))))
