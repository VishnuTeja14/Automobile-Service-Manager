package com.automobile.service.dao;

import com.automobile.service.model.Vehicle;
import com.automobile.service.util.DBConnectionUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * VehicleDAO class for handling database operations related to Vehicle
 */
public class VehicleDAO {
    
    private Connection connection;
    
    public VehicleDAO() {
        try {
            this.connection = DBConnectionUtil.getConnection();
        } catch (SQLException e) {
            System.err.println("Error initializing VehicleDAO: " + e.getMessage());
        }
    }
    
    /**
     * Add a new vehicle to the database
     * @param vehicle Vehicle object to add
     * @return true if successful, false otherwise
     */
    public boolean addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (customer_id, make, model, year, license_plate, vin, color, mileage) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, vehicle.getCustomerId());
            stmt.setString(2, vehicle.getMake());
            stmt.setString(3, vehicle.getModel());
            stmt.setInt(4, vehicle.getYear());
            stmt.setString(5, vehicle.getLicensePlate());
            stmt.setString(6, vehicle.getVin());
            stmt.setString(7, vehicle.getColor());
            stmt.setInt(8, vehicle.getMileage());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Get the generated vehicle ID
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    vehicle.setVehicleId(rs.getInt(1));
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Update an existing vehicle in the database
     * @param vehicle Vehicle object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE vehicles SET customer_id = ?, make = ?, model = ?, year = ?, " +
                     "license_plate = ?, vin = ?, color = ?, mileage = ?, last_service_date = ? " +
                     "WHERE vehicle_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, vehicle.getCustomerId());
            stmt.setString(2, vehicle.getMake());
            stmt.setString(3, vehicle.getModel());
            stmt.setInt(4, vehicle.getYear());
            stmt.setString(5, vehicle.getLicensePlate());
            stmt.setString(6, vehicle.getVin());
            stmt.setString(7, vehicle.getColor());
            stmt.setInt(8, vehicle.getMileage());
            
            if (vehicle.getLastServiceDate() != null) {
                stmt.setDate(9, Date.valueOf(vehicle.getLastServiceDate()));
            } else {
                stmt.setNull(9, Types.DATE);
            }
            
            stmt.setInt(10, vehicle.getVehicleId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating vehicle: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Delete a vehicle from the database
     * @param vehicleId ID of the vehicle to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteVehicle(int vehicleId) {
        String sql = "DELETE FROM vehicles WHERE vehicle_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, vehicleId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting vehicle: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Get a vehicle by ID
     * @param vehicleId ID of the vehicle to retrieve
     * @return Vehicle object if found, null otherwise
     */
    public Vehicle getVehicleById(int vehicleId) {
        String sql = "SELECT * FROM vehicles WHERE vehicle_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, vehicleId);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractVehicleFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting vehicle by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Get a vehicle by license plate
     * @param licensePlate License plate to search for
     * @return Vehicle object if found, null otherwise
     */
    public Vehicle getVehicleByLicensePlate(String licensePlate) {
        String sql = "SELECT * FROM vehicles WHERE license_plate = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, licensePlate);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractVehicleFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting vehicle by license plate: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Get all vehicles for a specific customer
     * @param customerId ID of the customer
     * @return List of Vehicle objects
     */
    public List<Vehicle> getVehiclesByCustomerId(int customerId) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE customer_id = ? ORDER BY year DESC, make, model";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                vehicles.add(extractVehicleFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting vehicles by customer ID: " + e.getMessage());
        }
        
        return vehicles;
    }
    
    /**
     * Get all vehicles from the database
     * @return List of Vehicle objects
     */
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles ORDER BY make, model, year DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                vehicles.add(extractVehicleFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all vehicles: " + e.getMessage());
        }
        
        return vehicles;
    }
    
    /**
     * Update the last service date for a vehicle
     * @param vehicleId ID of the vehicle
     * @param lastServiceDate New last service date
     * @return true if successful, false otherwise
     */
    public boolean updateLastServiceDate(int vehicleId, LocalDate lastServiceDate) {
        String sql = "UPDATE vehicles SET last_service_date = ? WHERE vehicle_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (lastServiceDate != null) {
                stmt.setDate(1, Date.valueOf(lastServiceDate));
            } else {
                stmt.setNull(1, Types.DATE);
            }
            
            stmt.setInt(2, vehicleId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating last service date: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Helper method to extract a Vehicle object from a ResultSet
     * @param rs ResultSet containing vehicle data
     * @return Vehicle object
     * @throws SQLException if there's an error accessing the ResultSet
     */
    private Vehicle extractVehicleFromResultSet(ResultSet rs) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(rs.getInt("vehicle_id"));
        vehicle.setCustomerId(rs.getInt("customer_id"));
        vehicle.setMake(rs.getString("make"));
        vehicle.setModel(rs.getString("model"));
        vehicle.setYear(rs.getInt("year"));
        vehicle.setLicensePlate(rs.getString("license_plate"));
        vehicle.setVin(rs.getString("vin"));
        vehicle.setColor(rs.getString("color"));
        vehicle.setMileage(rs.getInt("mileage"));
        
        Date lastServiceDate = rs.getDate("last_service_date");
        if (lastServiceDate != null) {
            vehicle.setLastServiceDate(lastServiceDate.toLocalDate());
        }
        
        return vehicle;
    }
}
