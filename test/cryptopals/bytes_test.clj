(ns cryptopals.bytes-test
  (:require [expectations :refer [expect]]
            [cryptopals.bytes :refer :all]))


;; hexchars->int helper function to convert two-digit hex bytes to integers
(expect 1  (hexbyte->int "01"))
(expect 2  (hexbyte->int "02"))
(expect 10 (hexbyte->int "0a"))
(expect 15 (hexbyte->int "0f"))
(expect nil (hexbyte->int "g"))
(expect nil (hexbyte->int "0g"))
(expect 16  (hexbyte->int "10"))
(expect 255 (hexbyte->int "ff"))


(expect [0 0 1] (zero-pad 3 [1]))
(expect [0 0 1] (zero-pad 3 [0 0 1]))
(expect [1 0 1] (zero-pad 3 [1 0 1]))
(expect [0 0 0 0 0 1] (zero-pad 6 [1]))
(expect [0 0 0 0 0 1 0 1 0 1 0 1] (zero-pad 6 [1 0 1 0 1 0 1]))

; int->bin converts ints to binary representation
(expect [0 0 0 0 0 0 0 0] (int->bin 0))
(expect [0 0 0 0 0 0 0 1] (int->bin 1))
(expect [0 0 0 0 0 0 1 0] (int->bin 2))
(expect [0 0 0 0 1 0 0 0] (int->bin 8))
(expect [0 1 0 0 0 0 0 1] (int->bin 65))


;; hex->bin helper function to convert hex strings to binary
(expect [0 0 0 0 0 0 0 0] (hexbyte->bin "00"))
(expect [0 0 0 0 0 0 0 1] (hexbyte->bin "01"))
(expect [0 0 0 0 0 0 1 0] (hexbyte->bin "02"))
(expect [0 0 0 0 0 0 1 1] (hexbyte->bin "03"))
(expect [0 0 0 0 0 1 0 0] (hexbyte->bin "04"))
(expect [0 0 0 0 1 0 0 0] (hexbyte->bin "08"))
(expect [0 0 0 0 1 0 1 0] (hexbyte->bin "0a"))
(expect [0 0 0 1 0 0 0 0] (hexbyte->bin "10"))
(expect [0 0 0 1 1 1 1 1] (hexbyte->bin "1f"))

(expect [0 0 0 0 0 0 0 0] (hex->bin "00"))
(expect [0 0 0 0 0 0 0 1] (hex->bin "01"))
(expect [0 0 0 0 0 0 1 0] (hex->bin "02"))
(expect [0 0 0 0 0 0 1 1] (hex->bin "03"))
(expect [0 0 0 0 0 1 0 0] (hex->bin "04"))
(expect [0 0 0 0 1 0 0 0] (hex->bin "08"))
(expect [0 0 0 0 1 0 1 0] (hex->bin "0a"))
(expect [0 0 0 1 0 0 0 0] (hex->bin "10"))
(expect [0 0 0 1 1 1 1 1] (hex->bin "1f"))
(expect [] (hex->bin "z"))

;; convert binary sequence back to ints
(expect 0 (bin->int [0]))
(expect 1 (bin->int [1]))
(expect 2 (bin->int [1 0]))
(expect 3 (bin->int [1 1]))

(expect "00" (bin->hex [0 0 0 0 0 0 0 0]))
(expect "01" (bin->hex [0 0 0 0 0 0 0 1]))
(expect "02" (bin->hex [0 0 0 0 0 0 1 0]))
(expect "03" (bin->hex [0 0 0 0 0 0 1 1]))
(expect "10" (bin->hex [0 0 0 1 0 0 0 0]))


; hex to and from ascii strings/characters
(expect "farts" (hex->string "6661727473"))
(expect "63" (string->hex "c"))
(expect "6377" (string->hex "cw"))
(expect "706c696e7468" (string->hex "plinth"))

(expect 37 (hamming "this is a test" "wokka wokka!!!"))
(expect 0 (hamming [0 1 0 1] [0 1 0 1]))
(expect 1 (hamming [0 0 1] [0 0 0]))
