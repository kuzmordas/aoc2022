(ns aoc2022.utils
  (:require [clojure.java.io :as io]))

(defn read-file [path] (-> path io/resource .getFile slurp))
