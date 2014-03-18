package screen6.cascading.redis;

import java.io.IOException;
import cascading.flow.FlowProcess;
import cascading.scheme.Scheme;
import cascading.scheme.SinkCall;
import cascading.scheme.SourceCall;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import cascading.tuple.TupleEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisScheme<Config> extends RedisBaseScheme<Config, TupleEntry, String> {

    private static final Logger logger = LoggerFactory.getLogger(RedisScheme.class);
    public static final String DEFAULT_KEY_DELIMITER = ":";
    public static final String DEFAULT_REDIS_COMMAND = "set";

    private Fields keyFields;
    private Fields valueFields;
    private String keyDelimiter = DEFAULT_KEY_DELIMITER;
    private String redisCommand = DEFAULT_REDIS_COMMAND;

    public RedisScheme(Fields keyFields, Fields valueFields) {
        super(Fields.merge(keyFields, valueFields));
        this.keyFields = keyFields;
        this.valueFields = valueFields;

        logger.info("Key fields: {}, value fields {}", keyFields, valueFields);
    }

    @Override
    protected TupleEntry getIntermediate(TupleEntry entry) {
        logger.info("Getting intermediate value of {}", entry);
        return entry;
    }

    @Override
    protected String getValue(TupleEntry entry) {
        logger.info("Getting value of {} with {}", entry, valueFields);
        return entry.selectTuple(this.valueFields).toString(this.keyDelimiter, false);
    }

    @Override
    protected String getKey(TupleEntry entry) {
        logger.info("Getting key of {} with {}", entry, keyFields);
        return entry.selectTuple(this.keyFields).toString(this.keyDelimiter, false);
    }
}
