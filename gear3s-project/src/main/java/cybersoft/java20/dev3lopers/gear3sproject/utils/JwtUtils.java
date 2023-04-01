package cybersoft.java20.dev3lopers.gear3sproject.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class JwtUtils {
    // Cài đặt thời hạn cho token (ms)
    private long expiryTime = 30*60*1000;   // 30 phút

    // Lấy privateKey từ file yml
    @Value("${jwt.privateKey}")
    private String privateKey;

    public String generateToken(String userName){
        Calendar date = new GregorianCalendar();
        long currentDateMs = date.getTimeInMillis() + expiryTime;
        Date expiryDate = new Date(currentDateMs);

        SecretKey key = Keys.hmacShaKeyFor(privateKey.getBytes());
        String jwt = Jwts.builder()
                    .setSubject(userName)
                    .signWith(key)
                    .setExpiration(expiryDate)
                    .compact();

        return jwt;
    }

    public boolean verifyToken(String token){
        try {
            SecretKey key = Keys.hmacShaKeyFor(privateKey.getBytes());
            String userName = Jwts.parserBuilder()
                                .setSigningKey(key)
                                .build()
                                .parseClaimsJws(token)
                                .getBody()
                                .getSubject();

            return (!"".equals(userName) && userName != null);
        } catch (Exception e){
            System.out.println("Error has occurred when verify token | "+e.getMessage());
            return false;
        }
    }
}
