package de.ait_tr.g_38_jp_shop.service.interfaces;

import de.ait_tr.g_38_jp_shop.domain.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto save(CustomerDto customer);
    List<CustomerDto> getAll();
    CustomerDto getById(Long id);
    void update(CustomerDto customer);
    void deleteById(Long id);
    void deleteByName(String name);
    void restoreById(Long id);
    int getTotalQuantity();

    //TODO: add cart methods
    /*
     * Return the cost of the customer's basket by its identifier (if it is active).
     * Return the average cost of a product in the customer's basket by its identifier (if it is active).
     * Add a product to the customer's basket by their identifiers (if both are active).
     * Remove a product from the customer's basket by their identifiers.
     * Completely clear the customer's basket by its identifier (if it is active).
     */

}
