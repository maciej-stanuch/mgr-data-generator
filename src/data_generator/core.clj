(ns data-generator.core
  (:gen-class)
  (:require [clojure.walk :as walk]
            [clojure.set :as set]
            [taoensso.timbre :as log]
            [data-generator.model :as model]
            [data-generator.date-utils :as date-utils]
            [data-generator.database.mongo :as mongo]))

(def ^{:const true :private true} known-program-params
  #{:--db-ip
    :--db-port
    :--data-size})

(defn- cmd-params
  "Parses the command line params to the key value map."
  [args]
  (walk/keywordize-keys (apply hash-map args)))

(defn- validate-params
  "Validates the command line params passed to the program."
  [params]
  (if (empty? params)
    #{"Parameters are required to run the program."}
    (let [unrecognized-params (set/difference (set (keys params))
                                              known-program-params)]
      (map #(str "Unrecognized parameter '" (name %) "'.") unrecognized-params))))

(defn -main
  [& args]
  (log/info "Reading arguments...")
  (let [params (cmd-params args)
        errors (validate-params params)]
    (if (empty? errors)
      (do
        (log/info "Running with parameters:")
        (log/info params)
        (println (str "Random activity: " (rand-nth model/activities)))
        (println (str "Now:\t" (date-utils/now)))
        (println (str "Next:\t" (date-utils/random-next-date (date-utils/now))))
        (let [conn (mongo/db-connection (:--db-ip params) (:--db-port params))
              db (mongo/get-db conn "admin")]
          (mongo/db-insert-document db "session" {:test "test" :abc "abc"})))
      (do
        (log/error "Encountered errors:")
        (doseq [error errors] (log/error (str "> " error)))))))