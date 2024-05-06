package de.ait_tr.g_38_jp_shop.repository;

import de.ait_tr.g_38_jp_shop.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Transactional
    void deleteByName(String name);
}
