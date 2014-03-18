(defproject screen6.cascading/redis "0.1.0"
  :description "Cascading tap for Redis"
  :url "http://opensource.screen6.io/"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :java-source-paths ["src/java"]
  :source-paths ["src/clj"]
  :main screen6.cascading.redis.core
  :repositories [["conjars" "http://conjars.org/repo/"]]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [redis.clients/jedis "2.4.2"]
                 [cascading/cascading-core "2.5.2"]
                 [org.slf4j/slf4j-api "1.7.6"]
                 [org.clojure/tools.logging "0.2.6"]
                 [ch.qos.logback/logback-classic "1.1.1"]
                 [cascalog "2.0.1-SNAPSHOT"
                  :exclusions [log4j/slf4j-api
                               org.slf4j/slf4j-log4j12]]
                 [org.apache.hadoop/hadoop-core "1.2.1"]])
