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
                 [redis.clients/jedis "2.2.1"]
                 [cascading/cascading-core "2.2.0"]
                 [org.slf4j/slf4j-api "1.7.5"]
                 [org.slf4j/slf4j-log4j12 "1.7.5"]
                 [log4j/log4j "1.2.17"]])
