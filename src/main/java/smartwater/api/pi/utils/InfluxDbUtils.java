package smartwater.api.pi.utils;


import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import smartwater.api.pi.models.NodeAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfluxDbUtils {
    
    public static NodeAttributes convertToNodeAttributes(List<FluxTable> tables) {
        NodeAttributes nodeAttributes = new smartwater.api.pi.models.NodeAttributes();
        Map<String, Map<String, List<NodeAttributes.FieldData>>> attributes = new HashMap<>();

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                String nodeName = (String) fluxRecord.getValueByKey("nodeName");
                String field = (String) fluxRecord.getValueByKey("_field");

                NodeAttributes.FieldData fieldData = new NodeAttributes.FieldData();
                fieldData.setFieldValue(fluxRecord.getValue());
                fieldData.setTimestamp(fluxRecord.getTime().toString());
                fieldData.setStart((String) fluxRecord.getValueByKey("_start"));
                fieldData.setStop((String) fluxRecord.getValueByKey("_stop"));

                attributes
                        .computeIfAbsent(nodeName, k -> new HashMap<>())
                        .computeIfAbsent(field, k -> new ArrayList<>())
                        .add(fieldData);
            }
        }

        nodeAttributes.setAttributes(attributes);
        return nodeAttributes;
    }
}
