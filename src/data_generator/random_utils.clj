(ns data-generator.random-utils
  (:import (java.util Random)))

(defn rand-str
  ^String [^Long len]
  (let [leftLimit 97
        rightLimit 122
        random (Random.)
        stringBuilder (StringBuilder. len)
        diff (- rightLimit leftLimit)]
    (dotimes [_ len]
      (let [ch (char (.intValue ^Double (+ leftLimit (* (.nextFloat ^Random random) (+ diff 1)))))]
        (.append ^StringBuilder stringBuilder ch)))
    (.toString ^StringBuilder stringBuilder)))