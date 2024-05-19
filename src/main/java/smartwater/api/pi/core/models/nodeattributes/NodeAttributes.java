package smartwater.api.pi.core.models.nodeattributes;

import java.util.List;
import java.util.Map;

public class NodeAttributes {
    private Map<String, Map<String, List<FieldData>>> attributes;

    public Map<String, Map<String, List<FieldData>>> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Map<String, List<FieldData>>> attributes) {
        this.attributes = attributes;
    }

    public static class FieldData {
        private Object fieldValue;
        private String timestamp;
        private String start;
        private String stop;

        // Getters and setters

        public Object getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(Object fieldValue) {
            this.fieldValue = fieldValue;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getStop() {
            return stop;
        }

        public void setStop(String stop) {
            this.stop = stop;
        }
    }
}