package smartwater.api.pi.domain.influx;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeAttributes {
    private Map<String, Map<String, List<FieldData>>> attributes;

    @Getter
    @Setter
    public static class FieldData {
        private Object fieldValue;
        private String timestamp;
        private String start;
        private String stop;
    }
}