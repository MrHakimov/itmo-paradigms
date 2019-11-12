; @author: Muhammadjon Hakimov
; Modification of HW9 - removed diffs and parse, added toStringSuffix and bitwise operations
(declare ZERO)
(declare ONE)
(declare TWO)

(definterface OperationsInterface
  (evaluate [variablesValues])
  (toString [])
  (toStringSuffix []))

(deftype ConstantOperation [value]
  OperationsInterface
  (evaluate [_ _] value)
  (toString [this] (format "%.1f" (double (.value this))))
  (toStringSuffix [this] (format "%.1f" (double (.value this)))))

(defn Constant [value] (ConstantOperation. value))

(def ZERO (Constant 0))
(def ONE (Constant 1))
(def TWO (Constant 2))

(deftype VariableOperation [name]
  OperationsInterface
  (evaluate [_ variablesValues] (variablesValues name))
  (toString [_] (str name))
  (toStringSuffix [_] (str name)))

(defn Variable [name] (VariableOperation. name))

(deftype AbstractUnaryOperation [symbol f a]
  OperationsInterface
  (evaluate [_ variablesValues] (f (.evaluate a variablesValues)))
  (toString [_] (str "(" symbol " " (.toString a) ")"))
  (toStringSuffix [_] (str "(" (.toStringSuffix a) " " symbol ")")))

(deftype AbstractOperation [symbol f args]
  OperationsInterface
  (evaluate [this variablesValues] (apply f (map #(.evaluate % variablesValues) (.args this))))
  (toString [this] (str "(" symbol " " (clojure.string/join " " (map #(.toString %) (.args this))) ")"))
  (toStringSuffix [this] (str "(" (clojure.string/join " " (map #(.toStringSuffix %) (.args this))) " " symbol ")")))

(defn evaluate [expression variablesValues] (.evaluate expression variablesValues))
(defn toString [expression] (.toString expression))
(defn toStringSuffix [expression] (.toStringSuffix expression))

(defn foldLeft [f] (fn [& args] (reduce f (first args) (rest args))))

(defn Add [& args] (AbstractOperation. '+ + args))
(defn Subtract [& args] (AbstractOperation. '- - args))
(defn Multiply [& args] (AbstractOperation. '* * args))
(defn Divide [& args] (AbstractOperation. '/ (foldLeft #(/ (double %1) (double %2))) args))

(defn And [& args] (AbstractOperation. '& (foldLeft
                                            #(Double/longBitsToDouble (bit-and (Double/doubleToLongBits %1) (Double/doubleToLongBits %2)))) args))
(defn Or [& args] (AbstractOperation. '| (foldLeft
                                           #(Double/longBitsToDouble (bit-or (Double/doubleToLongBits %1) (Double/doubleToLongBits %2)))) args))
(defn Xor [& args] (AbstractOperation. (symbol "^") (foldLeft
                                                      #(Double/longBitsToDouble (bit-xor (Double/doubleToLongBits %1) (Double/doubleToLongBits %2)))) args))

(defn Negate [a] (AbstractUnaryOperation. 'negate - a))
(defn Square [a] (AbstractUnaryOperation. 'square #(* % %) a))
(defn Sqrt [a] (AbstractUnaryOperation. 'sqrt #(Math/sqrt (Math/abs %)) a))

(def OPERATIONS {
                 '+           Add
                 '-           Subtract
                 '*           Multiply
                 '/           Divide
                 'negate      Negate
                 'square      Square
                 'sqrt        Sqrt
                 '&           And
                 '|           Or
                 (symbol "^") Xor
                 })
