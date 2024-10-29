;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
;                                                                                       |
;                                      10 HW                                            |
;                                                                                       |
;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
(require '[clojure.math :as math])

(defn operation [op]
  (fn [& args]
    (fn [map]
      (apply op (mapv #(% map) args)))))

(def add (operation +))

(def subtract (operation -))

(def multiply (operation *))

(defn divide [a b]
  (fn [map] (/ (double (a map)) (double (b map)))))

(def negate (operation -))

(def sin (operation math/sin))

(def cos (operation math/cos))

(defn constant [a]
  (fn [_] a))

(defn variable [var]
  (fn [map] (get map var)))

(def operators-map {
                    '+      add
                    '-      subtract
                    '*      multiply
                    '/      divide
                    'negate negate
                    'cos    cos
                    'sin    sin
                    })

(defn parse-expr [mappa func-cnst func-var]
  (fn recurs-parse [seq-list]
    (cond
      (seq? seq-list) (apply (get mappa (first seq-list)) (map recurs-parse (rest seq-list)))
      (number? seq-list) (func-cnst seq-list)
      (symbol? seq-list) (func-var (str seq-list)))))

(defn parseFunction [string]
  ((parse-expr operators-map constant variable) (read-string string)))

;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
;                                                                                       |
;                                      11 HW                                            |
;                                                                                       |
;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

(require '[clojure.string :as string])

(defn my-division [a b]
  (/ a (double b)))

(defn High-Operation-Class [op sign]
  (fn [& operands]
    {
     :evaluate        (fn [map] (apply op (mapv #((:evaluate %) map) operands)))
     :toString        (str "(" sign " " (string/join " " (map :toString operands)) ")")
     :toStringPostfix (str "(" (string/join " " (map :toStringPostfix operands)) " " sign ")")
     }))

(def Exp (High-Operation-Class math/exp 'exp))

(def Ln (High-Operation-Class math/log 'ln))

(def Negate (High-Operation-Class - 'negate))

(defn Constant [cnst]
  {
   :evaluate        (fn [_] cnst)
   :toString        (str cnst)
   :toStringPostfix (str (double cnst))
   })

(defn Variable [var]
  {
   :evaluate        (fn [args] (get args (string/lower-case (subs var 0 1))))
   :toString        (str var)
   :toStringPostfix (str var)
   })

(def Add (High-Operation-Class + '+))

(def Subtract (High-Operation-Class - '-))

(def Multiply (High-Operation-Class * '*))

(def Divide (High-Operation-Class my-division '/))

(defn evaluate [expr args]
  ((:evaluate expr) args))

(defn toString [expr]
  (:toString expr))

(defn toStringPostfix [expr]
  (:toStringPostfix expr))

(def object-operators
  {
   '+      Add
   '-      Subtract
   '*      Multiply
   '/      Divide
   'negate Negate
   'exp    Exp
   'ln     Ln
   })

(defn parseObject [str]
  ((parse-expr object-operators Constant Variable) (read-string str)))

;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
;                                                                                       |
;                                      12 HW                                            |
;                                                                                       |
;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

(load-file "parser.clj")

(defn -show [result]
  (if (-valid? result)
    (str "-> " (pr-str (-value result)) " | " (pr-str (apply str (-tail result))))
    "!"))

(defn tabulate [parser inputs]
  (run! (fn [input] (printf "    %-10s %s\n" (pr-str input) (-show (parser input)))) inputs))

(defn +number []
  (fn [input]
    (let [result (take-while #(Character/isDigit (char %)) input)]
      (if (empty? result)
        nil
        {:value (read-string (apply str result)), :tail (drop (count result) input)}))))

(def operators
  {
   "+"      Add
   "-"      Subtract
   "*"      Multiply
   "/"      Divide
   "negate" Negate
   })

(def *all-chars
  (mapv char (range 0 128)))

(def *space
  (+char (apply str (filter #(Character/isWhitespace %) *all-chars))))

(def *ws
  (+ignore (+star *space)))

(defn func-for-reverse [[arr op]]
  (apply (get operators op) arr))

(defn parse-with-ws [p]
  (+seqn 0 *ws p *ws))

(def parseObjectPostfix
  (let
    [*digit (+plus (+char "0123456789"))
     *second-number-part (+str (+seq (+char ".") (+str *digit)))
     *number (+map Constant (+map read-string (+str (+seq (+opt (+char "-")) (+str *digit) *second-number-part))))
     *string (+map Variable (+map str (+str (+plus (+char "xyzXYZ")))))]
    (letfn [(*operation []
              (+seqn 1
                     (+char "(")
                     (parse-with-ws (+map func-for-reverse
                                          (+seq (parse-with-ws (+plus (+seqn 0 (delay (*value)) *ws)))
                                                (+str (+star (+char "+-*/negate"))))))
                     (+char ")")))
            (*value []
              (+or *number (*operation) *string))]
      (+parser (parse-with-ws (*value))))))

(def expr (Add (Constant -627.0) (Variable "Xx")))

(println (evaluate expr {"x" 2}))

;(letfn [(*operation []
;          (+seqn 1
;                 (+char "(")
;                 *ws
;                 (+map func-for-reverse
;                       (+seq *ws (+plus (+seqn 0 (delay (*value)) *ws))
;                             (+str (+star (+char "+-*/negate")))))
;                 *ws
;                 (+char ")")))


;(println (parseObjectPostfix "(10.0 negate)"))