package com.automobile.service.test;

import com.automobile.service.model.Customer;
import com.automobile.service.service.CustomerService;

import java.util.List;

/**
 * Test class for CustomerService
 */
public class CustomerServiceTest {
    
    public static void main(String[] args) {
        testCustomerService();
    }
    
    public static void testCustomerService() {
        CustomerService customerService = new CustomerService();
        
        System.out.println("===== Testing CustomerService =====");
        
        // Test adding a customer
        Customer customer = new Customer();
        customer.setFirstName("Test");
        customer.setLastName("Customer");
        customer.setPhone("(555) 123-4567");
        customer.setEmail("test.customer@example.com");
        customer.setAddress("123 Test St");
        customer.setCity("Test City");
        customer.setState("TS");
        customer.setZipCode("12345");
        
        boolean addResult = customerService.addCustomer(customer);
        System.out.println("Add customer result: " + addResult);
        System.out.println("Customer ID: " + customer.getCustomerId());
        
        if (customer.getCustomerId() > 0) {
            // Test getting customer by ID
            Customer retrievedCustomer = customerService.getCustomerById(customer.getCustomerId());
            System.out.println("Retrieved customer: " + retrievedCustomer);
            
            // Test updating customer
            retrievedCustomer.setPhone("(555) 987-6543");
            boolean updateResult = customerService.updateCustomer(retrievedCustomer);
            System.out.println("Update customer result: " + updateResult);
            
            // Test getting customer by phone
            Customer customerByPhone = customerService.getCustomerByPhone("(555) 987-6543");
            System.out.println("Customer by phone: " + customerByPhone);
            
            // Test getting customer by email
            Customer customerByEmail = customerService.getCustomerByEmail("test.customer@example.com");
            System.out.println("Customer by email: " + customerByEmail);
            
            // Test getting all customers
            List<Customer> allCustomers = customerService.getAllCustomers();
            System.out.println("All customers count: " + allCustomers.size());
            
            // Test searching customers by name
            List<Customer> customersByName = customerService.searchCustomersByName("Test");
            System.out.println("Customers by name count: " + customersByName.size());
            
            // Test deleting customer
            boolean deleteResult = customerService.deleteCustomer(customer.getCustomerId());
            System.out.println("Delete customer result: " + deleteResult);
        }
        
        System.out.println("===== CustomerService Test Complete =====");
    }
}
