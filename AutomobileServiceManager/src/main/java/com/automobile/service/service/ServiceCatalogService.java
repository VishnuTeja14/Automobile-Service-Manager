package com.automobile.service.service;

import com.automobile.service.dao.ServiceDAO;
import com.automobile.service.model.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * ServiceCatalogService class for handling business logic related to Service operations
 */
public class ServiceCatalogService {
    
    private ServiceDAO serviceDAO;
    
    public ServiceCatalogService() {
        this.serviceDAO = new ServiceDAO();
    }
    
    /**
     * Add a new service to the catalog
     * @param service Service object to add
     * @return true if successful, false otherwise
     */
    public boolean addService(Service service) {
        // Validate service data
        if (!validateService(service)) {
            return false;
        }
        
        return serviceDAO.addService(service);
    }
    
    /**
     * Update an existing service
     * @param service Service object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateService(Service service) {
        // Validate service data
        if (!validateService(service)) {
            return false;
        }
        
        return serviceDAO.updateService(service);
    }
    
    /**
     * Delete a service
     * @param serviceId ID of the service to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteService(int serviceId) {
        return serviceDAO.deleteService(serviceId);
    }
    
    /**
     * Get a service by ID
     * @param serviceId ID of the service to retrieve
     * @return Service object if found, null otherwise
     */
    public Service getServiceById(int serviceId) {
        return serviceDAO.getServiceById(serviceId);
    }
    
    /**
     * Get all services
     * @return List of Service objects
     */
    public List<Service> getAllServices() {
        return serviceDAO.getAllServices();
    }
    
    /**
     * Search for services by name
     * @param name Name to search for
     * @return List of matching Service objects
     */
    public List<Service> searchServicesByName(String name) {
        return serviceDAO.searchServicesByName(name);
    }
    
    /**
     * Validate service data
     * @param service Service object to validate
     * @return true if valid, false otherwise
     */
    private boolean validateService(Service service) {
        // Check for required fields
        if (service.getServiceName() == null || service.getServiceName().trim().isEmpty()) {
            return false;
        }
        
        if (service.getStandardPrice() == null || service.getStandardPrice().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        
        return true;
    }
}
