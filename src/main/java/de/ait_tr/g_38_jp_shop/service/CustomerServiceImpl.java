package de.ait_tr.g_38_jp_shop.service;

import de.ait_tr.g_38_jp_shop.domain.entity.Customer;
import de.ait_tr.g_38_jp_shop.repository.CustomerRepository;
import de.ait_tr.g_38_jp_shop.service.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public List<Customer> getAll() {
        return repository.findAll();
    }

    @Override
    public Customer getById(Long id) {
        if (id == null || id < 1) {
            throw new RuntimeException("Customer ID is invalid");
        }

        Customer customer = repository.findById(id).orElse(null);

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        return customer;
    }

    @Override
    public void update(Customer customer) {
        repository.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
