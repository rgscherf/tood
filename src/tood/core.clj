(ns tood.core
  (:import java.util.Date)
  (:require [tood.print :as print]
            [clojure.core.match :refer [match]]
            [tood.repl :as nrepl]
            [lanterna.screen :as s]))

;; (nrepl/start-nrepl 7855)
(defonce scr (s/get-screen :swing))

;; data store
(defonce input
  (atom
   [{:title    "Buy apples"
     :added    (Date. 117 03 26)
     :done     false
     :priority 1}
    {:title    "Measure pictures"
     :added    (Date. 117 02 13)
     :done     false
     :priority 5}]))

(defn- sorted-input-items
  []
  (let [hellos (map str (sort-by :date  @input))]
    (map (fn [a b] [a b])
         (range (count hellos))
         hellos)))

(defn print-todos!
  [screen]
  (let [print-cmd (map #(s/put-string screen
                                      1
                                      (first %)
                                      (second %))
                       (sorted-input-items))]
    (s/clear screen)
    (dorun print-cmd)
    (s/redraw screen)))

(comment
  (s/redraw scr)
  (s/clear scr)
  (print-todos! scr))

(defn clear-print
  [screen f & args]
  (s/clear screen)
  (apply f args)
  (s/redraw screen))

(defn main
  [args])

(defn -main
  [& args]
  (main args))

