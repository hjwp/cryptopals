(ns cryptopals.base64
  (:require
   [clojure.string :as string]
   [cryptopals.bytes :refer :all]))

(def int->base64char
  (into {} (concat
            (map hash-map (range 0 26)  "ABCDEFGHIJKLMNOPQRSTUVWXYZ")
            (map hash-map (range 26 52) "abcdefghijklmnopqrstuvwxyz")
            (map hash-map (range 52 62) "0123456789")
            {62 \+, 63 \/})))


(def base64char->int
  (into {} (for [[key val] int->base64char] [val key])))


(defn base64->hexstring [b64string]
  (->>
   b64string
   (map base64char->int)
   (map int->bin)
   (map #(take-last 6 %))
   flatten
   (zero-pad 8)
   (partition 8)
   (map bin->hex)
   string/join))


(defn hexstring->base64 [hex-string]
  (->>
   hex-string
   hex->bin
   (zero-pad 6)
   (partition 6)
   (map bin->int)
   (map int->base64char)
   string/join))
