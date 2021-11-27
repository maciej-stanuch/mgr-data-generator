(ns data-generator.model
  (:import (org.bson.types ObjectId)
           (java.util Date)
           (clojure.lang PersistentVector)))

(def ^:const activities [:walking
                         :running
                         :sitting])

(defrecord Session [^ObjectId id
                    ^String owner
                    ^Date start-date
                    ^Date end-date
                    ^String session-key
                    ^PersistentVector activities])

(defrecord Exercise [^ObjectId id
                     ^String type
                     ^Date created-at
                     ^Integer repetitions])

(defrecord TeamMember [^ObjectId id
                       ^PersistentVector examinations
                       ^String role
                       ^String status
                       ^ObjectId user
                       ^ObjectId team])

(defrecord User [^ObjectId id
                 ^PersistentVector roles])

