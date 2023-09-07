package com.learning.configuration;

import com.learning.exception.CantParseJwtException;
import com.learning.model.users.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${oauth.jwt.secret}")
    private String SECRET_KEY;
    private static final Long TOKEN_EXPIRATION_MIN = 300L;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("permissions", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return generateToken(claims, ((AppUser) userDetails).getEmail());
    }

    public String getUsernameFromToken(String jwtToken) throws CantParseJwtException {
        return getClaimFromToken(jwtToken, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws CantParseJwtException {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) throws CantParseJwtException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException e) {
            throw new CantParseJwtException("Invalid token");
        }
    }

    public boolean validateToken(String jwtToken) throws CantParseJwtException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(jwtToken);
            return !isTokenExpired(jwtToken);
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException e) {
            throw new CantParseJwtException("Invalid token");
        }
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(String jwtToken) throws CantParseJwtException {
        List<String> permissions = getClaimFromToken(jwtToken, claims -> claims.get("permissions", List.class));
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public java.util.Date getExpirationDateFromToken(String token) throws CantParseJwtException {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private String generateToken(Map<String, Object> claims, String email) {
        Instant instant = Instant.now();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(instant.plus(TOKEN_EXPIRATION_MIN, ChronoUnit.MINUTES)))
                .signWith(getKey())
                .compact();
    }

    private Boolean isTokenExpired(String token) throws CantParseJwtException {
        Instant instant = Instant.now();
        final java.util.Date expiration = getExpirationDateFromToken(token);
        return expiration.before(Date.from(instant));
    }

    private Key getKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName(); // Retrieves the username from the authentication object
        }
        return null;
    }
}