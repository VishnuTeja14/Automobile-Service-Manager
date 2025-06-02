package com.automobile.service.test;

import com.automobile.service.model.Vehicle;
import com.automobile.service.service.VehicleService;

import java.time.LocalDate;
import java.util.List;

/**
 * Test class for VehicleService
 */
public class VehicleServiceTest {
    
    public static void main(String[] args) {
        testVehicleService();
    }
    
    public static void testVehicleService() {
        VehicleService vehicleService = new VehicleService();
        
        System.out.println("===== Testing VehicleService =====");
        
        // Test adding a vehicle
        // Note: This requires a valid customer ID in the database
        Vehicle vehicle = new Vehicle();
        vehicle.setCustomerId(1); // Assuming customer ID 1 exists
        vehicle.setMake("Toyota");
        vehicle.setModel("Camry");
        vehicle.setYear(2020);
        vehicle.setLicensePlate("TEST123");
        vehicle.setVin("1HGCM82633A123456");
        vehicle.setColor("Silver");
        vehicle.setMileage(15000);
        
        boolean addResult = vehicleService.addVehicle(vehicle);
        System.out.println("Add vehicle result: " + addResult);
        System.out.println("Vehicle ID: " + vehicle.getVehicleId());
        
        if (vehicle.getVehicleId() > 0) {
            // Test getting vehicle by ID
            Vehicle retrievedVehicle = vehicleService.getVehicleById(vehicle.getVehicleId());
            System.out.println("Retrieved vehicle: " + retrievedVehicle);
            
            // Test updating vehicle
            retrievedVehicle.setMileage(16000);
            boolean updateResult = vehicleService.updateVehicle(retrievedVehicle);
            System.out.println("Update vehicle result: " + updateResult);
            
            // Test getting vehicle by license plate
            Vehicle vehicleByLicensePlate = vehicleService.getVehicleByLicensePlate("TEST123");
            System.out.println("Vehicle by license plate: " + vehicleByLicensePlate);
            
            // Test getting vehicles by customer ID
            List<Vehicle> vehiclesByCustomerId = vehicleService.getVehiclesByCustomerId(1);
            System.out.println("Vehicles by customer ID count: " + vehiclesByCustomerId.size());
            
            // Test getting all vehicles
            List<Vehicle> allVehicles = vehicleService.getAllVehicles();
            System.out.println("All vehicles count: " + allVehicles.size());
            
            // Test updating last service date
            boolean updateDateResult = vehicleService.updateLastServiceDate(vehicle.getVehicleId(), LocalDate.now());
            System.out.println("Update last service date result: " + updateDateResult);
            
            // Test deleting vehicle
            boolean deleteResult = vehicleService.deleteVehicle(vehicle.getVehicleId());
            System.out.println("Delete vehicle result: " + deleteResult);
        }
        
        System.out.println("===== VehicleService Test Complete =====");
    }
}
