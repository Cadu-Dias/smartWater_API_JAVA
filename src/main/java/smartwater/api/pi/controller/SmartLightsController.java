package smartwater.api.pi.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import smartwater.api.pi.domain.nodeattributes.NodeAttributes;
import smartwater.api.pi.services.InfluxService;

@RequestMapping("/api/timeseries/v0.5/smartcampusmaua/SmartLights")
@RestController
public class SmartLightsController {
    
    @Autowired
    private InfluxService influxService;

    @GetMapping
    public ResponseEntity<NodeAttributes> getAllSmartLights(
        @RequestParam(required = false, name = "interval") String interval,
        @RequestParam(required = false, name = "limit") String limit
    ) {

        Optional<String> intervalSet;
        Optional<String> limitSet;

        intervalSet = interval == null? Optional.empty() : Optional.of(interval);
        limitSet = limit == null? Optional.empty() : Optional.of(limit);

        var smartLights = influxService.getAllNodes("SmartLight", intervalSet, limitSet);
        return ResponseEntity.ok(smartLights);
    }
}
