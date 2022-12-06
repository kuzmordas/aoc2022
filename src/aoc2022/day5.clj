(ns aoc2022.day5
  (:require [aoc2022.utils :as u]
            [clojure.string :as string]))

(defn create-crates [crates-str]
  (let [crates-line-str (string/split crates-str #"\n")
        crates (reduce (fn [acc curr] (assoc acc curr [])) {}
                       (vec (remove empty? (string/split (last crates-line-str) #" "))))
        lines (map #(partition 3 4 (seq %)) (butlast crates-line-str))]
    (loop [lines lines
           result crates]
      (if-let [line (first lines)]
        (recur (rest lines)
               (reduce (fn [acc [idx v]]
                         (if (every? #(= \space %) v)
                           acc
                           (update acc (str (inc idx)) conj (str (second v)))))
                       result
                       (map-indexed vector line)))
        (reduce (fn [acc [k v]] (assoc acc k (vec (reverse v)))) {} result)))))

(defn create-steps [steps-str]
  (let [lines (string/split steps-str #"\n")]
    (map (fn [line]
           (let [[count from to] (vec (remove #(or (= "move" %) (=  "from" %) (= "to" %))
                                              (string/split line #" ")))]
             {:count (Integer/parseInt count)
              :from from
              :to to})) lines)))

(defn parse-input [input]
  (let [s (string/split (u/read-file input) #"\n\n")
        crates (create-crates (first s))
        steps (create-steps (second s))]
  {:crates crates
   :steps steps}))

(defn execute-step1 [crates step]
  (loop [count (:count step)
         result crates]
    (if (= count 0)
      result
      (recur (dec count)
             (let [from (get result (:from step))
                   to   (get result (:to step))
                   peeked    (peek from)
                   next-from (if peeked (pop from) from)
                   next-to   (if peeked (conj to peeked) to)]
               (assoc result (:from step) next-from (:to step) next-to))))))

(defn solve1 [input]
  (let [parsed (parse-input input)
        result (reduce (fn [res step] (execute-step1 res step))
                       (:crates parsed)
                       (:steps parsed))]
    (string/join "" (remove nil?
                            (map second
                                 (sort #(< (first %) (first %2))
                                       (map (fn [[k v]] [(Integer/parseInt k) (last v)]) result)))))))

(defn execute-step2 [crates step]
  (let [from      (get crates (:from step))
        to        (get crates (:to step))
        items     (take-last (:count step) from)
        next-from (vec (drop-last (:count step) from))
        next-to   (into to items)]
    (assoc crates (:from step) next-from (:to step) next-to)))

(defn solve2 [input]
  (let [parsed (parse-input input)
        result (reduce (fn [res step] (execute-step2 res step))
                       (:crates parsed)
                       (:steps parsed))]
    (string/join "" (remove nil?
                            (map second
                                 (sort #(< (first %) (first %2))
                                       (map (fn [[k v]] [(Integer/parseInt k) (last v)]) result)))))))
(comment
  (solve1 "day5/input.txt")
  (solve2 "day5/input.txt")
)
