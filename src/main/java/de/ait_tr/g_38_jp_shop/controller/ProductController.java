package de.ait_tr.g_38_jp_shop.controller;

import de.ait_tr.g_38_jp_shop.domain.dto.ProductDto;
import de.ait_tr.g_38_jp_shop.service.interfaces.ProductService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public Object get(@RequestParam Optional<Long> id) {
        if (id.isEmpty()) {
            return service.getAll();
        }
        return service.getById(id.get());
    }

    @PostMapping
    public ProductDto save(@RequestBody ProductDto product) {
        return service.save(product);
    }

    @PutMapping
    public void update(@RequestBody ProductDto product) {
        service.update(product);
    }

    @DeleteMapping
    public void deleteById(@RequestParam(required = false) Long id, @RequestParam(required = false) String title) {
        if (id != null) {
            service.deleteById(id);
        } else if (title != null) {
            service.deleteByTitle(title);
        }
    }

    @GetMapping("/total-quantity")
    public int getTotalQuantity() {
        return service.getTotalQuantity();
    }

    @GetMapping("/total-price")
    public BigDecimal getTotalPrice() {
        return service.getTotalPrice();
    }

    @GetMapping("/average-price")
    public BigDecimal getAveragePrice() {
        return service.getAveragePrice();
    }
}
