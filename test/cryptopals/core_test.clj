(ns cryptopals.core-test
  (:require [expectations :refer :all]
            [cryptopals.core :refer :all]))


;; 49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d
;; should produce
;; SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t

;; hex->int helper function to convert hex strings to integers
(expect 1 (hex->int "1"))
(expect 10 (hex->int "a"))
(expect 16 (hex->int "10"))
(expect 26 (hex->int "1a"))
(expect nil (hex->int "z"))


;; uh-oh, looks like i've [1decided to implement binary addition??

(expect [1] (increment [0]))
(expect [1 0] (increment [1]))
(expect [1 1] (increment [1 0]))
(expect [1 0 0] (increment [1 1]))
(expect [1 0 1] (increment [1 0 0]))
(expect [1 1 0] (increment [1 0 1]))


;; hex->bin helper function to convert hex strings to binary
(expect [0] (hex->bin "0"))
(expect [1] (hex->bin "1"))
(expect [1 0] (hex->bin "2"))
(expect [1 1] (hex->bin "3"))
(expect [1 0 0] (hex->bin "4"))
(expect [1 0 0 0] (hex->bin "8"))
(expect [1 0 0 0 0] (hex->bin "10"))
(expect [1 0 1 0] (hex->bin "a"))
(expect nil (hex->bin "z"))

;; convert binary sequence back to ints
(expect 0 (bin->int [0]))
(expect 1 (bin->int [1]))
(expect 2 (bin->int [1 0]))
(expect 3 (bin->int [1 1]))

;; base64 char of an integer
(expect "A" (base64char 0))
(expect "K" (base64char 10))
(expect "a" (base64char 26))
(expect "z" (base64char 51))
(expect "0" (base64char 52))
(expect "9" (base64char 61))
(expect "+" (base64char 62))
(expect "/" (base64char 63))
