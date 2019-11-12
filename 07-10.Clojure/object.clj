; @author: Muhammadjon Hakimov
(declare ZERO)
(declare ONE)
(declare TWO)

(definterface OperationsInterface
  (evaluate [variablesValues])
  (diff [diffVariable])
  (toString []))

(deftype ConstantOperation [value]
  OperationsInterface
  (evaluate [_ _] value)
  (diff [_ _] ZERO)
  (toString [this] (format "%.1f" (double (.value this)))))

(defn Constant [value] (ConstantOperation. value))

(def ZERO (Constant 0))
(def ONE (Constant 1))
(def TWO (Constant 2))

(deftype VariableOperation [name]
  OperationsInterface
  (evaluate [_ variablesValues] (variablesValues name))
  (diff [_ diffVariable] (if (= name diffVariable) ONE ZERO))
  (toString [_] (str name)))

(defn Variable [name] (VariableOperation. name))

(deftype AbstractUnaryOperation [symbol f diff a]
  OperationsInterface
  (evaluate [_ variablesValues] (f (.evaluate a variablesValues)))
  (toString [_] (str "(" symbol " " (.toString a) ")"))
  (diff [_ diffVariable] (diff diffVariable)))

(deftype AbstractOperation [symbol f diff args]
  OperationsInterface
  (evaluate [this variablesValues] (apply f (map #(.evaluate % variablesValues) (.args this))))
  (toString [this] (str "(" symbol " " (clojure.string/join " " (map #(.toString %) (.args this))) ")"))
  (diff [_ diffVariable] (diff diffVariable)))

(defn evaluate [expression variablesValues] (.evaluate expression variablesValues))
(defn toString [expression] (.toString expression))
(defn diff [expression diffVariable] (.diff expression diffVariable))

(defn Add [& args] (AbstractOperation. '+ +
                                       (fn [diffVariable] (apply Add (map #(diff % diffVariable) args))) args))
(defn Subtract [& args] (AbstractOperation. '- -
                                  (fn [diffVariable] (apply Subtract (map #(diff % diffVariable) args))) args))
(defn Multiply [& args] (AbstractOperation. '* * (fn [diffVariable] (reduce #(Add (Multiply %1 (diff %2 diffVariable))
                                                            (Multiply %2 (diff %1 diffVariable))) args)) args))
(defn Divide [& args] (AbstractOperation. '/ #(/ (double %1) (double %2))
                              (fn [diffVariable] (reduce #(Divide (Subtract (Multiply %2 (diff %1 diffVariable))
                                              (Multiply %1 (diff %2 diffVariable))) (Multiply %2 %2)) args)) args))

(defn Negate [a] (AbstractUnaryOperation. 'negate - #(Negate (diff a %)) a))
(defn Square [a] (AbstractUnaryOperation. 'square #(* % %) #(Multiply (diff a %) (Multiply TWO a)) a))
(defn Sqrt [a] (AbstractUnaryOperation. 'sqrt #(Math/sqrt (Math/abs %)) #(Multiply (Multiply (diff a %) a)
                                                  (Divide ONE (Multiply TWO (Sqrt (Multiply (Square a) a))))) a))

(def OPERATIONS {
                 '+      Add
                 '-      Subtract
                 '*      Multiply
                 '/      Divide
                 'negate Negate
                 'square Square
                 'sqrt   Sqrt
                 })

(defn parse [expression]
  (cond
    (list? expression) (apply (OPERATIONS (first expression)) (map parse (rest expression)))
    (number? expression) (Constant expression)
    (symbol? expression) (Variable (str expression))
    ))

(defn parseObject [expression] (parse (read-string expression)))
