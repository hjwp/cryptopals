(ns cryptopals.core)

(defn hex->int [hex-string]
  (if (re-matches #"[\da-f]+"  hex-string)
    (read-string (str "0x" hex-string))))


(defn increment [binary-digits]
  (cond
   (= 0 (last binary-digits)) (vec (concat (butlast binary-digits) [1]))
   (= [1] binary-digits) [1 0]
   true (vec (concat (increment (butlast binary-digits)) [0]))))

(defn int->bin [number accum]
  (if (= number 0) accum
    (int->bin (- number 1) (increment accum))))

(defn hex->bin [hex-string]
  (if (re-matches #"[\da-f]+"  hex-string)
    (int->bin (hex->int hex-string) [0])))

(defn bin->int [binary-digits]
  (read-string (str "2r" (clojure.string/join binary-digits))))

(def A-offset (int \A))
(def a-offset (int \a))

(defn base64char [number]
  (cond
   (< number 26) (str (char (+ A-offset number)))
   (< number 52) (str (char (+ a-offset (- number 26))))
   (< number 62) (str (- number 52))
   (= number 62) "+"
   (= number 63) "/"))

