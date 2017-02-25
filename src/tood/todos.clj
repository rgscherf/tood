(ns tood.todos
  (:require [lanterna.screen :as s]
            [tood.render-utils :as utils]
            [tood.padding :as padding]))

(defn- stringify-todo
  [{:keys [title added done priority]}]
  (let [title-len 30
        date-len 9
        dateline (str (-> (.getYear added) (+ 1900))
                      "-"
                      (.getMonth added)
                      "-"
                      (.getDate added))]
    (str "| "
         (if done
           (str \u2714)
           (str \u2715))
         " | "
         priority
         " | "
         (padding/pad-string title title-len)
         " | "
         (padding/pad-string dateline date-len false)
         " |")))

(def table-header
  (str "| D | P | "
       (padding/pad-string "TASK" 30)
       " | "
       (padding/pad-string "ADDED" 9)
       " | "))

(defn- numbered-todos
  "Return seq of [row-index-of-todo stringified-todo]. Param row-offset is the row to start writing on (due to table headers etc)."
  [row-offset state]
  (let [hellos (sort-by :date state)]
    (map (fn [a b] [a b])
         (range row-offset 999)
         (map stringify-todo hellos))))

(defn write-todos!
  [screen state]
  (let [todos (numbered-todos state 3)
        horizontal-rule (utils/hr (apply max
                                   (map #(-> % second count)
                                        todos)))
        print-cmd (map #(s/put-string screen
                                      1
                                      (first %)
                                      (second %))
                         todos)]
    (s/put-string screen 1 0 horizontal-rule)
    (s/put-string screen 1 1 table-header)
    (s/put-string screen 1 2 horizontal-rule)
    (dorun print-cmd)
    (s/put-string screen
                  1
                  (inc (apply max (map #(-> % first) todos)))
                  horizontal-rule)))
