package screen6.cascading.redis;

import java.io.IOException;
import cascading.tap.SinkTap;
import cascading.flow.FlowProcess;

public class RedisSinkTap<Config> extends SinkTap<Config, Object> {

    String hostname = "localhost";
    int port = 6379;
    int db = 0;

    public RedisSinkTap(String hostname, int port, RedisBaseScheme scheme) {
        super(scheme);
        this.hostname = hostname;
        this.port = port;
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
        return "redis://" + this.hostname + ":" + this.port + "@" + this.db;
    }

}
