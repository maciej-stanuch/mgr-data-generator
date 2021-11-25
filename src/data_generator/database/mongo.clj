(ns data-generator.database.mongo
  (:require [monger.core :as mg]))

(defn db-connection
  "Returns a monger database connection."
  [db-ip db-port]
  (mg/connect {:host db-ip
               :port (Integer/parseInt db-port)}))

(defn db-connection-close
  "Closes the database connection."
  [db-connection]
  (mg/disconnect db-connection))
