package de.ait_tr.g_38_jp_shop.controller;

import de.ait_tr.g_38_jp_shop.domain.dto.ProductDto;
import de.ait_tr.g_38_jp_shop.exception_handling.Response;
import de.ait_tr.g_38_jp_shop.exception_handling.ThrowableController;
import de.ait_tr.g_38_jp_shop.exception_handling.exception.InvalidRequestException;
import de.ait_tr.g_38_jp_shop.service.interfaces.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController implements ThrowableController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ProductDto get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ProductDto> getAll() {
        return service.getAll();
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

    @PutMapping("/restore")
    public void setActive(@RequestParam Long id) {
        service.restoreById(id);
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

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleException(InvalidRequestException e) {
        return new Response(e.getMessage());
    }
}
