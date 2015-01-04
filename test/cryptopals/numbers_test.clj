(ns cryptopals.numbers-test
  (:require [expectations :refer :all]
            [cryptopals.numbers :refer :all]))


;; hexchar->int helper function to convert hex characters to integers
(expect 1 (hexchar->int \1))
(expect 2 (hexchar->int \2))
(expect 10 (hexchar->int \a))
(expect 15 (hexchar->int \f))
(expect nil (hexchar->int \g))

; int->bin helper should allow numbers greater than a single hex char repr
(expect nil (int->bin  16))


(expect [0 0 1] (zero-pad [1] 3))
(expect [0 0 1] (zero-pad [0 0 1] 3))
(expect [1 0 1] (zero-pad [1 0 1] 3))
(expect [0 0 0 0 0 1] (zero-pad [1] 6))
(expect [0 0 0 0 0 1 0 1 0 1 0 1] (zero-pad [1 0 1 0 1 0 1] 6))


;; hex->bin helper function to convert hex strings to binary
(expect [0] (hexchar->bin \0))
(expect [1] (hexchar->bin \1))
(expect [1 0] (hexchar->bin \2))
(expect [1 1] (hexchar->bin \3))
(expect [1 0 0] (hexchar->bin \4))
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
