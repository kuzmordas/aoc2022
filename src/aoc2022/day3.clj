(ns aoc2022.day3
  (:require [aoc2022.utils :as u]
            [clojure.string :as string]
            [clojure.set :refer [intersection]]))

(defn split-on-two-parts [s]
  (let [length (count s)]
    [(subs s 0 (/ length 2))
     (subs s (/ length 2))]))

(defn find-intersection [s1 s2]
  (first (intersection (set s1) (set s2))))


(defn solve1 [input]
  (let [chars (map char (concat (range 97 123) (range 65 91) ))
        items (string/split (u/read-file input) #"\n")]
    (reduce (fn [acc item]
              (+ acc
                 (inc (.indexOf chars
                                (apply find-intersection (split-on-two-parts item))))))
            0
            items)))

(defn solve2 [input]
  (let [chars (map char (concat (range 97 123) (range 65 91)))
        items (partition 3 3 (string/split (u/read-file input) #"\n"))]
    (reduce (fn [acc item]
              (+ acc
                 (inc (.indexOf chars
                                (first (apply intersection (map set item)))))))
            0
            items)))

(comment
  (solve1 "day3/input.txt")
  (solve2 "day3/input.txt")
)
