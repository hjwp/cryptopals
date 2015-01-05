(ns cryptopals.base64-test
  (:require [expectations :refer [expect]]
            [cryptopals.base64 :refer :all]))

;; base64 char of an integer
(expect \A (base64char 0))
(expect \K (base64char 10))
(expect \a (base64char 26))
(expect \z (base64char 51))
(expect \0 (base64char 52))
(expect \9 (base64char 61))
(expect \+ (base64char 62))
(expect \/ (base64char 63))


;; and finally, our base64 function
(expect "TWFu" (base64 "4d616e"))  ; from wikipedia. 0x4d616e = "Man"
(expect
 "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"
 (base64 "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"))
