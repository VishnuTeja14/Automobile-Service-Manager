package com.automobile.service.service;

import com.automobile.service.dao.CustomerDAO;
import com.automobile.service.model.Customer;

import java.util.List;

/**
 * CustomerService class for handling business logic related to Customer operations
 */
public class CustomerService {
    
    private CustomerDAO customerDAO;
    
    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }
    
    /**
     * Add a new customer
     * @param customer Customer object to add
     * @return true if successful, false otherwise
     */
    public boolean addCustomer(Customer customer) {
        // Validate customer data
        if (!validateCustomer(customer)) {
            return false;
        }
        
        return customerDAO.addCustomer(customer);
    }
    
    /**
     * Update an existing customer
     * @param customer Customer object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateCustomer(Customer customer) {
        // Validate customer data
        if (!validateCustomer(customer)) {
            return false;
        }
        
        return customerDAO.updateCustomer(customer);
    }
    
    /**
     * Delete a customer
     * @param customerId ID of the customer to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteCustomer(int customerId) {
        return customerDAO.deleteCustomer(customerId);
    }
    
    /**
     * Get a customer by ID
     * @param customerId ID of the customer to retrieve
     * @return Customer object if found, null otherwise
     */
    public Customer getCustomerById(int customerId) {
        return customerDAO.getCustomerById(customerId);
    }
    
    /**
     * Get a customer by phone number
     * @param phone Phone number to search for
     * @return Customer object if found, null otherwise
     */
    public Customer getCustomerByPhone(String phone) {
        return customerDAO.getCustomerByPhone(phone);
    }
    
    /**
     * Get a customer by email
     * @param email Email to search for
     * @return Customer object if found, null otherwise
     */
    public Customer getCustomerByEmail(String email) {
        return customerDAO.getCustomerByEmail(email);
    }
    
    /**
     * Get all customers
     * @return List of Customer objects
     */
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }
    
    /**
     * Search for customers by name
     * @param name Name to search for
     * @return List of matching Customer objects
     */
    public List<Customer> searchCustomersByName(String name) {
        return customerDAO.searchCustomersByName(name);
    }
    
    /**
     * Validate customer data
     * @param customer Customer object to validate
     * @return true if valid, false otherwise
     */
    private boolean validateCustomer(Customer customer) {
        // Check for required fields
        if (customer.getFirstName() == null || customer.getFirstName().trim().isEmpty()) {
            return false;
        }
        
        if (customer.getLastName() == null || customer.getLastName().trim().isEmpty()) {
            return false;
        }
        
        if (customer.getPhone() == null || customer.getPhone().trim().isEmpty()) {
            return false;
        }
        
        // Validate phone format (simple validation)
        String phonePattern = "^\\(\\d{3}\\)\\s\\d{3}-\\d{4}$|^\\d{3}-\\d{3}-\\d{4}$|^\\d{10}$";
        if (!customer.getPhone().matches(phonePattern)) {
            // Allow for different phone formats
            if (!customer.getPhone().replaceAll("[^0-9]", "").matches("^\\d{10}$")) {
                return false;
            }
        }
        
        // Validate email if provided
        if (customer.getEmail() != null && !customer.getEmail().trim().isEmpty()) {
            String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
            if (!customer.getEmail().matches(emailPattern)) {
                return false;
            }
        }
        
        return true;
    }
}
