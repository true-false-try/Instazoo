package com.example.instazoo.security.jwt;

import com.example.instazoo.entity.User;
import com.example.instazoo.security.SecurityConstant;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTTokenProvider {

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expireTime = new Date(now.getTime() + SecurityConstant.EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        // User's fields which included in JWT body as claim
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("id", userId);
        claimMap.put("username", user.getEmail());
        claimMap.put("firstname", user.getName());
        claimMap.put("lastname", user.getLastname());

        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claimMap)
                .setIssuedAt(now)
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, SecurityConstant.SECRET)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstant.SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException |
                MalformedJwtException |
                ExpiredJwtException |
                UnsupportedJwtException |
                IllegalArgumentException ex) {
            log.error(ex.getMessage());
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        Claims claims  = Jwts.parser()
                .setSigningKey(SecurityConstant.SECRET)
                .parseClaimsJws(token)
                .getBody();
        String id = String.valueOf(claims.get("id"));
        return Long.parseLong(id);
    }

}
