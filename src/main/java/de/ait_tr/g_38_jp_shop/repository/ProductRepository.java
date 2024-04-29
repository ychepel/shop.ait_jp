package de.ait_tr.g_38_jp_shop.repository;

import de.ait_tr.g_38_jp_shop.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByTitle(String title);

    @Query(value = "SELECT * FROM product WHERE title = :title AND price = :price", nativeQuery = true)
    Product findByTitleAndPrice(@Param("title") String productTitle, @Param("price")BigDecimal price);
}
