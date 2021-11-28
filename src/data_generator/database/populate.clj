(ns data-generator.database.populate)

(defmulti populate-database-with-records
          (fn [_ db-type] (keyword db-type)))

(defmethod populate-database-with-records :default [n _]
  (println "Populating mongo?")
  (println "With " n " numbers"))

(defmethod populate-database-with-records :timescale [n _]
  (println "Populating timescale?" n))
