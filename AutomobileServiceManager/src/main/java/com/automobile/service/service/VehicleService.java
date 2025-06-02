package com.automobile.service.service;

import com.automobile.service.dao.VehicleDAO;
import com.automobile.service.model.Vehicle;

import java.time.LocalDate;
import java.util.List;

/**
 * VehicleService class for handling business logic related to Vehicle operations
 */
public class VehicleService {
    
    private VehicleDAO vehicleDAO;
    
    public VehicleService() {
        this.vehicleDAO = new VehicleDAO();
    }
    
    /**
     * Add a new vehicle
     * @param vehicle Vehicle object to add
     * @return true if successful, false otherwise
     */
    public boolean addVehicle(Vehicle vehicle) {
        // Validate vehicle data
        if (!validateVehicle(vehicle)) {
            return false;
        }
        
        return vehicleDAO.addVehicle(vehicle);
    }
    
    /**
     * Update an existing vehicle
     * @param vehicle Vehicle object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateVehicle(Vehicle vehicle) {
        // Validate vehicle data
        if (!validateVehicle(vehicle)) {
            return false;
        }
        
        return vehicleDAO.updateVehicle(vehicle);
    }
    
    /**
     * Delete a vehicle
     * @param vehicleId ID of the vehicle to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteVehicle(int vehicleId) {
        return vehicleDAO.deleteVehicle(vehicleId);
    }
    
    /**
     * Get a vehicle by ID
     * @param vehicleId ID of the vehicle to retrieve
     * @return Vehicle object if found, null otherwise
     */
    public Vehicle getVehicleById(int vehicleId) {
        return vehicleDAO.getVehicleById(vehicleId);
    }
    
    /**
     * Get a vehicle by license plate
     * @param licensePlate License plate to search for
     * @return Vehicle object if found, null otherwise
     */
    public Vehicle getVehicleByLicensePlate(String licensePlate) {
        return vehicleDAO.getVehicleByLicensePlate(licensePlate);
    }
    
    /**
     * Get all vehicles for a specific customer
     * @param customerId ID of the customer
     * @return List of Vehicle objects
     */
    public List<Vehicle> getVehiclesByCustomerId(int customerId) {
        return vehicleDAO.getVehiclesByCustomerId(customerId);
    }
    
    /**
     * Get all vehicles
     * @return List of Vehicle objects
     */
    public List<Vehicle> getAllVehicles() {
        return vehicleDAO.getAllVehicles();
    }
    
    /**
     * Update the last service date for a vehicle
     * @param vehicleId ID of the vehicle
     * @param lastServiceDate New last service date
     * @return true if successful, false otherwise
     */
    public boolean updateLastServiceDate(int vehicleId, LocalDate lastServiceDate) {
        return vehicleDAO.updateLastServiceDate(vehicleId, lastServiceDate);
    }
    
    /**
     * Validate vehicle data
     * @param vehicle Vehicle object to validate
     * @return true if valid, false otherwise
     */
    private boolean validateVehicle(Vehicle vehicle) {
        // Check for required fields
        if (vehicle.getCustomerId() <= 0) {
            return false;
        }
        
        if (vehicle.getMake() == null || vehicle.getMake().trim().isEmpty()) {
            return false;
        }
        
        if (vehicle.getModel() == null || vehicle.getModel().trim().isEmpty()) {
            return false;
        }
        
        if (vehicle.getYear() < 1900 || vehicle.getYear() > LocalDate.now().getYear() + 1) {
            return false;
        }
        
        if (vehicle.getLicensePlate() == null || vehicle.getLicensePlate().trim().isEmpty()) {
            return false;
        }
        
        // Validate VIN if provided (basic validation)
        if (vehicle.getVin() != null && !vehicle.getVin().trim().isEmpty()) {
            // VIN is typically 17 characters for modern vehicles
            if (vehicle.getVin().length() != 17) {
                return false;
            }
        }
        
        return true;
    }
}
