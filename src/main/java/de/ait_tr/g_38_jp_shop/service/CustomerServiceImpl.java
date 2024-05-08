package de.ait_tr.g_38_jp_shop.service;

import de.ait_tr.g_38_jp_shop.domain.dto.CartProductDto;
import de.ait_tr.g_38_jp_shop.domain.dto.CustomerDto;
import de.ait_tr.g_38_jp_shop.domain.dto.ProductDto;
import de.ait_tr.g_38_jp_shop.domain.entity.Customer;
import de.ait_tr.g_38_jp_shop.domain.entity.Product;
import de.ait_tr.g_38_jp_shop.repository.CustomerRepository;
import de.ait_tr.g_38_jp_shop.service.interfaces.ProductService;
import de.ait_tr.g_38_jp_shop.service.interfaces.CustomerService;
import de.ait_tr.g_38_jp_shop.service.mapping.CustomerMappingService;
import de.ait_tr.g_38_jp_shop.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository repository;
    private CustomerMappingService mappingService;
    private ProductService productService;
    private ProductMappingService productMappingService;

    public CustomerServiceImpl(
            CustomerRepository repository,
            CustomerMappingService mappingService,
            ProductService productRepository,
            ProductMappingService productMappingService
    ) {
        this.repository = repository;
        this.mappingService = mappingService;
        this.productService = productRepository;
        this.productMappingService = productMappingService;
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = mappingService.mapDtoToEntity(customerDto);
        repository.save(customer);
        return mappingService.mapEntityToDto(customer);
    }

    @Override
    public List<CustomerDto> getAll() {
        return getFilteredStream()
                .map(customer -> mappingService.mapEntityToDto(customer))
                .toList();
    }

    @Override
    public CustomerDto getById(Long id) {
        Optional<Customer> customer = getActiveCustomer(id);
        return customer.map(mappingService::mapEntityToDto).orElse(null);
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
        return (int) getFilteredStream().count();
    }

    @Override
    public List<Product> getCartProducts(Long customerId) {
        Optional<Customer> customer = getActiveCustomer(customerId);
        return customer.map(value -> value.getCart().getProducts()).orElse(null);

    }

    @Override
    public BigDecimal getCartTotalPrice(Long customerId) {
        Optional<Customer> customer = getActiveCustomer(customerId);
        if (customer.isEmpty()) {
            return null;
        }

        List<Product> products = customer.get().getCart().getProducts();
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getCartAveragePrice(Long customerId) {
        Optional<Customer> customer = getActiveCustomer(customerId);
        if (customer.isEmpty()) {
            return null;
        }

        List<Product> products = customer.get().getCart().getProducts();
        BigDecimal totalPrice = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal productsQuantity = BigDecimal.valueOf(products.size());

        return totalPrice.divide(productsQuantity, RoundingMode.HALF_UP);
    }

    @Override
    @Transactional
    public List<Product> addProductToCart(CartProductDto cartProductDto) {
        Optional<Customer> customer = getActiveCustomer(cartProductDto.getCustomerId());
        if (customer.isEmpty()) {
            return null;
        }

        ProductDto productDto = productService.getById(cartProductDto.getProductId());
        if (productDto == null) {
            return null;
        }

        List<Product> cartProducts = customer.get().getCart().getProducts();
        cartProducts.add(productMappingService.mapDtoToEntity(productDto));

        return cartProducts;
    }

    @Override
    @Transactional
    public List<Product> removeProductFromCart(CartProductDto cartProductDto) {
        Optional<Customer> customer = getActiveCustomer(cartProductDto.getCustomerId());
        if (customer.isEmpty()) {
            return null;
        }

        ProductDto productDto = productService.getById(cartProductDto.getProductId());
        if (productDto == null) {
            return null;
        }

        List<Product> cartProducts = customer.get().getCart().getProducts();
        cartProducts.remove(productMappingService.mapDtoToEntity(productDto));

        return cartProducts;
    }

    @Override
    @Transactional
    public void clearCart(Long customerId) {
        Optional<Customer> customer = getActiveCustomer(customerId);
        customer.ifPresent(c -> c.getCart().setProducts(new ArrayList<>()));
    }

    private Stream<Customer> getFilteredStream() {
        return repository.findAll()
                .stream()
                .filter(Customer::isActive)
                .filter(customer -> !customer.getDeleted());
    }

    private Optional<Customer> getActiveCustomer(Long id) {
        if (id == null || id < 1) {
            throw new RuntimeException("Customer ID is invalid");
        }

        Optional<Customer> customer = repository.findByIdAndIsActiveAndDeleted(id, true, false);
        if (customer.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }

        return customer;
    }
}
