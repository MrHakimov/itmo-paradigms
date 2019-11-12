; @author: Muhammadjon Hakimov
(def constant constantly)
(defn variable [name] (fn [vars] (vars name)))

(defn abstractOperation [f] (fn [& args] (fn [vars] (apply f (map #(% vars) args)))))
(defn foldLeft [f] (fn [& args] (reduce f (first args) (rest args))))

(def add (abstractOperation +))
(def subtract (abstractOperation -))
(def multiply (abstractOperation *))
(def negate (abstractOperation -))
(def divide (abstractOperation (foldLeft #(/ (double %1) (double %2)))))
(def square (abstractOperation #(* % %)))
(def sqrt (abstractOperation #(Math/sqrt (Math/abs %))))

(def OPERATIONS {
                 '+      add
                 '-      subtract
                 '*      multiply
                 '/      divide
                 'negate negate
                 'square square
                 'sqrt   sqrt
                 })

(defn parse [expression]
  (cond
    (list? expression) (apply (OPERATIONS (first expression)) (map parse (rest expression)))
    (number? expression) (constant expression)
    (symbol? expression) (variable (str expression)))
  )

(defn parseFunction [expression] (parse (read-string expression)))
