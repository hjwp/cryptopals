(ns cryptopals.base64-test
  (:require [expectations :refer [expect]]
            [cryptopals.base64 :refer :all]))

;; base64 char of an integer
(expect \A (int->base64char 0))
(expect \K (int->base64char 10))
(expect \a (int->base64char 26))
(expect \z (int->base64char 51))
(expect \0 (int->base64char 52))
(expect \9 (int->base64char 61))
(expect \+ (int->base64char 62))
(expect \/ (int->base64char 63))


;; and finally, our base64 function
(expect "TWFu" (hexstring->base64 "4d616e"))  ; from wikipedia. 0x4d616e = "Man"
(expect
 "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"
 (hexstring->base64 "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"))

;; and back again for challeng 6
(expect "4d616e" (base64->hexstring "TWFu"))  ; from wikipedia. 0x4d616e = "Man"

(expect
 "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
 (base64->hexstring "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"))
