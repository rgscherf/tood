(ns tood.render-utils)

(defn hr
  [length]
  (str 
   "+"
   (clojure.string/join (repeat (- length 2)
                                "-"))
   "+"))
