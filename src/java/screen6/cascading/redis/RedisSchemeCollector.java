package screen6.cascading.redis;

import java.io.IOException;

import cascading.flow.FlowProcess;
import cascading.tap.TapException;
import cascading.tuple.TupleEntrySchemeCollector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisSchemeCollector<Config, Value> extends TupleEntrySchemeCollector<Config, TupleEntrySchemeCollector> {

    private static final Logger logger = LoggerFactory.getLogger(RedisSchemeCollector.class);

    private Jedis client;

    public RedisSchemeCollector(FlowProcess<Config> flowProcess, RedisBaseScheme scheme, String hostname, int port, int db) {
        super(flowProcess, scheme);
        JedisPool pool = new JedisPool(new JedisPoolConfig(), hostname, port);
        client = pool.getResource();
    }

    public void collect(String key, Value value) {
        client.set(key, (String) value);
    }

    @Override
    public void close() {
        logger.debug("Closing collector");
    }

}
