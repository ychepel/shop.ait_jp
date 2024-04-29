package de.ait_tr.g_38_jp_shop.controller;

import de.ait_tr.g_38_jp_shop.domain.entity.Customer;
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
    public Customer save(@RequestBody Customer customer) {
        return service.save(customer);
    }

    @PutMapping
    public void update(@RequestBody Customer customer) {
        service.update(customer);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {
        service.deleteById(id);
    }
}
