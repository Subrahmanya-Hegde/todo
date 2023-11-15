package com.hegde.todo.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {

    //TODO : Move to config.
    private String SECRET = "7526C8EAD36B463C498C23A71B565SNVDJAR232F2F2FQEFJQ13";

    public String createToken(String email){
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_00);

        return Jwts.builder().setSubject(email).setIssuedAt(now)
                .setExpiration(validity).signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateUserDetails(String token, UserDetails userDetails){
        final String userName = extractUserEmail(token);
        return (userName.equals(userDetails.getUsername()));
    }

    public String extractUserEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignKey()).build().parseSignedClaims(token).getPayload();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
