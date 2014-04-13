(ns kaggle-loan.evolve
  (:use
   [clojush.pushgp.pushgp]
   [clojush.random]
   [clojush.pushstate]
   [clojush.interpreter])
  (:require
   [kaggle-loan.error :as error]))

(try
  (define-registered
    aux_index_from_int
    (fn [state]
      (if (not (empty? (:integer state)))
        (let [int-item (stack-ref :integer 0 state)
              auxiliary-seq (stack-ref :auxiliary 0 state)
              auxiliary-item (nth auxiliary-seq (mod int-item (count auxiliary-seq)))
              result-state (pop-item :integer state)]
          (push-item auxiliary-item :exec result-state))
        state)))
  (catch Exception e (str "Already defined index: " (.getMessage e))))


(defn argmap [fitness-casses input-count]
  {:error-function (error/error-function fitness-casses)
   :atom-generators (list (fn [] (rand-int input-count))
                          'aux_index_from_int
                          'float_div
                          'float_mult
                          'float_add
                          'float_sub
                          'float_mod
                          'float_frominteger
                          'float_min
                          'integer_div
                          'integer_mult
                          'integer_add
                          'integer_sub
                          'integer_mod
                          'integer_fromfloat
                          'integer_min)
   ;   :population-size 500
   :print-errors false
   ;   :error-threshold 805
   ;   :print-csv-logs true
   ;   :print-json-logs true
   :return-simplified-on-failure true
   :max-generations 10})
