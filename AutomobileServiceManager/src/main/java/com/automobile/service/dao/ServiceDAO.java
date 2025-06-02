package com.automobile.service.dao;

import com.automobile.service.model.Service;
import com.automobile.service.util.DBConnectionUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ServiceDAO class for handling database operations related to Service
 */
public class ServiceDAO {
    
    private Connection connection;
    
    public ServiceDAO() {
        try {
            this.connection = DBConnectionUtil.getConnection();
        } catch (SQLException e) {
            System.err.println("Error initializing ServiceDAO: " + e.getMessage());
        }
    }
    
    /**
     * Add a new service to the database
     * @param service Service object to add
     * @return true if successful, false otherwise
     */
    public boolean addService(Service service) {
        String sql = "INSERT INTO services (service_name, description, standard_price, estimated_hours) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, service.getServiceName());
            stmt.setString(2, service.getDescription());
            stmt.setBigDecimal(3, service.getStandardPrice());
            stmt.setBigDecimal(4, service.getEstimatedHours());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Get the generated service ID
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    service.setServiceId(rs.getInt(1));
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error adding service: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Update an existing service in the database
     * @param service Service object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateService(Service service) {
        String sql = "UPDATE services SET service_name = ?, description = ?, " +
                     "standard_price = ?, estimated_hours = ? " +
                     "WHERE service_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, service.getServiceName());
            stmt.setString(2, service.getDescription());
            stmt.setBigDecimal(3, service.getStandardPrice());
            stmt.setBigDecimal(4, service.getEstimatedHours());
            stmt.setInt(5, service.getServiceId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating service: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Delete a service from the database
     * @param serviceId ID of the service to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteService(int serviceId) {
        String sql = "DELETE FROM services WHERE service_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, serviceId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting service: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Get a service by ID
     * @param serviceId ID of the service to retrieve
     * @return Service object if found, null otherwise
     */
    public Service getServiceById(int serviceId) {
        String sql = "SELECT * FROM services WHERE service_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, serviceId);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractServiceFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting service by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Get all services from the database
     * @return List of Service objects
     */
    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM services ORDER BY service_name";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                services.add(extractServiceFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all services: " + e.getMessage());
        }
        
        return services;
    }
    
    /**
     * Search for services by name
     * @param name Name to search for
     * @return List of matching Service objects
     */
    public List<Service> searchServicesByName(String name) {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM services WHERE service_name LIKE ? ORDER BY service_name";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                services.add(extractServiceFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching services by name: " + e.getMessage());
        }
        
        return services;
    }
    
    /**
     * Helper method to extract a Service object from a ResultSet
     * @param rs ResultSet containing service data
     * @return Service object
     * @throws SQLException if there's an error accessing the ResultSet
     */
    private Service extractServiceFromResultSet(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setServiceId(rs.getInt("service_id"));
        service.setServiceName(rs.getString("service_name"));
        service.setDescription(rs.getString("description"));
        service.setStandardPrice(rs.getBigDecimal("standard_price"));
        service.setEstimatedHours(rs.getBigDecimal("estimated_hours"));
        
        return service;
    }
}
