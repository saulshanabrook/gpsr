(ns kaggle-loan.core
  (:require
   [clojush.pushgp.pushgp :as pushgp]
   [kaggle-loan.evolve :as evolve]
   [kaggle-loan.divide-data :as divide-data]
   [kaggle-loan.data :as data]
   [kaggle-loan.utils :as utils]))

(defn solve
  [input-resource-path number-lines]
   (let [file-path (divide-data/make-partial-resource-file input-resource-path number-lines)
         fitness-cases (data/fitness-cases-from-csv file-path)
         input-count (count (first (first fitness-cases)))]
      (let [return-push (utils/with-out-value
                          (pushgp/pushgp (evolve/argmap fitness-cases input-count)))
            mean-error (/ (:total-error return-push) (count (:errors return-push)))]
        {:program (:program return-push) :mean-error mean-error})))
