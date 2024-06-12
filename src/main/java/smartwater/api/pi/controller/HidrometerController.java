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

@Tag(name = "Hidrometers")
@RequestMapping("/api/timeseries/v0.5/smartcampusmaua/Hidrometers")
@RestController
@SecurityRequirement(name = "bearer-key")
public class HidrometerController {

    @Autowired
    private InfluxService influxService;

    @Operation( summary = "Obter dados de todas os Hidrometros", description = "Obtém dados emitidos pelos sensores inteligentes que leem as informações dos Hidrometros, conforme no intervalo de tempo e limite de dados fornecidos ou não.")
    @GetMapping
    public ResponseEntity<NodeAttributes> getAllWaterTanks(
            @RequestParam(required = false, name = "interval", defaultValue = "60") String interval,
            @RequestParam(required = false, name = "limit", defaultValue = "10") String limit) {

        var waterTanks = influxService.getAllNodes("Hidrometer", interval, limit);
        return ResponseEntity.ok(waterTanks);
    }

    @Operation( summary = "Obtem dado de uma caixa D'água específica", description = "Obtém dado emitido de um sensor inteligente contido na Caixa D'água específica, definida com base no nome do dispositivo fornecido.")
    @GetMapping("deviceName/{nodeName}")
    public ResponseEntity<NodeAttributes> getWaterTankByNodeName(
            @PathVariable("nodeName") String nodename) {
        var waterTank = influxService.getByDeviceName("Hidrometer", nodename);
        return ResponseEntity.ok(waterTank);
    }

    @Operation( summary = "Obter caixa D'água com base no ID do dispositivo sensorial", description = "Obtém dado emitido de um sensor inteligente contido na Caixa D'água específica, definida com base no ID sensor específico." )
    @GetMapping("deviceId/{devEUI}")
    public ResponseEntity<NodeAttributes> getWaterTankByDevEUI(
            @PathVariable("devEUI") String devEUI) {
        var smartLights = influxService.getByDeviceId("Hidrometer", devEUI);
        return ResponseEntity.ok(smartLights);
    }
}
