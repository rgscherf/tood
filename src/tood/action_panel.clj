(ns tood.action-panel
  (:require [lanterna.screen :as s]
            [tood.render-utils :as utils]))

(defn write-panel!
  [screen]
  (let [ui-rows 1
        screen-size (s/get-size screen)]
    (s/put-string screen
                  0
                  (- (second screen-size) (inc ui-rows))
                  (-> screen-size first utils/hr))))
