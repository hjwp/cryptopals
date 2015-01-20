(ns cryptopals.bytes
  (:require [clojure.string :as string]))

(defn zero-pad [binary-digits desired-num]
  (let [pad (- desired-num (rem (count binary-digits) desired-num))]
    (if (= pad desired-num)
      binary-digits
      (concat (repeat pad 0) binary-digits))))


(defn valid-hexchar? [character]
  (re-matches #"[\da-f]+" (str character)))

(defn valid-hexstring? [string]
  (every? valid-hexchar? string))


(defn hexchar->int [character]
  (if (valid-hexchar? character)
    (read-string (str "0x" (str character)))))


(defn int->bin [number]
  (cond
   (< number 2) [number]
   :else (concat
          (int->bin (int (/ number 2)))
          [(mod number 2)])))

(defn hexchar->bin [character]
  (zero-pad (int->bin (hexchar->int character)) 4))

(defn hex->bin [hex-string]
  (if (valid-hexstring? hex-string)
    (reduce concat (map hexchar->bin hex-string))))


(defn bin->int [binary-digits]
  (read-string (str "2r" (string/join binary-digits))))


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


(defn hex->char [hex-digit]
  (char (hexchar->int hex-digit)))

(defn hexstring->hexchars [hex-string]
  (map string/join (partition 2 hex-string)))

(defn hex->string [hex-string]
  (string/join (map hex->char (hexstring->hexchars hex-string))))


(defn char->hex [character]
  (string/join (bin->hex (int->bin (int character)))))

(defn string->hex [string]
  (string/join (map char->hex string)))
