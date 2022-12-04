(ns aoc2022.day4
  (:require [aoc2022.utils :as u]
            [clojure.string :as string]
            [clojure.set :refer [intersection]]))


(defn process-input [input]
  (map (fn [x]
         (let [[[p1min p1max] [p2min p2max]] (mapv #(string/split % #"-") (string/split x #","))]
           [{:min (Integer/parseInt p1min)
             :max (Integer/parseInt p1max)}
            {:min (Integer/parseInt p2min)
             :max (Integer/parseInt p2max)}]))
       (string/split (u/read-file input) #"\n")))

(defn includes? [p1 p2]
  (or
   (and (>= (:min p1) (:min p2))
        (<= (:max p1) (:max p2)))
   (and (>= (:min p2) (:min p1))
        (<= (:max p2) (:max p1)))))

(defn solve1 [input]
  (let [pairs (process-input input)]
    (count (filter (fn [[p1 p2]] (includes? p1 p2)) pairs))))

(defn solve2 [input]
  (let [pairs (process-input input)]
    (count (filter (fn [pair] (seq (apply intersection pair)))
                   (map (fn [[p1 p2]]
                          [(set (range (:min p1) (inc (:max p1))))
                           (set (range (:min p2) (inc (:max p2))))]) pairs)))))

(comment
  (solve1 "day4/input.txt")
  (solve2 "day4/input.txt")
)
