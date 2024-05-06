package de.ait_tr.g_38_jp_shop.domain.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductDto {

    private Long productId;
    private String title;
    private BigDecimal price;

    public ProductDto() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(productId, that.productId) && Objects.equals(title, that.title) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, title, price);
    }

    @Override
    public String toString() {
        return String.format("Product DTO: ID - %d, title - %s, price - %.2f", productId, title, price);
    }
}
