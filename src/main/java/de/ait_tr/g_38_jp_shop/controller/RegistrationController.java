package de.ait_tr.g_38_jp_shop.controller;

import de.ait_tr.g_38_jp_shop.domain.dto.UserDto;
import de.ait_tr.g_38_jp_shop.service.interfaces.ConfirmationService;
import de.ait_tr.g_38_jp_shop.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private UserService service;
    private ConfirmationService confirmationService;

    public RegistrationController(UserService service, ConfirmationService confirmationService) {
        this.service = service;
        this.confirmationService = confirmationService;
    }

    @GetMapping
    public ResponseEntity<String> activate(@RequestParam String code) {
        try {
            confirmationService.activateCode(code);
            return ResponseEntity.ok("Activated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public UserDto register(@RequestBody UserDto userDto) {
        return service.register(userDto);
    }
}
