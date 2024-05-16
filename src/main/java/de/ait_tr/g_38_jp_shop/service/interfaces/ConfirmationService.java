package de.ait_tr.g_38_jp_shop.service.interfaces;

import de.ait_tr.g_38_jp_shop.domain.entity.User;
import de.ait_tr.g_38_jp_shop.exception_handling.exception.ConfirmationCodeException;

public interface ConfirmationService {

    String generateConfirmationCode(User user);
    void activateCode(String code) throws ConfirmationCodeException;
}
