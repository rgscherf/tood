(ns tood.core
  (:import java.util.Date)
  (:require [tood.print :as print]
            [clojure.core.match :refer [match]]
            [tood.repl :as nrepl]
            [lanterna.screen :as s]))

;; (nrepl/start-nrepl 7855)
(defonce screen (s/get-screen :swing))

;; data store
(defonce state
  (atom
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
     :priority 5}]))

(defn additional-spaces
  [num-spaces]
  (apply str (repeat num-spaces " ")))

(defn pad-string
  "Pad a string to intended length from the left (default) or right. If intended length is > the string's length, the string will be truncated."
  ([string intended-len]
   (pad-string string intended-len true))
  ([string intended-len pad-left?]
   (let [spaces-to-add (- intended-len
                          (count string))
         spaces-as-string (additional-spaces spaces-to-add)]
     (if (neg? spaces-to-add)
       (apply str (take intended-len string))
       (if pad-left?
         (str spaces-as-string
              string)
         (str string
              spaces-as-string))))))

(defn- stringify-todo
  [{:keys [title added done priority]}]
  (let [dateline (str (-> (.getYear added) (+ 1900))
                      "-"
                      (.getMonth added)
                      "-"
                      (.getDate added))]
    (str (pad-string title 20 false)
         " | added "
         (pad-string dateline 10)
         " | done? " done)))

(defn- numbered-todos
  []
  (let [hellos (sort-by :date @state)]
    (map (fn [a b] [a b])
         (range 1 (-> (count hellos) inc))
         (map stringify-todo hellos))))

(defn write-todos!
  [screen]
  (let [print-cmd (map #(s/put-string screen
                                      1
                                      (first %)
                                      (second %))
                       (numbered-todos))]
    (dorun print-cmd)))

(defn refresh-todos!
  []
  (s/clear screen)
  (write-todos! screen)
  (s/redraw screen))

(defn main
  [& args]
  (s/start screen)
  (refresh-todos!))

(defn -main
  [& args]
  (main args))

