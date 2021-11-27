(ns data-generator.date-utils
  (:import (java.util Date)))

(defn ^Long minutes->seconds [minutes]
  (* minutes 60))

(defn ^Long minutes->millis [minutes]
  (* minutes 60 1000))

(defn ^Long seconds->millis [seconds]
  (* seconds 1000))

(defn ^Date now
  "Returns the current date."
  [] (new Date))

(defn ^Date random-next-date
  "Returns a date that is 1 - 5 minutes after the input date."
  [date]
  (new Date (+
              (.getTime date)
              (+
                (rand-int (minutes->millis 5))
                (minutes->millis 1)))))
