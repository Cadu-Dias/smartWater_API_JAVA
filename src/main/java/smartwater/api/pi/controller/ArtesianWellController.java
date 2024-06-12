package smartwater.api.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import smartwater.api.pi.domain.influx.InfluxService;
import smartwater.api.pi.domain.influx.NodeAttributes;

@Tag(name = "ArtesianWell")
@RequestMapping("/api/timeseries/v0.5/smartcampusmaua/ArtesianWell")
@RestController
@SecurityRequirement(name = "bearer-key")
public class ArtesianWellController {

    @Autowired
    private InfluxService influxService;

    @Operation( summary = "Obter dados emitidos pelo sensor do ArtesianWell", description = "Obtém dados do sensor inteligente instalado para o ArtesianWell, conforme no intervalo de tempo e limite de dados fornecidos ou não.")
    @GetMapping
    public ResponseEntity<NodeAttributes> getArtesianWell(
            @RequestParam(required = false, name = "interval", defaultValue = "60") String interval,
            @RequestParam(required = false, name = "limit", defaultValue = "10") String limit) {

        var smartLights = influxService.getAllNodes("ArtesianWell", interval, limit);
        return ResponseEntity.ok(smartLights);
    }
}
