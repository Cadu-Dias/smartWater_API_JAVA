package smartwater.api.pi.core.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxTable;

import smartwater.api.pi.core.models.nodeattributes.NodeAttributes;
import smartwater.api.pi.utils.InfluxDbUtils;

@Service
public class InfluxService {

    @Value("${api.influxdb.config.url}")
    private String host;

    @Value("${api.influxdb.config.token}")
    private String token;

    @Value("${api.influxdb.config.database}")
    String database;

    public NodeAttributes getAllNodes(String tableName, Optional<String> intervalSet, Optional<String> limitSet) {

        Integer limit = 10;
        if(limitSet.isPresent()) limit = Integer.parseInt(limitSet.get());

        Integer interval = 60;
        if(intervalSet.isPresent()) interval = Integer.parseInt(intervalSet.get());

        NodeAttributes nodeAttributes = new NodeAttributes();

        try (InfluxDBClient influxDBClient = InfluxDBClientFactory.create(host, token.toCharArray(), database)) {
            String flux = String.format("from(bucket: \"smartcampusmaua\") |> range(start: -%sm) |> filter(fn: (r) => r._measurement == \"%s\") |> limit(n: %s)", interval, tableName, limit);
            QueryApi queryApi = influxDBClient.getQueryApi();
            List<FluxTable> tables = queryApi.query(flux);
            
            nodeAttributes = InfluxDbUtils.convertToNodeAttributes(tables);
            return nodeAttributes;
        } catch (Exception e) {
            e.printStackTrace();
            return new NodeAttributes(); 
        }
    }

    public NodeAttributes getByDeviceName(String tableName, String deviceName) {
        NodeAttributes nodeAttributes = new NodeAttributes();

        try (InfluxDBClient influxDBClient = InfluxDBClientFactory.create(host, token.toCharArray(), database)) {
            String flux = String.format("from(bucket: \"smartcampusmaua\") |> range(start: -60m) |> filter(fn: (r) => r._measurement == \"%s\" and r.nodeName == \"%s\")", tableName, deviceName);
            QueryApi queryApi = influxDBClient.getQueryApi();
            List<FluxTable> tables = queryApi.query(flux);
            
            nodeAttributes = InfluxDbUtils.convertToNodeAttributes(tables);
            return nodeAttributes;
        } catch (Exception e) {
            e.printStackTrace();
            return new NodeAttributes(); 
        }
    }

    public NodeAttributes getByDeviceId(String tableName, String deviceId) {
        NodeAttributes nodeAttributes = new NodeAttributes();

        try (InfluxDBClient influxDBClient = InfluxDBClientFactory.create(host, token.toCharArray(), database)) {
            String flux = String.format("from(bucket: \"smartcampusmaua\") |> range(start: -60m) |> filter(fn: (r) => r._measurement == \"%s\" and r.devEUI == \"%s\")", tableName, deviceId);
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