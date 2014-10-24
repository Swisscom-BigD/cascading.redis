package io.screen6.cascading.redis;

import cascading.flow.FlowProcess;
import cascading.tuple.TupleEntrySchemeCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

public class RedisSchemeCollector<Config, Value> extends TupleEntrySchemeCollector<Config, TupleEntrySchemeCollector> {

    private static final Logger logger = LoggerFactory.getLogger(RedisSchemeCollector.class);

    private JedisPool pool;

    public RedisSchemeCollector(FlowProcess<Config> flowProcess, RedisBaseScheme scheme, String hostname, int port, int db) {
        super(flowProcess, scheme);
        logger.debug("Connecting to {}:{}@{}", hostname, port, db);
        this.pool = new JedisPool(new JedisPoolConfig(), hostname, port);
        setOutput(this);
        logger.info("Connected to {}:{}@{}", hostname, port, db);
    }

    public void collect(String command, String key, Value value) {
        Jedis client = pool.getResource();
        try {
            if (command.equals("set")) {
                this.set(client, key, value);
            } else if (command.equals("lpush")) {
                this.lpush(client, key, value);
            }
        } catch (JedisConnectionException exc) {
            if (client != null) {
                this.pool.returnBrokenResource(client);
            }
        } finally {
            if (client != null) {
                this.pool.returnResource(client);
            }
        }
    }

    private void set(Jedis client, String key, Value value) {
        client.set(key, (String) value);
    }

    private void lpush(Jedis client, String key, Value value) {
        try {
            client.lpush(key, (String) value);
        } catch (JedisDataException exc) {
            client.del(key);
            client.lpush(key, (String) value);
        }
    }

    @Override
    public void close() {
        this.pool.destroy();
        logger.info("Closing collector");
    }

}
