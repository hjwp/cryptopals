(ns cryptopals.repeating-key-xor-test
  (:require [expectations :refer [expect]]
            [cryptopals.bytes :refer :all]
            [cryptopals.fixed-xor :refer :all]
            [cryptopals.repeating-key-xor :refer :all]))


;; Implement repeating-key XOR


;; In repeating-key XOR, you'll sequentially apply each byte of the key; the first byte of plaintext will be XOR'd against I, the next C, the next E, then I again for the 4th byte, and so on.
(expect (hexor "abcdef" "abcabc") (repeating-key-xor "abc" "abcdef"))
(expect (hexor "hello there" "abcabcabcab") (repeating-key-xor "abc" "hello there"))

;; Here is the opening stanza of an important work of the English language:

;; Burning 'em, if you ain't quick and nimble
;; I go crazy when I hear a cymbal

;; Encrypt it, under the key "ICE", using repeating-key XOR.
;; It should come out to:

;; 0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272
;; a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f

(expect
 "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272"
 (repeating-key-xor "ICE" "Burning 'em, if you ain't quick and nimble"))
