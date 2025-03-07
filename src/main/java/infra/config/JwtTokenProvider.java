package infra.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import models.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class JwtTokenProvider {
    private final String secretKey = "palavra_oculta_xablau";
    private long jwtExpirationDate = 3600000; //1 hora


    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("roles", authorities)
                .claim("department", setDepartment(authorities))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return token;
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    private String setDepartment(String roles) {
        return roles.contains("ADMIN") ? "IT" : "FINANCE";
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            // Analisa e valida o token de atualização
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(refreshToken);
            return true; // Token válido
        } catch (Exception e) {
            // Token inválido ou expirado
            return false;
        }
    }

    public String refreshAccessToken(String refreshToken) {
        return refreshToken;
    }
    public String generateTokenFromUser(Authentication authentication) {
        return generateToken(authentication);
    }
    public String getUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }
}
