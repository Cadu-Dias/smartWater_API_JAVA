package smartwater.api.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import smartwater.api.pi.domain.users.User;
import smartwater.api.pi.domain.users.UserAuthenticationData;
import smartwater.api.pi.infra.security.TokenModel;
import smartwater.api.pi.infra.security.TokenService;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/api/timeseries/v0.5/smartcampusmaua/login")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @SuppressWarnings("rawtypes")
    @Operation( summary = "Validate the given user", description = "Verify if the sended user is present in the database")
    @PostMapping
    public ResponseEntity authenticateUser(@RequestBody UserAuthenticationData user) {
        try {
            var token = new UsernamePasswordAuthenticationToken(user.email(), user.password());
            var authentication = manager.authenticate(token);
            var tokenJWT = tokenService.tokenGeneration((User) authentication.getPrincipal());
            return ResponseEntity.ok(new TokenModel(tokenJWT));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
