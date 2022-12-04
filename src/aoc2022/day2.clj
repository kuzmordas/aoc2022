(ns aoc2022.day2
  (:require [aoc2022.utils :as u]
            [clojure.string :as string]))

;; A X - rock
;; B Y - paper
;; C Z - scissors

(def items {"A" :rock
            "X" :rock
            "B" :paper
            "Y" :paper
            "C" :scissors
            "Z" :scissors})

(def points {:rock 1
             :paper 2
             :scissors 3})

(defn calc-points [opponent you]
  (cond
    (and (= opponent :rock) (= you :paper)) 6
    (and (= opponent :rock) (= you :rock)) 3
    (and (= opponent :rock) (= you :scissors)) 0

    (and (= opponent :paper) (= you :paper)) 3
    (and (= opponent :paper) (= you :rock)) 0
    (and (= opponent :paper) (= you :scissors)) 6

    (and (= opponent :scissors) (= you :paper)) 0
    (and (= opponent :scissors) (= you :rock)) 6
    (and (= opponent :scissors) (= you :scissors)) 3))


(defn get-needed-item [opponent you]
  (cond
    (= you "X") (cond
                  (= opponent :rock) :scissors
                  (= opponent :paper) :rock
                  (= opponent :scissors) :paper)

    (= you "Y") (cond
                  (= opponent :rock) :rock
                  (= opponent :paper) :paper
                  (= opponent :scissors) :scissors)
    (= you "Z") (cond
                  (= opponent :rock) :paper
                  (= opponent :paper) :scissors
                  (= opponent :scissors) :rock)))

(defn solve1 [input]
  (let [variants (map #(string/split % #" ") (string/split (u/read-file input) #"\n"))]
    (reduce
     (fn [acc [opponent you]]
       (+ acc
          (calc-points (get items opponent)
                       (get items you))
          ((get items you) points))) 0 variants)))

(defn solve2 [input]
  (let [variants (map #(string/split % #" ") (string/split (u/read-file input) #"\n"))]
    (reduce
     (fn [acc [opponent you]]
       (+ acc
          (calc-points (get items opponent)
                       (get-needed-item (get items opponent) you))
          ((get-needed-item (get items opponent) you) points))) 0 variants)))

(comment
  (solve1 "day2/input.txt")
  (solve2 "day2/input.txt")
)
