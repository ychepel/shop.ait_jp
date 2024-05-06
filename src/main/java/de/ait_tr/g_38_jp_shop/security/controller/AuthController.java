package de.ait_tr.g_38_jp_shop.security.controller;

import de.ait_tr.g_38_jp_shop.domain.entity.User;
import de.ait_tr.g_38_jp_shop.security.dto.RefreshRequestDto;
import de.ait_tr.g_38_jp_shop.security.dto.TokenResponseDto;
import de.ait_tr.g_38_jp_shop.security.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        try {
            TokenResponseDto response = service.login(user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/access")
    public ResponseEntity<Object> getNewAccessToken(@RequestBody RefreshRequestDto request) {
        try {
            TokenResponseDto response = service.getAccessToken(request.getRefreshToken());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
