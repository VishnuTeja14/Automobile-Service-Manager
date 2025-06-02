package com.automobile.service.test;

import com.automobile.service.model.Service;
import com.automobile.service.service.ServiceCatalogService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Test class for ServiceCatalogService
 */
public class ServiceCatalogServiceTest {
    
    public static void main(String[] args) {
        testServiceCatalogService();
    }
    
    public static void testServiceCatalogService() {
        ServiceCatalogService serviceCatalogService = new ServiceCatalogService();
        
        System.out.println("===== Testing ServiceCatalogService =====");
        
        // Test adding a service
        Service service = new Service();
        service.setServiceName("Test Service");
        service.setDescription("This is a test service for unit testing");
        service.setStandardPrice(new BigDecimal("59.99"));
        service.setEstimatedHours(new BigDecimal("1.5"));
        
        boolean addResult = serviceCatalogService.addService(service);
        System.out.println("Add service result: " + addResult);
        System.out.println("Service ID: " + service.getServiceId());
        
        if (service.getServiceId() > 0) {
            // Test getting service by ID
            Service retrievedService = serviceCatalogService.getServiceById(service.getServiceId());
            System.out.println("Retrieved service: " + retrievedService);
            
            // Test updating service
            retrievedService.setStandardPrice(new BigDecimal("69.99"));
            boolean updateResult = serviceCatalogService.updateService(retrievedService);
            System.out.println("Update service result: " + updateResult);
            
            // Test getting all services
            List<Service> allServices = serviceCatalogService.getAllServices();
            System.out.println("All services count: " + allServices.size());
            
            // Test searching services by name
            List<Service> servicesByName = serviceCatalogService.searchServicesByName("Test");
            System.out.println("Services by name count: " + servicesByName.size());
            
            // Test deleting service
            boolean deleteResult = serviceCatalogService.deleteService(service.getServiceId());
            System.out.println("Delete service result: " + deleteResult);
        }
        
        System.out.println("===== ServiceCatalogService Test Complete =====");
    }
}
