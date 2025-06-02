package com.automobile.service.dao;

import com.automobile.service.model.Customer;
import com.automobile.service.util.DBConnectionUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * CustomerDAO class for handling database operations related to Customer
 */
public class CustomerDAO {
    
    private Connection connection;
    
    public CustomerDAO() {
        try {
            this.connection = DBConnectionUtil.getConnection();
        } catch (SQLException e) {
            System.err.println("Error initializing CustomerDAO: " + e.getMessage());
        }
    }
    
    /**
     * Add a new customer to the database
     * @param customer Customer object to add
     * @return true if successful, false otherwise
     */
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (first_name, last_name, phone, email, address, city, state, zip_code) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getEmail());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getCity());
            stmt.setString(7, customer.getState());
            stmt.setString(8, customer.getZipCode());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Get the generated customer ID
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    customer.setCustomerId(rs.getInt(1));
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Update an existing customer in the database
     * @param customer Customer object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, phone = ?, " +
                     "email = ?, address = ?, city = ?, state = ?, zip_code = ? " +
                     "WHERE customer_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getEmail());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getCity());
            stmt.setString(7, customer.getState());
            stmt.setString(8, customer.getZipCode());
            stmt.setInt(9, customer.getCustomerId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating customer: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Delete a customer from the database
     * @param customerId ID of the customer to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Get a customer by ID
     * @param customerId ID of the customer to retrieve
     * @return Customer object if found, null otherwise
     */
    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractCustomerFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting customer by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Get a customer by phone number
     * @param phone Phone number to search for
     * @return Customer object if found, null otherwise
     */
    public Customer getCustomerByPhone(String phone) {
        String sql = "SELECT * FROM customers WHERE phone = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phone);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractCustomerFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting customer by phone: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Get a customer by email
     * @param email Email to search for
     * @return Customer object if found, null otherwise
     */
    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractCustomerFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting customer by email: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Get all customers from the database
     * @return List of Customer objects
     */
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY last_name, first_name";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                customers.add(extractCustomerFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all customers: " + e.getMessage());
        }
        
        return customers;
    }
    
    /**
     * Search for customers by name (first or last)
     * @param name Name to search for
     * @return List of matching Customer objects
     */
    public List<Customer> searchCustomersByName(String name) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE first_name LIKE ? OR last_name LIKE ? " +
                     "ORDER BY last_name, first_name";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + name + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                customers.add(extractCustomerFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching customers by name: " + e.getMessage());
        }
        
        return customers;
    }
    
    /**
     * Helper method to extract a Customer object from a ResultSet
     * @param rs ResultSet containing customer data
     * @return Customer object
     * @throws SQLException if there's an error accessing the ResultSet
     */
    private Customer extractCustomerFromResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setPhone(rs.getString("phone"));
        customer.setEmail(rs.getString("email"));
        customer.setAddress(rs.getString("address"));
        customer.setCity(rs.getString("city"));
        customer.setState(rs.getString("state"));
        customer.setZipCode(rs.getString("zip_code"));
        
        Date registrationDate = rs.getDate("registration_date");
        if (registrationDate != null) {
            customer.setRegistrationDate(registrationDate.toLocalDate());
        }
        
        return customer;
    }
}
