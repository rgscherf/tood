(ns tood.padding)

(defn additional-spaces
  [num-spaces]
  (apply str (repeat num-spaces " ")))

(defn pad-string
  ;; TODO add 'padwith' param (default to \space)
  ;; TODO implement padwith and pad-left? as options map
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
         (str string
              spaces-as-string)
         (str spaces-as-string
              string))))))
