(ns kaggle-loan.error
  (:use
   [clojush.pushgp.pushgp]
   [clojush.random]
   [clojush.pushstate]
   [clojush.interpreter])
  (:require
   [clojure.math.numeric-tower :as math]
   [taoensso.timbre :as timbre]))

(timbre/refer-timbre) ; Provides useful Timbre aliases in this ns


(defn error-function [fitness-cases]
  (fn error-function [program]
       (doall
        (for [[input target] fitness-cases]
          (do
            (let [initial-push (p :error-function/make-push-state (make-push-state))
                  push-filled-with-aux (p :error-function/push-aux (push-item input :auxiliary initial-push))
                  finished-push (p :error-function/run (run-push program push-filled-with-aux))
                  top-float (top-item :float finished-push)]
              (p :error-function/return-error (if (number? top-float)
                (math/abs (- top-float target))
                (math/abs target)))))))))

;normalize penalty change fitness
;implicit fitness sharing
;historically assessed hardness
