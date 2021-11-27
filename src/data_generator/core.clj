(ns data-generator.core
  (:gen-class)
  (:require [clojure.walk :as walk]
            [clojure.set :as set]
            [data-generator.model :as model]
            [data-generator.date-utils :as date-utils]))

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
        (println (str "Random activity: " (rand-nth model/activities)))
        (println (str "Now: " (date-utils/now)))
        (println (str "Next: " (date-utils/random-next-date (date-utils/now)))))
      (do
        (println "Encountered errors:")
        (doseq [error errors] (println (str "- " error)))))))

(defn- cmd-params
  "Parses the command line params to the key value map."
  [args]
  (walk/keywordize-keys (apply hash-map args)))

(defn- validate-params
  "Validates the command line params passed to the program."
  [params]
  (map #(str "Unrecognized parameter '" (name %) "'.") (set/difference
                                                         (set (keys params))
                                                         known-program-params)))