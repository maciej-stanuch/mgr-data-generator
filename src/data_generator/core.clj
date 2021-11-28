(ns data-generator.core
  (:gen-class)
  (:require [clojure.walk :as walk]
            [clojure.set :as set]
            [taoensso.timbre :as log]
            [data-generator.database.populate :as populate]))

(def ^{:const true :private true} known-program-params
  #{:--db-ip
    :--db-port
    :--records-number
    :--db-type})

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
      (do (log/info "Running with parameters:")
          (log/info params)
          (log/info "Populating the" (or (:--db-type params) "default") "database.")
          (populate/populate-database-with-records (:--records-number params) (:--db-type params)))
      (do (log/error "Encountered errors:")
          (doseq [error errors] (log/error (str "> " error)))))))