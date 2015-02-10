(ns cryptopals.decrypt-single-byte-xor
  (:require
   [clojure.string :as string]
   [cryptopals.fixed-xor :refer :all]
   [cryptopals.bytes :refer :all]
   [cryptopals.score :refer :all]))


(defn get-all-decrypts [cyphertext]
  (for [possible-char (map char (range 256))]
    (let [decrypted (bytes->string (hexor-single-byte possible-char cyphertext))]
      {:cyphertext cyphertext,
       :score (score decrypted),
       :char possible-char,
       :plaintext decrypted})))

(defn most-likely-single-byte-xor-decrypt [secret]
  (first (sort-by :score (get-all-decrypts secret))))
