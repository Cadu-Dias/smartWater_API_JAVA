package smartwater.api.pi.services;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import smartwater.api.pi.models.NodeAttributes;
import smartwater.api.pi.utils.InfluxDbUtils;

@Service
public class InfluxService {
    String host = "https://us-east-1-1.aws.cloud2.influxdata.com";
    String token = "YDD_QnWNZ6qAvD4n28K5nSTrI3XDD5S9Z6gXK5eFAIZk2eTyMFaEcMY3XC-ArkcgXi6mEqE7I42ghiE4tSjCaQ==";
    String database = "smartcampusmaua";

    public NodeAttributes getSmartLights() {

        NodeAttributes nodeAttributes = new NodeAttributes();
     
        try (InfluxDBClient influxDBClient = InfluxDBClientFactory.create(host, token.toCharArray(), database)) {
            String flux = "from(bucket: \"smartcampusmaua\") |> range(start: -1h) |> filter(fn: (r) => r._measurement == \"SmartLight\") |> limit(n: 10)";
            QueryApi queryApi = influxDBClient.getQueryApi();
            List<FluxTable> tables = queryApi.query(flux);
            
            nodeAttributes = InfluxDbUtils.convertToNodeAttributes(tables);
            return nodeAttributes;
        } catch (Exception e) {
            e.printStackTrace();
            return new NodeAttributes(); 
        }
    }
}