package screen6.cascading.redis;

import java.io.IOException;
import cascading.flow.FlowProcess;
import cascading.scheme.Scheme;
import cascading.scheme.SinkCall;
import cascading.scheme.SourceCall;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import cascading.tuple.TupleEntry;

public class RedisScheme<Config, Int, Value> extends Scheme<Config, Void, RedisSchemeCollector, Object[], Void> {

    public RedisScheme(Fields sinkFields) {
        setSinkFields(sinkFields);
    }

    @Override
    public boolean isSource() {
        return false;
    }


}
