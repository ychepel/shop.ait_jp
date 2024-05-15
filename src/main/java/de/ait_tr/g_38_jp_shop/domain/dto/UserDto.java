package de.ait_tr.g_38_jp_shop.domain.dto;

import de.ait_tr.g_38_jp_shop.domain.entity.Role;

import java.util.Objects;
import java.util.Set;

public class UserDto {

    private Long id;
    private String username;
    private String password;
    private Set<Role> roles;
    private String email;
    private boolean isActive;

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return isActive == userDto.isActive && Objects.equals(id, userDto.id) && Objects.equals(username, userDto.username) && Objects.equals(password, userDto.password) && Objects.equals(roles, userDto.roles) && Objects.equals(email, userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, roles, email, isActive);
    }

    @Override
    public String toString() {
        return String.format("User Dto: ID - %d, username - %s, email - %s, active - %s, roles - %s"
                , id, username, email, isActive ? "yes" : "no", roles);
    }
}
