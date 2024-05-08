package de.ait_tr.g_38_jp_shop.domain.dto;

import java.util.Objects;

public class CartProductDto {

    private Long customerId;
    private Long productId;

    public CartProductDto() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartProductDto that = (CartProductDto) o;
        return Objects.equals(customerId, that.customerId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, productId);
    }

    @Override
    public String toString() {
        return String.format("CartProductDto: customerId - %d, productId - %d", customerId, productId);
    }
}
