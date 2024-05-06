package de.ait_tr.g_38_jp_shop.domain.dto;

import java.util.Objects;

public class CustomerDto {

    private Long id;
    private String name;
    private Long cartId;

    public CustomerDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDto that = (CustomerDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(cartId, that.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cartId);
    }

    @Override
    public String toString() {
        return String.format("Customer DTO: ID - %d, name - %s, cart ID - %d", id, name, cartId);
    }
}
