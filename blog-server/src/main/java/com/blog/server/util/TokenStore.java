package com.blog.server.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.blog.common.entity.User;
import com.blog.server.component.context.Token;
import com.blog.server.config.AuthProperties;
import com.blog.server.constance.TokenConstance;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class TokenStore {

    private final AuthProperties authProperties;

    public TokenStore(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }

    private static class DecodedToken implements Token {

        private final Long userid;

        private DecodedToken(Long userid) {
            this.userid = userid;
        }

        @Override
        public Long getUserid() {
            return userid;
        }
    }

    public Token extract(String token) {

        DecodedJWT tmp = verifyToken(token);

        Claim claim = tmp.getClaim(Token.Claim.UserId.getKey());
        Long userId = claim.asLong();

        return new DecodedToken(userId);
    }

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(authProperties.getSecret());
            Date date = new Date(new Date().getTime() + authProperties.getExp());
            return JWT.create()
                    .withIssuer(authProperties.getIssuer())
                    .withClaim(Token.Claim.UserId.getKey(), user.getId())
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (JWTCreationException e){
            e.printStackTrace();
            throw new IllegalArgumentException("生成token失败");
        }
    }


    public Map<String, Claim> getTokenClaim(String token) {
        DecodedJWT decodedJwt = verifyToken(token);
        return decodedJwt.getClaims();
    }

    public DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(authProperties.getSecret()); //use more secure key
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(authProperties.getIssuer())
                .build(); //Reusable verifier instance
        return verifier.verify(token);
    }


}
