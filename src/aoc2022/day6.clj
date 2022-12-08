(ns aoc2022.day6
  (:require [aoc2022.utils :as u]))

(defn repeated? [l distinct-num] (not= distinct-num (count (frequencies l))))

(defn solve [input distinct-num]
  (loop [chanks (partition distinct-num 1 (seq (u/read-file input)))
         res    0]
    (when-let [chank (first chanks)]
      (if (repeated? chank distinct-num)
        (recur (rest chanks) (inc res))
        (+ distinct-num res)))))

(comment
  (solve "day6/input.txt" 4)
  (solve "day6/input.txt" 14)
)
