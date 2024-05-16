package de.ait_tr.g_38_jp_shop.service;

import de.ait_tr.g_38_jp_shop.domain.entity.ConfirmationCode;
import de.ait_tr.g_38_jp_shop.domain.entity.User;
import de.ait_tr.g_38_jp_shop.exception_handling.exception.ConfirmationCodeException;
import de.ait_tr.g_38_jp_shop.exception_handling.exception.UserNotFoundException;
import de.ait_tr.g_38_jp_shop.repository.ConfirmationCodeRepository;
import de.ait_tr.g_38_jp_shop.service.interfaces.ConfirmationService;
import de.ait_tr.g_38_jp_shop.service.interfaces.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private final static int EXPIRATION_INTERVAL_MINUTES = 1;

    private ConfirmationCodeRepository repository;
    private UserService userService;

    public ConfirmationServiceImpl(ConfirmationCodeRepository repository, @Lazy UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public String generateConfirmationCode(User user) {
        LocalDateTime expired = LocalDateTime.now().plusMinutes(EXPIRATION_INTERVAL_MINUTES);
        String code = UUID.randomUUID().toString();
        ConfirmationCode entity = new ConfirmationCode(code, expired, user);
        repository.save(entity);
        return code;
    }

    @Override
    public void activateCode(String code) throws ConfirmationCodeException {
        ConfirmationCode dbCode = repository.findByCode(code);
        if (dbCode == null) {
            throw new ConfirmationCodeException("Wrong confirmation code");
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(dbCode.getExpired())) {
            throw new ConfirmationCodeException("Expired confirmation code");
        }

        User user = dbCode.getUser();
        if (user == null) {
            throw new UserNotFoundException("No user with ConfirmationCode=" + code);
        }
        if (user.isActive()) {
            throw new ConfirmationCodeException("User is already active");
        }

        user.setActive(true);
        userService.update(user);
    }
}
