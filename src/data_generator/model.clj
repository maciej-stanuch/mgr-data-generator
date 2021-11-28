(ns data-generator.model
  (:require [data-generator.date-utils :as date-utils]
            [data-generator.random-utils :as random])
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

(defn random-session []
  (let [start-date (date-utils/random-next-date (date-utils/now))
        end-date (date-utils/random-next-date start-date)]
    (->Session
      (ObjectId.)
      (random/rand-str 32)
      start-date
      end-date
      (random/rand-str 32)
      (rest (shuffle activities)))))

