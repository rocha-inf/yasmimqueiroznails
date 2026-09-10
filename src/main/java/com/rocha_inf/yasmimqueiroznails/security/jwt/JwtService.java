package com.rocha_inf.yasmimqueiroznails.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rocha_inf.yasmimqueiroznails.security.config.JwtPropertiesConfig;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class JwtService {

    private final JwtPropertiesConfig jwtPropertiesConfig;
    private final Algorithm algorithm;

    public JwtService(JwtPropertiesConfig jwtPropertiesConfig) {
        this.jwtPropertiesConfig = jwtPropertiesConfig;
        this.algorithm = Algorithm.HMAC256(jwtPropertiesConfig.secret());
    }

    public String generateToken(UUID id){
        return JWT.create()
                .withSubject(id.toString())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(600))
                .withIssuer(jwtPropertiesConfig.issuer())
                .sign(algorithm);
    }

    public Optional<UUID> validateToken(String token){
        try{

            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer(jwtPropertiesConfig.issuer())
                    .build()
                    .verify(token);

            return Optional.of(UUID.fromString(decodedJWT.getSubject()));

        } catch (JWTVerificationException e){
            return Optional.empty();
        }
    }
}