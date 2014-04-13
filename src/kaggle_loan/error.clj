(ns kaggle-loan.error
  (:use
   [clojush.pushgp.pushgp]
   [clojush.random]
   [clojush.pushstate]
   [clojush.interpreter])
  (:require
    [clojure.math.numeric-tower :as math]))

(defn error-function [fitness-cases]
  (fn error-function [program]
    (doall
     (for [[input target] fitness-cases]
       (do
         (let [state (run-push program
                               (push-item input :auxiliary
                                          (make-push-state)))
               top-float (top-item :float state)]
           (if (number? top-float)
             (math/abs (- top-float target))
             (math/abs target))))))))

;normalize penalty change fitness
;implicit fitness sharing
;historically assessed hardness
