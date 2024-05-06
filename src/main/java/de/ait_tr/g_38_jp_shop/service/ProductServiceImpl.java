package de.ait_tr.g_38_jp_shop.service;

import de.ait_tr.g_38_jp_shop.domain.dto.ProductDto;
import de.ait_tr.g_38_jp_shop.domain.entity.Product;
import de.ait_tr.g_38_jp_shop.repository.ProductRepository;
import de.ait_tr.g_38_jp_shop.service.interfaces.ProductService;
import de.ait_tr.g_38_jp_shop.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;
    private ProductMappingService mappingService;

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        Product product = mappingService.mapDtoToEntity(productDto);
        repository.save(product);
        return mappingService.mapEntityToDto(product);
    }

    @Override
    public List<ProductDto> getAll() {
        return getFilteredSTream()
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public ProductDto getById(Long id) {
        if (id == null || id < 1) {
            throw new RuntimeException("Product ID is invalid");
        }

        Product product = repository.findById(id).orElse(null);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        if (!product.isActive() || product.isDeleted()) {
            return null;
        }

        return mappingService.mapEntityToDto(product);
    }

    @Override
    public void update(ProductDto productDto) {
        Product product = mappingService.mapDtoToEntity(productDto);
        product.setId(productDto.getProductId());
        repository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByTitle(String title) {
        Product product = repository.findByTitle(title);
        repository.delete(product);
    }

    @Override
    public void restoreById(Long id) {
        Product product = repository.findById(id).orElse(null);
        if (product != null) {
            product.setDeleted(false);
            repository.save(product);
        }
    }

    @Override
    public int getTotalQuantity() {
        return (int) getFilteredSTream().count();
    }

    @Override
    public BigDecimal getTotalPrice() {
        return getFilteredSTream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getAveragePrice() {
        return getTotalPrice().divide(new BigDecimal(getTotalQuantity()), RoundingMode.HALF_UP);
    }

    private Stream<Product> getFilteredSTream() {
        return repository.findAll()
                .stream()
                .filter(product -> product.isActive() && !product.isDeleted());
    }
}
