(ns aoc2022.day1
  (:require [aoc2022.utils :as u]
            [clojure.string :as string]))

(defn solve1 [input]
  (let [input-str (u/read-file input)
        calories  (map (fn [x] (map #(Integer/parseInt %) (string/split x #"\n")))
                       (string/split input-str #"\n\n"))]
    (apply max (map (fn [x] (apply + x)) calories))))

(defn solve2 [input]
  (let [input-str (u/read-file input)
        calories  (map (fn [x] (map #(Integer/parseInt %) (string/split x #"\n")))
                       (string/split input-str #"\n\n"))]
    (apply + (take 3 (sort > (map (fn [x] (apply + x)) calories))))))

(comment
  (solve1 "day1/input.txt")
  (solve2 "day1/input.txt")
)
