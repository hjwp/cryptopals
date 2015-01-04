(ns cryptopals.base64
  (:require
   [clojure.string :as string]
   [cryptopals.numbers :refer :all]))

(def A-offset (int \A))
(def a-offset (int \a))
(def zero-offset (int \0))

(defn base64char [number]
  (cond
   (< number 26) (str (char (+ A-offset number)))
   (< number 52) (str (char (+ a-offset (- number 26))))
   (< number 62) (str (- number 52))
   (= number 62) "+"
   (= number 63) "/"))


(defn base64 [hex-string]
  (string/join (map base64char (map bin->int (partition 6 (zero-pad (hex->bin hex-string) 6))))))
