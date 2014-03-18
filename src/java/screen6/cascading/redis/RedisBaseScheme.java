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


public abstract class RedisBaseScheme<Config, Intermediate, Value> extends Scheme<Config, Void, RedisSchemeCollector, Object[], Void> {

    private static final Logger logger = LoggerFactory.getLogger(RedisBaseScheme.class);

    RedisBaseScheme(Fields sinkFields) {
        setSinkFields(sinkFields);
    }

    @Override
    public boolean isSource() {
        return false;
    }

    @Override
    public void sourceConfInit(FlowProcess<Config> flowProcess, Tap<Config, Void, RedisSchemeCollector> output, Config conf) {}

    @Override
    public void sinkConfInit(FlowProcess<Config> flowProcess, Tap<Config, Void, RedisSchemeCollector> output, Config conf) {}

    @Override
    public boolean source( FlowProcess<Config> flowProcess, SourceCall<Object[], Void> voidSourceCall ) throws IOException {
        throw new IllegalStateException("Can't be used as a source");
    }

    protected abstract Intermediate getIntermediate(TupleEntry tupleEntry);
    protected abstract String getKey(Intermediate tupleEntry);
    protected abstract Value getValue(Intermediate tupleEntry);

    @Override
    public void sink(FlowProcess<Config> flowProcess, SinkCall<Void, RedisSchemeCollector> sinkCall) throws IOException {
        Intermediate entry = getIntermediate(sinkCall.getOutgoingEntry());
        String key = getKey(entry);
        Value value = getValue(entry);
        logger.info("what: {} {}", key, value);
        sinkCall.getOutput().collect(key, value);
    }

}
