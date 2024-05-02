package de.ait_tr.g_38_jp_shop.controller;

import de.ait_tr.g_38_jp_shop.domain.dto.ProductDto;
import de.ait_tr.g_38_jp_shop.service.interfaces.ProductService;
import org.springframework.web.bind.annotation.*;

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
}
