package de.ait_tr.g_38_jp_shop.service.interfaces;

import de.ait_tr.g_38_jp_shop.domain.dto.CartProductDto;
import de.ait_tr.g_38_jp_shop.domain.dto.CustomerDto;
import de.ait_tr.g_38_jp_shop.domain.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    CustomerDto save(CustomerDto customer);
    List<CustomerDto> getAll();
    CustomerDto getById(Long id);
    void update(CustomerDto customer);
    void deleteById(Long id);
    void deleteByName(String name);
    void restoreById(Long id);
    int getTotalQuantity();
    List<Product> getCartProducts(Long customerId);
    BigDecimal getCartTotalPrice(Long customerId);
    BigDecimal getCartAveragePrice(Long customerId);
    List<Product> addProductToCart(CartProductDto cartProductDto);
    List<Product> removeProductFromCart(CartProductDto cartProductDto);
    void clearCart(Long customerId);
}
