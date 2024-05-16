package de.ait_tr.g_38_jp_shop.security.service;

import de.ait_tr.g_38_jp_shop.domain.entity.User;
import de.ait_tr.g_38_jp_shop.security.dto.TokenResponseDto;
import de.ait_tr.g_38_jp_shop.service.interfaces.UserService;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private UserService userService;
    private TokenService tokenService;
    private Map<String, String> refreshStorage;
    private BCryptPasswordEncoder encoder;

    public AuthService(UserService userService, TokenService tokenService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.encoder = encoder;
        this.refreshStorage = new HashMap<>();
    }

    public TokenResponseDto login(@NonNull User inboundUser) throws AuthException {
        String username = inboundUser.getUsername();
        User foundUser = (User) userService.loadUserByUsername(username);

        if (!foundUser.isActive()) {
            throw new AuthException("User is not active. Please confirm your email first.");
        }
        if (encoder.matches(inboundUser.getPassword(), foundUser.getPassword())) {
            String accessToken = tokenService.generateAccessToken(foundUser);
            String refreshToken = tokenService.generateRefreshToken(foundUser);
            refreshStorage.put(username, refreshToken);
            return new TokenResponseDto(accessToken, refreshToken);
        } else {
            throw new AuthException("Password is incorrect");
        }
    }

    public TokenResponseDto getAccessToken(@NonNull String inboundRefreshToken) {
        if (tokenService.validateRefreshToken(inboundRefreshToken)) {
            Claims refreshClaims = tokenService.getRefreshClaims(inboundRefreshToken);
            String username = refreshClaims.getSubject();
            String storedRefreshToken = refreshStorage.get(username);

            if (inboundRefreshToken.equals(storedRefreshToken)) {
                User user = (User) userService.loadUserByUsername(username);
                String accessToken = tokenService.generateAccessToken(user);
                return new TokenResponseDto(accessToken, inboundRefreshToken);
            }
        }
        throw new RuntimeException("Refresh token is incorrect");
    }
}
