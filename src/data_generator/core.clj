(ns data-generator.core
  (:gen-class)
  (:use clojure.walk
        clojure.set)
  (:require [data-generator.database.mongo :as mongo]))

(declare cmd-params
         validate-params)

(def ^:const known-program-params
  #{:--db-ip
    :--db-port
    :--data-size})

(defn -main
  [& args]
  (println "Reading arguments...")
  (let [params (cmd-params args)
        errors (validate-params params)]
    (if (empty? errors)
      (do
        (println "Running with parameters:")
        (println params)
        (let [conn (mongo/db-connection (:--db-ip params) (:--db-port params))]
          (do
            (println conn)
            (mongo/db-connection-close conn))))
      (do
        (println "Encountered errors:")
        (doseq [error errors] (println (str "- " error)))))))

(defn- cmd-params
  "Parses the command line params to the key value map."
  [args]
  (keywordize-keys (apply hash-map args)))

(defn- validate-params
  "Validates the command line params passed to the program."
  [params]
  (map #(str "Unrecognized parameter '" (name %) "'.") (difference
                                                         (set (keys params))
                                                         known-program-params)))