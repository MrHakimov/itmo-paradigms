; @authors: Muhammadjon Hakimov and Dmitriy Mozhevitin
;  created: 17.04.2019 02:59:39

(defn abstractOperation [f & arguments]
  (apply mapv f arguments))

(def v+ (partial abstractOperation +))
(def v- (partial abstractOperation -))
(def v* (partial abstractOperation *))

(defn scalar [firstVector secondVector]
  (reduce + (v* firstVector secondVector)))
(defn determinant [a b c d] (- (* a d) (* b c)))
(defn vect [firstVector secondVector]
  (def x1 (first firstVector))
  (def y1 (second firstVector))
  (def z1 (last firstVector))
  (def x2 (first secondVector))
  (def y2 (second secondVector))
  (def z2 (last secondVector))
  (vector
    (determinant y1 z1 y2 z2)
    (- (determinant x1 z1 x2 z2))
    (determinant x1 y1 x2 y2)))

(defn v*s [v s]
  (mapv (partial * s) v))
(defn s*v [s v]
  (v*s v s))

(def m+ (partial abstractOperation v+))
(def m- (partial abstractOperation v-))
(def m* (partial abstractOperation v*))

(defn multiplyMatrixTo [f first second]
  (mapv (partial f first) second))

(defn m*s [m s]
  (multiplyMatrixTo s*v s m))
(defn m*v [m v]
  (multiplyMatrixTo scalar v m))
(defn transpose [matrix]
  (apply mapv vector matrix))
(defn m*m [firstMatrix secondMatrix]
  (transpose (mapv (partial m*v firstMatrix) (transpose secondMatrix))))

(defn abstractShapelessOperation [f & arguments]
  (if (number? (first arguments))
    (apply f arguments)
    (apply mapv (partial abstractShapelessOperation f) arguments)))

(defn s+ [& args] (apply (partial abstractShapelessOperation +) args))
(defn s- [& args] (apply (partial abstractShapelessOperation -) args))
(defn s* [& args] (apply (partial abstractShapelessOperation *) args))
