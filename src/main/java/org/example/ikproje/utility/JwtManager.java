package org.example.ikproje.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.ikproje.entity.enums.EUserRole;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class JwtManager {


    private String secretKey="qAz1+!rUt7w$eS5#";


    private String issuer="IKProje";    

    private final Long ExDate = 1000L * 60 * 60; //60 dk sonra iptal olsun.

    Algorithm algorithm = Algorithm.HMAC512(secretKey);
    Date createdDate = new Date(System.currentTimeMillis());
    Date expirationDate = new Date(System.currentTimeMillis() + ExDate);

    // Token oluştururken userRole'ü de aldım.
    // FrontEnd'de token'i decode edip içerisinden userRole'ü alacağım.
    public String createUserToken(Long authId, EUserRole userRole) {


        String token = JWT.create()
                .withAudience()
                .withIssuer(issuer)
                .withIssuedAt(createdDate)
                .withExpiresAt(expirationDate)
                .withClaim("authId", authId)
                .withClaim("key", "IKProje")
                .withClaim("role",userRole.name())
                .sign(algorithm);
        return token;
    }

    public String createAdminToken(Long adminId, String email) {

        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(createdDate)
                .withExpiresAt(expirationDate)
                .withClaim("adminId",adminId)
                .withClaim("email",email)
                .withClaim("key","IKProje")
                .withClaim("role","ADMIN")
                .sign(algorithm);
    }

    public String createResetPasswordToken(Long authId,String email) {
        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(createdDate)
                .withExpiresAt(expirationDate)
                .withClaim("authId",authId)
                .withClaim("RESET_PASSWORD_EMAIL_KEY",email)
                .withClaim("role","USER")
                .sign(algorithm);

    }

    public Optional<Long> validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if(Objects.isNull(decodedJWT))
                return Optional.empty();

            String role = decodedJWT.getClaim("role").asString();
            if(role.equals("ADMIN")){
                return Optional.of(decodedJWT.getClaim("adminId").asLong());
            }
            else{
                return Optional.of(decodedJWT.getClaim("authId").asLong());
            }

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
    
    
}