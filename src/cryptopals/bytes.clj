(ns cryptopals.bytes
  (:require [clojure.string :as string]))


(def hexchar->bin
  {
   \0 [0 0 0 0]
   \1 [0 0 0 1]
   \2 [0 0 1 0]
   \3 [0 0 1 1]
   \4 [0 1 0 0]
   \5 [0 1 0 1]
   \6 [0 1 1 0]
   \7 [0 1 1 1]
   \8 [1 0 0 0]
   \9 [1 0 0 1]
   \a [1 0 1 0]
   \b [1 0 1 1]
   \c [1 1 0 0]
   \d [1 1 0 1]
   \e [1 1 1 0]
   \f [1 1 1 1]})

(def hexchar->int
  (into {} (map hash-map "0123456789abcdef" (range 0 16))))

(def bin->hexchar
  (into {} (for [[key val] hexchar->bin] [val key])))

(def int->hexchar
  (into {} (for [[key val] hexchar->int] [val key])))

(def allhexbytes
  (for [first-digit "0123456789abcdef" second-digit "0123456789abcdef"] (str first-digit second-digit)))

(def int->hexbyte
  (into {} (map hash-map (range 256) allhexbytes)))

(def hexbyte->int
  (into {} (map hash-map allhexbytes (range 256))))

(defn hexstring->hexbytes [hexstring]
  (map string/join (partition 2 hexstring)))

(defn hex->bytes [hexstring]
  (map hexbyte->int (hexstring->hexbytes hexstring)))

(defn tostring [byteseq]
  (string/join (map char byteseq)))


(defn zero-pad [desired-num binary-digits]
  (let [pad (- desired-num (rem (count binary-digits) desired-num))]
    (if (= pad desired-num)
      binary-digits
      (concat (repeat pad 0) binary-digits))))

(defn- int->bin-recursive [number]
   (cond
     (< number 2) [number]
    :else (concat
           (int->bin-recursive (int (/ number 2)))
           [(mod number 2)])))

(defn int->bin [number]
  (zero-pad 8 (int->bin-recursive number)))


(def hexbyte->bin
  (into {} (for [hexbyte allhexbytes] {hexbyte (int->bin (hexbyte->int hexbyte))})))

(def bin->hexbyte
  (into {} (for [[hexbyte bits] hexbyte->bin] [bits hexbyte])))


(defn hex->bin [hex-string]
  (flatten (map hexbyte->bin (hexstring->hexbytes hex-string))))

(defn hex->string [hexstring]
  (string/join (map char (hex->bytes hexstring))))


(defn bin->hex [binary-digits]
  (let [grouped-bits (partition 8 (zero-pad 8 binary-digits))]
    (string/join (map bin->hexbyte grouped-bits))))



(defn two-to-the-n [n]
  (reduce * (repeat n 2N)))
(defn sixteen-to-the-n [n]
  (reduce * (repeat n 16N)))


(defn bin->int [binary-digits]
  (let [first-digit (first binary-digits)
        remainder (rest binary-digits)]
    (if (empty? binary-digits) 0
      (+ (* first-digit (two-to-the-n (count remainder)))
       (bin->int remainder)))))


(defn hexstring->hexchars [hex-string]
  (map string/join (partition 2 hex-string)))

(defn string->hex [string]
  (string/join (map int->hexbyte (map int string))))


(defmulti hamming
  (fn [bytes-or-string1 bytes-or-string2]
    (if (integer? (first bytes-or-string1)) :bits :string)))

(defmethod hamming :string [fromstring tostring]
  (reduce + (map bit-xor
                 (hex->bin (string->hex fromstring))
                 (hex->bin (string->hex tostring)))))

(defmethod hamming :bits [frombits tobits]
  (reduce + (map bit-xor frombits tobits)))
