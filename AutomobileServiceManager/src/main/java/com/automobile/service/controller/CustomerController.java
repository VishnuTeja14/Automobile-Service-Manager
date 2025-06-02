package com.automobile.service.controller;

import com.automobile.service.model.Customer;
import com.automobile.service.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * CustomerController servlet for handling customer-related HTTP requests
 */
@WebServlet("/customers/*")
public class CustomerController extends HttpServlet {
    
    private CustomerService customerService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        customerService = new CustomerService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // List all customers
            List<Customer> customers = customerService.getAllCustomers();
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("/WEB-INF/views/customers.jsp").forward(request, response);
            
        } else if (pathInfo.equals("/add")) {
            // Show add customer form
            request.getRequestDispatcher("/WEB-INF/views/customers-add.jsp").forward(request, response);
            
        } else if (pathInfo.startsWith("/edit/")) {
            // Show edit customer form
            try {
                int customerId = Integer.parseInt(pathInfo.substring(6));
                Customer customer = customerService.getCustomerById(customerId);
                
                if (customer != null) {
                    request.setAttribute("customer", customer);
                    request.getRequestDispatcher("/WEB-INF/views/customers-edit.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid customer ID");
            }
            
        } else if (pathInfo.startsWith("/view/")) {
            // Show customer details
            try {
                int customerId = Integer.parseInt(pathInfo.substring(6));
                Customer customer = customerService.getCustomerById(customerId);
                
                if (customer != null) {
                    request.setAttribute("customer", customer);
                    request.getRequestDispatcher("/WEB-INF/views/customers-view.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid customer ID");
            }
            
        } else if (pathInfo.equals("/search")) {
            // Search customers
            String searchTerm = request.getParameter("term");
            
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                List<Customer> customers = customerService.searchCustomersByName(searchTerm);
                request.setAttribute("customers", customers);
                request.setAttribute("searchTerm", searchTerm);
                request.getRequestDispatcher("/WEB-INF/views/customers.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/customers");
            }
            
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Default POST not supported
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            
        } else if (pathInfo.equals("/add")) {
            // Add new customer
            Customer customer = extractCustomerFromRequest(request);
            
            if (customerService.addCustomer(customer)) {
                response.sendRedirect(request.getContextPath() + "/customers");
            } else {
                request.setAttribute("customer", customer);
                request.setAttribute("errorMessage", "Failed to add customer. Please check the form and try again.");
                request.getRequestDispatcher("/WEB-INF/views/customers-add.jsp").forward(request, response);
            }
            
        } else if (pathInfo.startsWith("/edit/")) {
            // Update existing customer
            try {
                int customerId = Integer.parseInt(pathInfo.substring(6));
                Customer customer = extractCustomerFromRequest(request);
                customer.setCustomerId(customerId);
                
                if (customerService.updateCustomer(customer)) {
                    response.sendRedirect(request.getContextPath() + "/customers");
                } else {
                    request.setAttribute("customer", customer);
                    request.setAttribute("errorMessage", "Failed to update customer. Please check the form and try again.");
                    request.getRequestDispatcher("/WEB-INF/views/customers-edit.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid customer ID");
            }
            
        } else if (pathInfo.startsWith("/delete/")) {
            // Delete customer
            try {
                int customerId = Integer.parseInt(pathInfo.substring(8));
                
                if (customerService.deleteCustomer(customerId)) {
                    response.sendRedirect(request.getContextPath() + "/customers");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete customer");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid customer ID");
            }
            
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * Extract customer data from request parameters
     * @param request HttpServletRequest containing form data
     * @return Customer object populated with form data
     */
    private Customer extractCustomerFromRequest(HttpServletRequest request) {
        Customer customer = new Customer();
        
        customer.setFirstName(request.getParameter("firstName"));
        customer.setLastName(request.getParameter("lastName"));
        customer.setPhone(request.getParameter("phone"));
        customer.setEmail(request.getParameter("email"));
        customer.setAddress(request.getParameter("address"));
        customer.setCity(request.getParameter("city"));
        customer.setState(request.getParameter("state"));
        customer.setZipCode(request.getParameter("zipCode"));
        
        return customer;
    }
}
