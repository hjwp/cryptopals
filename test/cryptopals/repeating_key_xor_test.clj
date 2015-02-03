(ns cryptopals.repeating-key-xor-test
  (:require [expectations :refer [expect]]
            [cryptopals.bytes :refer :all]
            [cryptopals.fixed-xor :refer :all]
            [cryptopals.repeating-key-xor :refer :all]))


;; Implement repeating-key XOR


;; In repeating-key XOR, you'll sequentially apply each byte of the key;
;; the first byte of plaintext will be XOR'd against I, the next C, the next E, then I again for the 4th byte, and so on.


(expect (string->hex "abc") "616263")

(expect (repeating-key-xor "abc" "abcdef") (hexor "616263646566" "616263616263"))
(expect (repeating-key-xor "abc" "hello there") (hexor (string->hex "hello there") "616263616263616263616263"))

;; Here is the opening stanza of an important work of the English language:

;; Burning 'em, if you ain't quick and nimble
;; I go crazy when I hear a cymbal

;; Encrypt it, under the key "ICE", using repeating-key XOR.
;; It should come out to:
;; 0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272
;; a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f


(expect
 (str "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272"
      "a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f")
 (repeating-key-xor "ICE" "Burning 'em, if you ain't quick and nimble\nI go crazy when I hear a cymbal"))




;;     Let KEYSIZE be the guessed length of the key; try values from 2 to (say) 40.
;;     For each KEYSIZE, take the first KEYSIZE worth of bytes, and the second KEYSIZE worth of bytes, and find the edit distance between them. Normalize this result by dividing by KEYSIZE.
;;     The KEYSIZE with the smallest normalized edit distance is probably the key. You could proceed perhaps with the smallest 2-3 KEYSIZE values. Or take 4 KEYSIZE blocks instead of 2 and average the distances.

(expect 4 (probable-keysizes (repeating-key-xor "ab" "this is a test")))
(expect 6 (probable-keysizes (repeating-key-xor "abc" "this is a shmest")))
(expect 12 (probable-keysizes (repeating-key-xor "abcdef" "This is a test too, a much longer one, with a larger key size")))

;;     Now that you probably know the KEYSIZE: break the ciphertext into blocks of KEYSIZE length.
;;     Now transpose the blocks: make a block that is the first byte of every block, and a block that is the second byte of every block, and so on.
;;     Solve each block as if it was single-character XOR. You already have code to do this.
;;     For each block, the single-byte XOR key that produces the best looking histogram is the repeating-key XOR key byte for that block. Put them together and you have the key.

