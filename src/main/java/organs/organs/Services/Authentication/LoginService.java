package organs.organs.Services.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import organs.organs.Models.UserTypes.Users;
import organs.organs.Repositories.UserTypes.UsersRepository;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class LoginService {

    private final UsersRepository usersRepository;

    public static String secretKeyString;

    public static Integer USER_ID;
    public static String EMAIL;
    public static String ROLE;
    public static Users USER;

    @Value("${jwt.secretKey}")
    public void setSecretKeyString(String secretKeyString) {

        LoginService.secretKeyString = secretKeyString;
    }

    public static SecretKey getSecretKey() {

        byte[] secretBytes = secretKeyString.getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(secretBytes);
    }

    public String login(String email, String password) {

        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Email Not Found"));

        if (Objects.equals(user.getPassword(), password)) {

            Claims claims = Jwts.claims();

            claims.put("id", user.getId());
            claims.put("email", user.getEmail());
            claims.put("role", user.getRole());
            claims.put("user", user);

            // Set expiration time to 1 week
            Date expiration = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);

            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(expiration)
                    .signWith(getSecretKey())
                    .compact();
        }
        else {

            throw new IllegalArgumentException("Password Didn't Match!");
        }
    }

    public static Map<String, Object> parseToken(HttpServletRequest request) throws Exception {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.toLowerCase().startsWith("bearer")) {

            String token = authorizationHeader.substring("Bearer".length()).trim();

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();

            if (expiration != null && expiration.before(new Date())) {

                throw new Exception("Token is expired");
            }

            Map<String, Object> result = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();

            LinkedHashMap<String, Object> userMap = (LinkedHashMap<String, Object>) claims.get("user");

            Users user = mapper.convertValue(userMap, Users.class);

            result.put("id", claims.get("id"));
            result.put("email", claims.get("email"));
            result.put("role", claims.get("role"));
            result.put("user", user);

            USER_ID = (Integer) claims.get("id");
            EMAIL = (String) claims.get("email");
            ROLE = (String) claims.get("role");
            USER = user;

            return result;
        }

        return null;
    }

    public static boolean isAuthorized(HttpServletRequest request, List<String> requiredRoles) throws Exception {

        Map<String, Object> tokenData = parseToken(request);

        if (tokenData != null) {

            String role = (String) tokenData.get("role");

            return !requiredRoles.contains(role);
        }

        return true;
    }
}