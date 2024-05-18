package smartwater.api.pi.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxTable;

import smartwater.api.pi.domain.nodeattributes.NodeAttributes;
import smartwater.api.pi.domain.utils.InfluxDbUtils;

@Service
public class InfluxService {
    String host = "https://us-east-1-1.aws.cloud2.influxdata.com";
    String token = "YDD_QnWNZ6qAvD4n28K5nSTrI3XDD5S9Z6gXK5eFAIZk2eTyMFaEcMY3XC-ArkcgXi6mEqE7I42ghiE4tSjCaQ==";
    String database = "smartcampusmaua";

    public NodeAttributes getAllNodes(String tableName, Optional<String> intervalSet, Optional<String> limitSet) {

        Integer limit = 10;
        if(limitSet.isPresent()) limit = Integer.parseInt(limitSet.get());

        Integer interval = 60;
        if(intervalSet.isPresent()) interval = Integer.parseInt(intervalSet.get());
        
        NodeAttributes nodeAttributes = new NodeAttributes();

        try (InfluxDBClient influxDBClient = InfluxDBClientFactory.create(host, token.toCharArray(), database)) {
            String flux = "from(bucket: \"smartcampusmaua\") |> range(start: -" + interval + "m) |> filter(fn: (r) => r._measurement == \"SmartLight\") |> limit(n: " + limit +")";
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