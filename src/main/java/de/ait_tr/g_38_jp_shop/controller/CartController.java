package de.ait_tr.g_38_jp_shop.controller;

import de.ait_tr.g_38_jp_shop.domain.dto.CartProductDto;
import de.ait_tr.g_38_jp_shop.domain.entity.Product;
import de.ait_tr.g_38_jp_shop.service.interfaces.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/customers/cart")
public class CartController {

    private CustomerService service;

    public CartController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getCartProducts(@RequestParam Long customerId) {
        return service.getCartProducts(customerId);
    }

    @GetMapping("/total-price")
    public BigDecimal getCartTotalPrice(@RequestParam Long customerId) {
        return service.getCartTotalPrice(customerId);
    }

    @GetMapping("/average-price")
    public BigDecimal getCartAveragePrice(@RequestParam Long customerId) {
        return service.getCartAveragePrice(customerId);
    }

    @PutMapping("/add-product")
    public List<Product> addProductToCart(@RequestBody CartProductDto cartProductDto) {
        return service.addProductToCart(cartProductDto);
    }

    @PutMapping("/remove-product")
    public List<Product> removeProductFromCart(@RequestBody CartProductDto cartProductDto) {
        return service.removeProductFromCart(cartProductDto);
    }

    @DeleteMapping("/products")
    public void removeAll(@RequestParam Long customerId) {
        service.clearCart(customerId);
    }
}
