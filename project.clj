(defproject kaggle-loan "0.1.0-SNAPSHOT"
  :description "Predict loan defaults with GP, for Kaggle"
  :url "http://www.github.com/saulshanabrook/kaggle-loan/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-gorilla "0.2.0"]]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clojure-csv/clojure-csv "2.0.1"]
                 [org.clojure/data.csv "0.1.2"]
                 [clojush "1.3.58"]
                 [fipp "0.4.1"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [com.taoensso/timbre "3.1.6"]
                 [net.mikera/core.matrix.stats "0.4.0"]]
  :main clojush.core)
