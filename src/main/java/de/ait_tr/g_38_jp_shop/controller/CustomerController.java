package de.ait_tr.g_38_jp_shop.controller;

import de.ait_tr.g_38_jp_shop.domain.dto.CustomerDto;
import de.ait_tr.g_38_jp_shop.service.interfaces.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService service;

    public CustomerController(CustomerService service) {
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
    public CustomerDto save(@RequestBody CustomerDto customerDto) {
        return service.save(customerDto);
    }

    @PutMapping
    public void update(@RequestBody CustomerDto customerDto) {
        service.update(customerDto);
    }

    @DeleteMapping
    public void delete(@RequestParam(required = false) Long id, @RequestParam(required = false) String name) {
        if (id != null) {
            service.deleteById(id);
        } else if (name != null) {
            service.deleteByName(name);
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
}
