(ns io.screen6.cascading.redis-test
  (:require [midje.sweet :refer :all]
            [midje.cascalog :refer :all]
            [cascalog.api :refer [<- ?-]]
            [cascalog.logic.vars :refer [gen-non-nullable-vars]]
            [io.screen6.cascalog.redis :refer :all]
            [taoensso.carmine :as redis]))

(def host "localhost")
(def port 6379)
(def db 0)

(defn fetch
  [redis-command key]
  (redis/wcar
   {:pool nil :spec {:host host :port port :db db}}
   (case redis-command
     "set" (redis/get key)
     "lpush" (redis/lrange key 0 -1))))

(defn get-all
  [command keys]
  (zipmap keys (map (partial fetch command) keys)))

(defn command
  [command keys-count values-count data]
  (let [keys (gen-non-nullable-vars keys-count)
        values (gen-non-nullable-vars values-count)
        fields (vec (concat keys values))
        sink (redis-tap (redis-scheme keys values command) host port db)]
    (?- sink (<- fields (data :>> fields)))
    (get-all command (map #(clojure.string/join ":" (take keys-count %)) data))))

(def set-joined-key-and-value
  (partial command "set" 2 2))
(def set-joined-value
  (partial command "set" 1 2))
(def set-joined-key
  (partial command "set" 2 1))
(def set-simple
  (partial command "set" 1 1))

(def lpush-simple
  (partial command "lpush" 1 1))

(fact
 "Simple key/value based data works correctly"
 (let [data [["foo" 1]
             ["foo" 2]
             ["bar" 3]]]
   (set-simple data) => {"foo" "2" "bar" "3"})
 (let [data [["foo" "1"]
             ["foo" "2"]
             ["bar" "3"]]]
   (set-simple data) => {"foo" "2" "bar" "3"}))

(fact
 "Joining of keys is done correctly"
 (let [data [["foo" "foo" 1]
             ["foo" "bar" 2]
             ["bar" "foo" 3]
             ["foo" "foo" 4]]]
   (set-joined-key data) => {"foo:bar" "2" "bar:foo" "3" "foo:foo" "4"}))

(fact
 "Joining of values is done correctly"
 (let [data [["foo" 1 1]
             ["foo" 1 2]
             ["bar" 1 3]
             ["foo" 1 4]]]
   (set-joined-value data) => {"foo" "1:4" "bar" "1:3"}))

(fact
 "Joining of values is done correctly"
 (let [data [["foo" "foo" 1 1]
             ["foo" "bar" 1 2]
             ["bar" "foo" 1 3]
             ["foo" "foo" 1 4]]]
   (set-joined-key-and-value data) => {"foo:foo" "1:4" "foo:bar" "1:2" "bar:foo" "1:3"}))

(fact
 "LPUSH works correctly"
 (let [data [["foo" 1]
             ["foo" 2]
             ["bar" 3]]]
   (lpush-simple data) => {"bar" ["3"] "foo" ["2" "1"]}))
