(ns cryptopals.core
  (:require [clojure.string :as string]))

(def A-offset (int \A))
(def a-offset (int \a))
(def zero-offset (int \0))

(defn valid-hexchar? [character]
  (re-matches #"[\da-f]+" (str character)))
(defn valid-hexstring? [string]
  (re-matches #"[\da-f]+" string))

(defn power [x n]
  (reduce * (repeat n x)))

(defmulti hex->int (fn [input] (if (char? input) :char :string)))
(defmethod hex->int :char [hex-char]
  (if (valid-hexchar? hex-char)
    (read-string (str "0x" hex-char))))
(defmethod hex->int :string [hex-string]
  (if (valid-hexstring? hex-string)
    (let [length (count hex-string)]
      (reduce + (map
       (fn [pos] (* (power 16 (- length pos 1)) (hex->int (get hex-string pos))))
       (range length))))))

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
