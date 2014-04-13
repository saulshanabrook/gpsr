(ns kaggle-loan.divide-data
  (:require
   [clojure.java.shell :as shell]
   [clojure.java.io :as io]
   [taoensso.timbre :as timbre]))

(timbre/refer-timbre) ; Provides useful Timbre aliases in this ns

(defn make-partial-file
  [input-path output-path number-lines]
  (p :shuf (shell/sh "/usr/local/bin/gshuf" input-path "-o" output-path "-n" (str number-lines))))

(defn get-resource-full-path [resource-path]
  (.getFile (io/resource resource-path)))

(defn make-partial-resource-file
  [input-resource-path number-lines]
  (let [input-path (get-resource-full-path input-resource-path)
        out-relative-path-parts [".partial" input-resource-path (str number-lines)]
        _ (apply io/make-parents out-relative-path-parts)
        output-path (.getAbsolutePath (apply io/file out-relative-path-parts))]
    (make-partial-file input-path output-path number-lines)
    output-path))
