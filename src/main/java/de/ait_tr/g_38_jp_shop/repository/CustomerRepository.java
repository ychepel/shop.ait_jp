package de.ait_tr.g_38_jp_shop.repository;

import de.ait_tr.g_38_jp_shop.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
