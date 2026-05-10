package teamhub.teamhub_backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Em produção, isso deve vir das configurações (application.properties/yml)
    private static final String SECRET_KEY_STRING = "UmaChaveSuperSecretaMuitoLongaParaOJwtFuncionarCorretamente12345!";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    // 1 hora de validade
    private static final long EXPIRATION_TIME = 3600000;

    public String generateToken(String username, String accessLevel) {
        return Jwts.builder()
                .subject(username)
                .claim("access_level", accessLevel)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
}
