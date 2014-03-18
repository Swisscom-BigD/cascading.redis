(defproject io.screen6.cascading/redis "0.1.1-SNAPSHOT"
  :description "Cascading tap for Redis"
  :url "http://opensource.screen6.io/"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :java-source-paths ["src/java"]
  :source-paths ["src/clj"]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.5.1"]
                                  [cascalog "2.0.1-SNAPSHOT"
                                   :exclusions [log4j/slf4j-api
                                                org.slf4j/slf4j-log4j12]]
                                  [org.apache.hadoop/hadoop-core "1.2.1"]
                                  [com.taoensso/carmine "2.4.5"] ]
                   :plugins [[lein-midje "3.1.3"]]}}
  :repositories [["conjars" "http://conjars.org/repo/"]]
  :dependencies [[redis.clients/jedis "2.4.2"]
                 [cascading/cascading-core "2.5.2"]
                 [org.slf4j/slf4j-api "1.7.6"]]

  :scm {:url "git@github.com:screen6/cascading.redis.git"}
  :pom-additions [:developers [:developer
                               [:name "Andrii V. Mishkovskyi"]
                               [:url "https://mishkovskyi.net/"]
                               [:email "andrii@screen6.io"]
                               [:timezone "+1"]]])
