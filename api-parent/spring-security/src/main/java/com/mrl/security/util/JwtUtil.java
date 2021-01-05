package com.mrl.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @ClassName: JwtUtil
 * @Description
 * @Author Mr.L
 * @Date 2020/12/1 15:03
 * @Version 1.0
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Claims extraAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private <T> T extraClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extraAllClaims(token);
        return resolver.apply(claims);
    }

    public String extraUserName(String token) {
        return extraClaim(token, Claims::getSubject);
    }

    public Date extraExpiration(String token) {
        return extraClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extraExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extraUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(4);
        //claims.put("auth", userDetails.getAuthorities());
        return generateToken(claims, userDetails.getUsername());
    }
}
