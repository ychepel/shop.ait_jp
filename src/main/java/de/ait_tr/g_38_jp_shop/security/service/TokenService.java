package de.ait_tr.g_38_jp_shop.security.service;

import de.ait_tr.g_38_jp_shop.domain.entity.Role;
import de.ait_tr.g_38_jp_shop.domain.entity.User;
import de.ait_tr.g_38_jp_shop.repository.RoleRepository;
import de.ait_tr.g_38_jp_shop.security.AuthInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class TokenService {

    private static final int ACCESS_TOKEN_EXPIRATION_DAYS = 7;
    private static final int REFRESH_TOKEN_EXPIRATION_DAYS = 30;

    private static final String ROLES_IN_CLAIM_KEY = "roles";
    private static final String USERNAME_IN_CLAIM_KEY = "name";

    private SecretKey accessKey;
    private SecretKey refreshKey;
    private RoleRepository roleRepository;

    public TokenService(
            @Value("${key.access}") String accessKey,
            @Value("${key.refresh}") String refreshKey,
            RoleRepository roleRepository
    ) {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
        this.roleRepository = roleRepository;
    }

    public String generateAccessToken(User user) {
        LocalDateTime currentDate = LocalDateTime.now();
        Instant expirationInstant = currentDate
                .plusDays(ACCESS_TOKEN_EXPIRATION_DAYS)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Date expirationDate = Date.from(expirationInstant);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(accessKey)
                .claim(ROLES_IN_CLAIM_KEY, user.getAuthorities())
                .claim(USERNAME_IN_CLAIM_KEY, user.getUsername())
                .compact();
    }

    public String generateRefreshToken(User user) {
        LocalDateTime currentDate = LocalDateTime.now();
        Instant expirationInstant = currentDate
                .plusDays(REFRESH_TOKEN_EXPIRATION_DAYS)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Date expirationDate = Date.from(expirationInstant);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(refreshKey)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, accessKey);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, refreshKey);
    }

    private boolean validateToken(String token, SecretKey key) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getAccessClaims(String accessToken) {
        return getClaims(accessToken, accessKey);
    }

    public Claims getRefreshClaims(String refreshToken) {
        return getClaims(refreshToken, refreshKey);
    }

    private Claims getClaims(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public AuthInfo mapClaimsToAuthInfo(Claims claims) {
        String username = claims.getSubject();
        List<LinkedHashMap<String, String>> rolesList = (List<LinkedHashMap<String, String>>) claims.get(ROLES_IN_CLAIM_KEY);
        Set<Role> roles = new HashSet<>();

        for (LinkedHashMap<String, String> roleEntry : rolesList) {
            String roleTitle = roleEntry.get("authority");
            Role role = roleRepository.findByTitle(roleTitle);
            roles.add(role);
        }

        return new AuthInfo(username, roles);
    }
}
