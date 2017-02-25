(ns tood.core
  (:import java.util.Date)
  (:require [tood.padding :as padding]
            [tood.todos :as todos]
            [tood.action-panel :as panel]
            [tood.repl :as nrepl]
            [clojure.core.match :refer [match]]
            [lanterna.screen :as s]))

;; (nrepl/start-nrepl 7855)
(defonce screen (s/get-screen :swing))

;; data store
(defonce state
  (atom
   {:todos
    [{:title    "Buy apples"
      :added    (Date. 117 03 26)
      :done     false
      :priority 1}
     {:title    "Wear birkenstocks!"
      :added    (Date.)
      :done     true
      :priority 2}
     {:title    "Measure pictures"
      :added    (Date. 117 02 13)
      :done     false
      :priority 5}]}))

(defn refresh-todos!
  []
  (s/clear screen)
  (todos/write-todos! screen (:todos @state))
  (panel/write-panel! screen)
  (s/redraw screen))

(comment
  (refresh-todos!))

(defn main
  [& args]
  (s/start screen)
  (refresh-todos!))

(defn -main
  [& args]
  (main args))

;; (-main)
