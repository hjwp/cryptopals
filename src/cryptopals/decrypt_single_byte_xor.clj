(ns cryptopals.decrypt-single-byte-xor
  (:require
   [clojure.string :as string]
   [cryptopals.fixed-xor :refer :all]
   [cryptopals.bytes :refer :all]
   [cryptopals.score :refer :all]))


(defn get-all-decrypts [secret]
  (for [possible-byte (map char (range 256))]
    (let [decrypted (hex->string (hexor-single-byte secret possible-byte))]
      {:cyphertext secret,
       :score (score decrypted),
       :byte possible-byte,
       :plaintext decrypted})))

(defn most-likely-single-byte-xor-decrypt [secret]
  (first (sort-by :score (get-all-decrypts secret))))
