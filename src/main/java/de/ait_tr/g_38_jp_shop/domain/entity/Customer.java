package de.ait_tr.g_38_jp_shop.domain.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "cart_id")
    private Long cartId;

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public Long getCartId() {
            return cartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return isActive == customer.isActive && Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(cartId, customer.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isActive, cartId);
    }

    @Override
    public String toString() {
        return String.format("Customer: ID - %d, name - %s, cartID - %d, active - %s",
                id, name, cartId, isActive ? "yes" : "no");
    }
}
