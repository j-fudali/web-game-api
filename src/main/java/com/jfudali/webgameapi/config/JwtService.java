package com.jfudali.webgameapi.config;

import com.jfudali.webgameapi.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService {
    private static final SecretKey key =  Jwts.SIG.HS256.key().build();
    public String extractUsername(String jwtToken){
        return extractClaim(jwtToken, Claims::getSubject);
    }
    public String generateToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        Optional<? extends GrantedAuthority>
                role =  user.getAuthorities().stream().findFirst();
        role.ifPresent(grantedAuthority -> extraClaims.put("role", grantedAuthority.toString()));
        return generateToken(extraClaims, user);
    }
    public String generateToken(Map<String, Object> extraClaims,
                                UserDetails userDetails){
        return Jwts.builder().claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(key)
                .compact();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResovler){
        final Claims claims = extractAllClaims(token);
        return claimsResovler.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}
