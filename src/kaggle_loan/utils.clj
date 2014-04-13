(ns kaggle-loan.utils)

(defmacro with-out-value
  "Evaluates exprs in a context in which *out* is bound to a fresh
  StringWriter.  Returns the whatever the exprs returned.

  http://stackoverflow.com/a/7151125/907060"
  [& body]
  `(let [s# (new java.io.StringWriter)]
     (binding [*out* s#]
       (let [v# ~@body]
         v#))))
