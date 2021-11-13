package uz.pdp.task_2_5.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.task_2_5.entity.Role;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    private static final long expireTime = 1000*60*60*24;
    private static final String secretKey = "thisissecretkeynobadyknows";

    public String generateToken(String username, Set<Role> roles){
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("role", roles)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return token;
    }
}
