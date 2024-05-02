package de.ait_tr.g_38_jp_shop.repository;

import de.ait_tr.g_38_jp_shop.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
