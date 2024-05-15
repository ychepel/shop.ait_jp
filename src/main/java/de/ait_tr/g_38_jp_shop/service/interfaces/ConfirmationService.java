package de.ait_tr.g_38_jp_shop.service.interfaces;

import de.ait_tr.g_38_jp_shop.domain.entity.User;

public interface ConfirmationService {

    String generateConfirmationCode(User user);
}
