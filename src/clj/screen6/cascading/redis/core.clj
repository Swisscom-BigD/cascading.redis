(ns screen6.cascading.redis.core
  (:require
   [cascalog.api :refer :all]
   [cascalog.logic.ops :as logic.ops])
  (:import
   [screen6.cascading.redis RedisScheme RedisSinkTap]
   [cascading.tuple Fields])
  (:gen-class))

(def data
  [["a" 1]
   ["a" 2]
   ["b" 1]
   ["c" 2]])

(defn vectorize
  [item]
  [[item]])

(defn insert
  [left right]
  [(vec (concat left right))])

(defparallelagg join
  :init-var #'vectorize
  :combine-var #'insert)

(defn -main
  [& args]
  (let [sink (RedisSinkTap. "localhost" (RedisScheme. (Fields. (into-array String ["?key"])) (Fields. (into-array String ["?sum"]))))
        ;; sink (stdout)
        ]
    (?- sink
        (<- [?key ?sum]
            (data ?key ?val)
            (join ?val :> ?sum))))
  (println "args" args))
