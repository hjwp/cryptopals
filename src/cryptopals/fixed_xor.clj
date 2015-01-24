(ns cryptopals.fixed-xor
  (:require
   [clojure.string :as string]
   [cryptopals.bytes :refer :all]))


;; (defn hexor [string1 string2]
;;   (string/join (map (int->hex (map bit-xor (map hexchar->int string1) (map hexchar->int string2))))))

(string/join
   (map int->hex
    (map bit-xor
      (map hexchar->int "abc123")
      (map hexchar->int "616161"))))

(defn hexor [string1 string2]
  (string/join
   (map int->hex
    (map bit-xor
      (map hexchars->int (partition 2 string1))
      (map hexchars->int (partition 2 string2))))))

(defn hexor-single-byte [string1 character]
  (string/join
   (map int->hex
    (map bit-xor
      (map hexchars->int (partition 2 string1))
      (repeat (int character))))))
