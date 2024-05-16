package de.ait_tr.g_38_jp_shop.service.interfaces;

import de.ait_tr.g_38_jp_shop.domain.dto.UserDto;
import de.ait_tr.g_38_jp_shop.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto register(UserDto userDto);
    void update(User user);
}
