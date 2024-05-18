package smartwater.api.pi.controller;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import smartwater.api.pi.models.NodeAttributes;
import smartwater.api.pi.services.InfluxService;

@RestController
public class InfluxController {
    
    @Autowired
    private InfluxService influxService;

    @GetMapping("/teste")
    public NodeAttributes teste() {
        var teste = influxService.getSmartLights();
        return teste;
    }
}
