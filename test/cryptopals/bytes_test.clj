(ns cryptopals.bytes-test
  (:require [expectations :refer [expect]]
            [cryptopals.bytes :refer :all]))


;; hexchar->int helper function to convert hex characters to integers
(expect 1 (hexchars->int "1"))
(expect 2 (hexchars->int "2"))
(expect 10 (hexchars->int "a"))
(expect 15 (hexchars->int "f"))
(expect nil (hexchars->int "g"))
(expect 16 (hexchars->int "10"))
(expect 255 (hexchars->int "ff"))


(expect [0 0 1] (zero-pad [1] 3))
(expect [0 0 1] (zero-pad [0 0 1] 3))
(expect [1 0 1] (zero-pad [1 0 1] 3))
(expect [0 0 0 0 0 1] (zero-pad [1] 6))
(expect [0 0 0 0 0 1 0 1 0 1 0 1] (zero-pad [1 0 1 0 1 0 1] 6))

; int->bin converts ints to binary representation
(expect [0] (int->bin 0))
(expect [1] (int->bin 1))
(expect [1 0] (int->bin 2))
(expect [1 0 0 0] (int->bin 8))
(expect [1 0 0 0 0 0 1] (int->bin 65))


;; hex->bin helper function to convert hex strings to binary
(expect [0 0 0 0] (hexchar->bin \0))
(expect [0 0 0 1] (hexchar->bin \1))
(expect [0 0 1 0] (hexchar->bin \2))
(expect [0 0 1 1] (hexchar->bin \3))
(expect [0 1 0 0] (hexchar->bin \4))
(expect [1 0 0 0] (hexchar->bin \8))
(expect [1 0 1 0] (hexchar->bin \a))
(expect [1 1 1 1] (hexchar->bin \f))

(expect [0 0 0 0] (hex->bin "0"))
(expect [0 0 0 1] (hex->bin "1"))
(expect [0 0 1 0] (hex->bin "2"))
(expect [0 0 1 1] (hex->bin "3"))
(expect [0 1 0 0] (hex->bin "4"))
(expect [1 0 0 0] (hex->bin "8"))
(expect [1 0 1 0] (hex->bin "a"))
(expect [0 0 0 1 0 0 0 0] (hex->bin "10"))
(expect [0 0 0 1 1 1 1 1] (hex->bin "1f"))
(expect nil (hex->bin "z"))

;; convert binary sequence back to ints
(expect 0 (bin->int [0]))
(expect 1 (bin->int [1]))
(expect 2 (bin->int [1 0]))
(expect 3 (bin->int [1 1]))

;; convert binary sequence back to hex
(expect \0 (bin->hexchar [0]))
(expect \1 (bin->hexchar [1]))
(expect \2 (bin->hexchar [1 0]))
(expect \a (bin->hexchar [1 0 1 0]))
(expect \f (bin->hexchar [1 1 1 1]))

(expect "0" (bin->hex [0]))
(expect "1" (bin->hex [1]))
(expect "2" (bin->hex [1 0]))
(expect "3" (bin->hex [1 1]))
(expect "10" (bin->hex [0 0 0 1 0 0 0 0]))

(expect "a" (int->hex 10))

; hex to and from ascii strings/characters
(expect \a (hexchars->char "61"))
(expect \z (hexchars->char "7a"))
(expect "farts" (hex->string "6661727473"))

(expect "63" (char->hex \c))
(expect "77" (char->hex \w))
(expect "6377" (string->hex "cw"))
(expect "706c696e7468" (string->hex "plinth"))
