(defn abstract [op]
      (fn [vect1 vect2]
          (mapv op vect1 vect2)))
;; NOTE:: one abstract for all
(defn transpose [matrix]
      (apply mapv vector matrix))

(defn idx-sbtr-mtp [vect1 vect2 idx1 idx2]
      (- (* (nth vect1 idx1) (nth vect2 idx2)) (* (nth vect1 idx2) (nth vect2 idx1))))

(defn abstract-op [op vect scal]
      (vec (map #(op % scal) vect)))

(def v+ (abstract +))

(def v- (abstract -))

(def v* (abstract *))

(def vd (abstract /))

(defn v*s [vect scal] (abstract-op * vect scal))

(defn scalar [vect1 vect2] (reduce + 0 (v* vect1 vect2)))

(def m+ (abstract v+))

(def m- (abstract v-))

(def m* (abstract v*))

(def md (abstract vd))

(defn m*s [matrix scal]
      (abstract-op v*s matrix scal))

(defn m*m [m1 m2]
      (let [transp-m2 (transpose m2)]
           (mapv (fn [row-m1]
                     (vec (map (fn [col-m2]
                                   (reduce + (map * row-m1 col-m2))) transp-m2))) m1)))

(defn m*v [mtrx vctr]
      (mapv #(scalar % vctr) mtrx))

(defn vect [vect1 vect2]
      [(idx-sbtr-mtp vect1 vect2 1 2)
       (idx-sbtr-mtp vect1 vect2 2 0)
       (idx-sbtr-mtp vect1 vect2 0 1)])

(def c+ (abstract m+))

(def c- (abstract m-))

(def c* (abstract m*))

(def cd (abstract md))