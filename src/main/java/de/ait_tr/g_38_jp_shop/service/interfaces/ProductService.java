package de.ait_tr.g_38_jp_shop.service.interfaces;

import de.ait_tr.g_38_jp_shop.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductDto save(ProductDto product);
    List<ProductDto> getAll();
    ProductDto getById(Long id);
    void update(ProductDto product);
    void deleteById(Long id);
    void deleteByTitle(String title);
    void restoreById(Long id);
    int getTotalQuantity();
    BigDecimal getTotalPrice();
    BigDecimal getAveragePrice();
    void attachImage(String imageUrl, String productTitle);
}
