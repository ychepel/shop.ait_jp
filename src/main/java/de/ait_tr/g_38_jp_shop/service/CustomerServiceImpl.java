package de.ait_tr.g_38_jp_shop.service;

import de.ait_tr.g_38_jp_shop.domain.dto.CustomerDto;
import de.ait_tr.g_38_jp_shop.domain.entity.Customer;
import de.ait_tr.g_38_jp_shop.repository.CustomerRepository;
import de.ait_tr.g_38_jp_shop.service.interfaces.CustomerService;
import de.ait_tr.g_38_jp_shop.service.mapping.CustomerMappingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository repository;
    private CustomerMappingService mappingService;

    public CustomerServiceImpl(CustomerRepository repository, CustomerMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = mappingService.mapDtoToEntity(customerDto);
        repository.save(customer);
        return mappingService.mapEntityToDto(customer);
    }

    @Override
    public List<CustomerDto> getAll() {
        return getFilteredSTream()
                .map(customer -> mappingService.mapEntityToDto(customer))
                .toList();
    }

    @Override
    public CustomerDto getById(Long id) {
        if (id == null || id < 1) {
            throw new RuntimeException("Customer ID is invalid");
        }

        Customer customer = repository.findById(id).orElse(null);

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }
        if (!customer.isActive() || customer.isDeleted()) {
            return null;
        }

        return mappingService.mapEntityToDto(customer);
    }

    @Override
    public void update(CustomerDto customerDto) {
        Customer customer = mappingService.mapDtoToEntity(customerDto);
        customer.setId(customerDto.getId());
        repository.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

    @Override
    public void restoreById(Long id) {
        Customer customer = repository.findById(id).orElse(null);
        if (customer != null) {
            customer.setDeleted(false);
            repository.save(customer);
        }
    }

    @Override
    public int getTotalQuantity() {
        return (int) getFilteredSTream().count();
    }

    private Stream<Customer> getFilteredSTream() {
        return repository.findAll()
                .stream()
                .filter(customer -> customer.isActive() && !customer.isDeleted());
    }
}
