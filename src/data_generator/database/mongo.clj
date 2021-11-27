(ns data-generator.database.mongo
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

(defn db-connection
  "Returns a monger database connection."
  [db-ip db-port]
  (mg/connect {:host db-ip
               :port (Integer/parseInt db-port)}))

(defn get-db [conn name]
  (mg/get-db conn name))

(defn db-insert-document [db collection document]
  (mc/insert-and-return db collection document))

(defn db-insert-document-batch [db collection documents]
  (mc/insert-batch db collection documents))

(defn db-connection-close
  "Closes the database connection."
  [db-connection]
  (mg/disconnect db-connection))
