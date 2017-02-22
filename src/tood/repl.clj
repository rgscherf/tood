(ns tood.repl
  (:require [clojure.tools.nrepl.server :as nrepl]))

;; starting and stopping an nREPL server
(defonce ^{:doc "Holds the running REPL server, if there is one, for later shutdown."}
  nrepl-server (atom nil))

(defn start-nrepl
  "Start a network REPL for debugging or remote control."
  [port]
  (try
    (swap! nrepl-server #(do (when % (nrepl/stop-server %)) (nrepl/start-server :port port)))
    (catch Throwable t
      (println t "failed to start nREPL"))))

