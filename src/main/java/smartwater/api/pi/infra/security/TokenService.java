package smartwater.api.pi.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import smartwater.api.pi.domain.users.User;

@Service
public class TokenService {
    
    @Value("${api.security.token.secret}")
    private String secret;

    public String tokenGeneration(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                .withIssuer("SmartWater API")
                .withSubject(user.getEmail())
                .withExpiresAt(expirationDate())
                .withClaim("user_role", user.getUser_role())
                .sign(algorithm);
        } catch (JWTCreationException error) {
            throw new RuntimeException("Error while doing the Token Generation", error);
        }
    }

    public String getSubject(String JWTToken) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer("SmartWater API")
                .build()
                .verify(JWTToken)
                .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired JWT Token!");
        }
    }

    public Instant expirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
