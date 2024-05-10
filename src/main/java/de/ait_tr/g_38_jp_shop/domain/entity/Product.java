package de.ait_tr.g_38_jp_shop.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product")
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE id=?")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @NotEmpty(message = "Product title cannot be empty")
    @Size(min = 5, message = "Product title should be greater or equal to 5 symbols")
    private String title;

    @Column(name = "price")
    @Min(value = 1, message = "Product price could not be less than 1 BTC")
    private BigDecimal price;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "deleted")
    private boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return isActive == product.isActive && isDeleted == product.isDeleted && Objects.equals(id, product.id) && Objects.equals(title, product.title) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, isActive, isDeleted);
    }

    @Override
    public String toString() {
        return String.format("Product: ID - %d, title - %s, price - %.2f, active - %s",
                id, title, price, isActive ? "yes" : "no");
    }
}
