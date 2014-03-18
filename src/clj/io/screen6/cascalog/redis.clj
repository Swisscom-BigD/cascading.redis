(ns io.screen6.cascalog.redis
  (:import [io.screen6.cascading.redis RedisScheme RedisSinkTap]
           [cascading.tuple Fields]))

(defn redis-tap
  "Create a Cascading Redis tap"
  [scheme host port db]
  (RedisSinkTap. (str host)
                 (int port)
                 (int db)
                 scheme))

(defn redis-scheme
  "Create Cascading Redis scheme"
  [key-fields value-fields command]
  (RedisScheme. (Fields. (into-array String key-fields))
                (Fields. (into-array String value-fields))
                command))
