package smartwater.api.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import smartwater.api.pi.domain.influx.InfluxService;
import smartwater.api.pi.domain.influx.NodeAttributes;

@Tag(name = "SmartLights")
@RequestMapping("/api/timeseries/v0.5/smartcampusmaua/SmartLights")
@RestController
@SecurityRequirement(name = "bearer-key")
public class SmartLightsController {

    @Autowired
    private InfluxService influxService;

    @Operation( operationId = "getAllSmartLights", summary = "Obter os dados de todas luzes inteligentes", description = "Obtém dados de todas as luzes inteligentes, conforme no intervalo de tempo e limite de dados fornecidos ou não.")
    @GetMapping
    public ResponseEntity<NodeAttributes> getAllSmartLights(
            @RequestParam(required = false, name = "interval", defaultValue = "60") String interval,
            @RequestParam(required = false, name = "limit", defaultValue = "10") String limit) {

        var smartLights = influxService.getAllNodes("SmartLight", interval, limit);
        return ResponseEntity.ok(smartLights);
    }

    @Operation( operationId = "getSpecificSmartLights", summary = "Obter luz inteligente por nome do dispositivo", description = "Obtém uma luz inteligente específica com base no nome do dispositivo fornecido.")
    @GetMapping("deviceName/{nodeName}")
    public ResponseEntity<NodeAttributes> getSmartLightByNodeName(
            @PathVariable("nodeName") String nodename) {
        var smartLights = influxService.getByDeviceName("SmartLight", nodename);
        return ResponseEntity.ok(smartLights);
    }

    @Operation( operationId = "getSpecificSmartLights", summary = "Obter luz inteligente por ID do dispositivo", description = "Obtém uma luz inteligente específica com base no ID do dispositivo fornecido." )
    @GetMapping("deviceId/{devEUI}")
    public ResponseEntity<NodeAttributes> getSmartLightByDevEUI(
            @PathVariable("devEUI") String devEUI) {
        var smartLights = influxService.getByDeviceId("SmartLight", devEUI);
        return ResponseEntity.ok(smartLights);
    }
}
