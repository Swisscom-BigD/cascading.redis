package io.screen6.cascading.redis;

import java.io.IOException;
import cascading.tap.SinkTap;
import cascading.flow.FlowProcess;
import cascading.tuple.TupleEntryCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisSinkTap<Config> extends SinkTap<Config, Object> {

    private static final Logger logger = LoggerFactory.getLogger(RedisSinkTap.class);

    private static final String HOSTNAME = "localhost";
    private static final int PORT = 6379;
    private static final int DATABASE = 0;

    private String hostname;
    private int port;
    private int db;

    public RedisSinkTap(RedisBaseScheme scheme) {
        this(HOSTNAME, PORT, DATABASE, scheme);
    }

    public RedisSinkTap(String hostname, RedisBaseScheme scheme) {
        this(hostname, PORT, DATABASE, scheme);
    }

    public RedisSinkTap(String hostname, int port, RedisBaseScheme scheme) {
        this(hostname, port, DATABASE, scheme);
    }

    public RedisSinkTap(String hostname, int port, int db, RedisBaseScheme scheme) {
        super(scheme);
        logger.info("Creating tap at {}:{}@{}", hostname, port, db);
        this.hostname = hostname;
        this.port = port;
        this.db = db;
    }

    public TupleEntryCollector openForWrite(FlowProcess<Config> flowProcess, Object output) throws IOException {
        return new RedisSchemeCollector(flowProcess,
                                        (RedisBaseScheme) getScheme(),
                                        hostname,
                                        port,
                                        db);
    }

    @Override
    public boolean deleteResource(Config conf) throws IOException {
        return true;
    }

    @Override
    public boolean createResource(Config conf) throws IOException {
        return true;
    }

    @Override
    public boolean resourceExists(Config conf) throws IOException {
        return true;
    }

    @Override
    public long getModifiedTime(Config conf) throws IOException {
        // We are always assuming stale data in database
        return 0;
    }

    @Override
    public String getIdentifier() {
        return String.format("redis://%s:%s@%s", this.hostname, this.port, this.db);
    }

}
