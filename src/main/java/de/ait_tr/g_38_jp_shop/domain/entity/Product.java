package de.ait_tr.g_38_jp_shop.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Product title cannot be blank")
    @Pattern(
            regexp = "[A-Z][a-z]{2,}",
            message = "Product title should be at least 3 character length, " +
                    "start with capital letter and may contain only latin characters"
    )
    private String title;

    @Column(name = "price")
    @NotNull(message = "Product price cannot be null")
    @DecimalMin(
            value = "3.00",
            message = "Product price should be greater or equal than 3"
    )
    @DecimalMax(
            value = "100000.00",
            inclusive = false,
            message = "Product price should be lesser than 100 000"
    )
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
