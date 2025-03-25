package br.com.solipy.loja.configs.security;

import br.com.solipy.loja.configs.HashidService;
import br.com.solipy.loja.models.Role;
import br.com.solipy.loja.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JwtService {

    private static final String SECRET_KEY = "6B58703273357638792F423F4528482B4B6250655368566D597133743677397A24432646294A404E635166546A576E5A7234753778214125442A472D4B615064";
    private final HashidService hashidService;

    public String getUserToken(String jwtToken) {
        return getClaim(jwtToken, Claims::getSubject);
    }

    public String getUserKey(String jwtToken) {
        final Claims claims = getAllClaims(jwtToken);
        return claims.get("userKey").toString();
    }

    public <T> T getClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            User user
    ) {
        String roles = user.getRoles().stream().map(Role::getRole).collect(Collectors.joining(","));
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getToken())
                .claim("userKey", hashidService.get(32).encode(user.getId()))
                .claim("name", user.getName())
                .claim("roles", roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000000 * 60 * 24))
                .signWith(getSigngInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean isTokenValid(String jwtToken, User user) {
        final Long userId = hashidService.toLongUserKey(getUserKey(jwtToken));
        final String userToken = getUserToken(jwtToken);
        return (userId.equals(user.getId())) && (userToken.equals(user.getToken())) && !isTokenExpired(jwtToken);
    }

    public Claims getAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigngInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }
    private Boolean isTokenExpired(String jwtToken) {
        return getExpiration(jwtToken).before(new Date());
    }

    private Date getExpiration(String jwtToken) {
        return getClaim(jwtToken, Claims::getExpiration);
    }
    private Key getSigngInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
