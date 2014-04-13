(ns kaggle-loan.data
  (:require
   [clojure.data.csv :as csv]
   [clojure.java.io :as io]))


(defn parse-csv [csv-path]
  (with-open [in-file (io/reader csv-path)]
    (doall
      (csv/read-csv in-file))))

(defn read-number [s]
  (if (= s "NA")
    0
    (read-string s)))

(defn fitness-cases-from-strings [data]
  (for [row_of_strings data]
    (let [row_of_numbers (map read-number row_of_strings)]
      [(drop-last row_of_numbers) (last row_of_numbers)])))

(defn fitness-cases-from-csv [csv-path]
  (-> csv-path parse-csv fitness-cases-from-strings))
;normalize penalty change fitness
;implicit fitness sharing
;historically assessed hardness
