package cybersoft.java20.dev3lopers.gear3sproject.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtUtils {
    // Cài đặt thời hạn cho token (ms)
    private long expiryTime = 30*60*1000;   // 30 phút

    // Lấy privateKey từ file yml
    @Value("${jwt.privateKey}")
    private String privateKey;

    public String generateToken(String userName){
        Date date = new Date();
        long currentDate = date.getTime() + expiryTime;
        Date expiryDate = new Date(currentDate);

        SecretKey key = Keys.hmacShaKeyFor(privateKey.getBytes());
        String jwt = Jwts.builder()
                    .setSubject(userName)
                    .setIssuedAt(date)
                    .setExpiration(expiryDate)
                    .signWith(key)
                    .compact();

        return jwt;
    }

    public boolean verifyToken(String token){
        try {
            SecretKey key = Keys.hmacShaKeyFor(privateKey.getBytes());
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: "+ e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: "+ e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: "+ e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: "+ e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: "+ e.getMessage());
        }

        return false;
    }

    public String getSubjectFromToken(String token){
        try {
            SecretKey key = Keys.hmacShaKeyFor(privateKey.getBytes());
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e){
            System.out.println("Error has occurred when get subject from token | "+e.getMessage());
            return null;
        }
    }


}
