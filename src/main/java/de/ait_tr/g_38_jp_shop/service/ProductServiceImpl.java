package de.ait_tr.g_38_jp_shop.service;

import de.ait_tr.g_38_jp_shop.domain.dto.ProductDto;
import de.ait_tr.g_38_jp_shop.domain.entity.Product;
import de.ait_tr.g_38_jp_shop.exception_handling.exception.*;
import de.ait_tr.g_38_jp_shop.repository.ProductRepository;
import de.ait_tr.g_38_jp_shop.service.interfaces.ProductService;
import de.ait_tr.g_38_jp_shop.service.mapping.ProductMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ProductRepository repository;
    private ProductMappingService mappingService;

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        Product product = mappingService.mapDtoToEntity(productDto);
        product.setId(null);
        try {
            repository.save(product);
        } catch (Exception e) {
            throw new ProductSaveException("Trying to save invalid product", e);
        }
        return mappingService.mapEntityToDto(product);
    }

    @Override
    public List<ProductDto> getAll() {
        return getFilteredStream()
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public ProductDto getById(Long id) {
        if (id == null || id < 1) {
            logger.warn("Invalid product id = {}", id);
            throw new InvalidRequestException("Product ID is invalid");
        }

        Product product = repository.findById(id).orElse(null);

        if (product == null) {
            throw new ProductNotFoundException("Product not found");
        }
        if (!product.isActive() || product.isDeleted()) {
            throw new ProductUnavailableException("Product is not available");
        }

        return mappingService.mapEntityToDto(product);
    }

    @Override
    public void update(ProductDto productDto) {
        if (!repository.existsById(productDto.getProductId())) {
            throw new ProductNotFoundException("Product is not exist");
        }
        Product product = mappingService.mapDtoToEntity(productDto);
        try {
            repository.save(product);
        } catch (Exception e) {
            throw new ProductUpdateException("Product cannot be updated", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException("Product is not exist");
        }
        repository.deleteById(id);
    }

    @Override
    public void deleteByTitle(String title) {
        Product product = repository.findByTitle(title);
        if (product == null) {
            throw new ProductNotFoundException("Product is not exist");
        }
        repository.delete(product);
    }

    @Override
    public void restoreById(Long id) {
        Product product = repository.findById(id).orElse(null);
        if (product != null) {
            product.setDeleted(false);
            repository.save(product);
        } else {
            throw new ProductNotFoundException("Product is not exist");
        }
    }

    @Override
    public int getTotalQuantity() {
        return (int) getFilteredStream().count();
    }

    @Override
    public BigDecimal getTotalPrice() {
        return getFilteredStream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getAveragePrice() {
        return getTotalPrice().divide(new BigDecimal(getTotalQuantity()), RoundingMode.HALF_UP);
    }

    private Stream<Product> getFilteredStream() {
        return repository.findAll()
                .stream()
                .filter(Product::isActive)
                .filter(product -> !product.isDeleted());
    }
}
