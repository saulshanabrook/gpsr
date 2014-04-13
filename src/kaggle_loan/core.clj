(ns kaggle-loan.core
  (:require
   [clojush.pushgp.pushgp :as pushgp]
   [kaggle-loan.evolve :as evolve]
   [kaggle-loan.divide-data :as divide-data]
   [kaggle-loan.error :as error]
   [kaggle-loan.data :as data]
   [kaggle-loan.utils :as utils]
   [taoensso.timbre :as timbre]
   [clojure.core.matrix.stats :refer [mean]]))

(timbre/refer-timbre) ; Provides useful Timbre aliases in this ns

(defn fitness-cases-from-csv-path [input-resource-path number-lines]
   (let [file-path (divide-data/make-partial-resource-file input-resource-path number-lines)]
      (data/fitness-cases-from-csv file-path)))

(defn solve
  [fitness-cases]
   (let [input-count (count (first (first fitness-cases)))]
      (let [return-push (utils/with-out-value
                          (p :pushgp (pushgp/pushgp (evolve/argmap fitness-cases input-count))))
            mean-error (/ (:total-error return-push) (count (:errors return-push)))]
        {:program (:program return-push) :mean-error mean-error :errors (:errors return-push)})))

(defn test-solution
  [fitness-cases program]
  (let [errors ((error/error-function fitness-cases) program)]
    {:mean-error (mean errors) :errors errors}))

(defn train-and-test [input-resource-path number-training-lines number-testing-lines]
  (info "Training on " number-training-lines " lines of resources/" input-resource-path)
  (info "Creating partial CSV to train on...")
  (def training-file-path (divide-data/make-partial-resource-file input-resource-path number-training-lines))
  (info "Parsing CSV created at " training-file-path "...")
  (def training-fitness-cases (data/fitness-cases-from-csv training-file-path))
  (info "Running PushGP on fitness cases...")
  (def solution (solve training-fitness-cases))

  (info "Testing solution on " number-testing-lines " lines of resources/" input-resource-path)
  (info "Creating partial CSV to test on...")
  (def testing-file-path (divide-data/make-partial-resource-file input-resource-path number-testing-lines))
  (info "Parsing CSV created at " testing-file-path "...")
  (def testing-fitness-cases (data/fitness-cases-from-csv training-file-path))
  (info "Testing fitness cases against solution...")
  (def test-results (spy :info (test-solution testing-fitness-cases (:program solution))))
  {:program (:program solution) :training-errors (:errors solution) :testing-errors (:errors test-results)})
