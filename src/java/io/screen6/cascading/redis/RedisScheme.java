package io.screen6.cascading.redis;

import cascading.tuple.Fields;
import cascading.tuple.TupleEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisScheme<Config> extends RedisBaseScheme<Config, TupleEntry, String> {

    private static final Logger logger = LoggerFactory.getLogger(RedisScheme.class);
    public static final String DEFAULT_KEY_DELIMITER = ":";
    public static final String DEFAULT_VALUE_DELIMITER = ":";
    public static final String DEFAULT_COMMAND = "set";

    private Fields keyFields;
    private Fields valueFields;
    private String keyDelimiter = DEFAULT_KEY_DELIMITER;
    private String valueDelimiter = DEFAULT_VALUE_DELIMITER;
    private String command = DEFAULT_COMMAND;

    public RedisScheme(Fields keyFields, Fields valueFields) {
        this(keyFields, valueFields, DEFAULT_COMMAND);
    }

    public RedisScheme(Fields keyFields, Fields valueFields, String command) {
        this(keyFields, valueFields, command, DEFAULT_KEY_DELIMITER, DEFAULT_VALUE_DELIMITER);
    }

    public RedisScheme(Fields keyFields, Fields valueFields, String command, String keyDelimiter, String valueDelimiter) {
        super(Fields.merge(keyFields, valueFields));
        this.keyFields = keyFields;
        this.valueFields = valueFields;
        this.command = command;
        this.keyDelimiter = keyDelimiter;
        this.valueDelimiter = valueDelimiter;
        logger.debug("Created {}", this);
    }

    public String toString() {
        return String.format("<RedisScheme %s; %s. Command: %s>", this.keyFields, this.valueFields, this.command);
    }

    @Override
    protected TupleEntry getIntermediate(TupleEntry entry) {
        logger.debug("Getting intermediate value of {}", entry);
        return entry;
    }

    @Override
    protected String getValue(TupleEntry entry) {
        logger.debug("Getting value of {} with {}", entry, valueFields);
        return entry.selectTuple(this.valueFields).toString(this.valueDelimiter, false);
    }

    @Override
    protected String getKey(TupleEntry entry) {
        logger.debug("Getting key of {} with {}", entry, keyFields);
        return entry.selectTuple(this.keyFields).toString(this.keyDelimiter, false);
    }

    @Override
    protected String getCommand() {
        return this.command;
    }
}
