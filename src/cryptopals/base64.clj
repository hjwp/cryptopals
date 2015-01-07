(ns cryptopals.base64
  (:require
   [clojure.string :as string]
   [cryptopals.bytes :refer :all]))

(defn base64char [number]
  (cond
   (< number 26) (char (+ A-offset number))
   (< number 52) (char (+ a-offset (- number 26)))
   (< number 62) (char (+ zero-offset (- number 52)))
   (= number 62) \+
   (= number 63) \/))



(defn base64 [hex-string]
  (string/join (map base64char (map bin->int (partition 6 (zero-pad (hex->bin hex-string) 6))))))
