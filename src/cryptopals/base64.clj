(ns cryptopals.base64
  (:require [clojure.string :as string]))

(def A-offset (int \A))
(def a-offset (int \a))
(def zero-offset (int \0))

(defn valid-hexstring? [string]
  (re-matches #"[\da-f]+" string))

(defn power [x n]
  (reduce * (repeat n x)))

(defn hex->int [hex-string]
  (if (valid-hexstring? hex-string)
    (read-string (str "0x" hex-string))))

(defn int->bin [number]
  (if (< number 2) [number]
    (concat (int->bin (biginteger (/ number 2))) [(mod number 2)])))

(defn hex->bin [hex-string]
  (if (re-matches #"[\da-f]+"  hex-string)
    (int->bin (hex->int hex-string))))

(defn bin->int [binary-digits]
  (read-string (str "2r" (clojure.string/join binary-digits))))


(defn base64char [number]
  (cond
   (< number 26) (str (char (+ A-offset number)))
   (< number 52) (str (char (+ a-offset (- number 26))))
   (< number 62) (str (- number 52))
   (= number 62) "+"
   (= number 63) "/"))

(defn zero-pad [binary-digits]
  (let [pad (- 6 (mod (count binary-digits) 6))]
    (concat (repeat pad 0) binary-digits)))


(defn base64 [hex-string]
  (string/join (map base64char (map bin->int (partition 6 (zero-pad (hex->bin hex-string)))))))
