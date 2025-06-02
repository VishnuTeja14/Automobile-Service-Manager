package com.automobile.service.model;

import java.time.LocalDate;

/**
 * Vehicle model class representing a vehicle in the Automobile Service Manager
 */
public class Vehicle {
    private int vehicleId;
    private int customerId;
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private String vin;
    private String color;
    private int mileage;
    private LocalDate lastServiceDate;
    
    // Default constructor
    public Vehicle() {
    }
    
    // Parameterized constructor
    public Vehicle(int customerId, String make, String model, int year, 
                  String licensePlate, String vin, String color, int mileage) {
        this.customerId = customerId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.vin = vin;
        this.color = color;
        this.mileage = mileage;
    }
    
    // Full constructor
    public Vehicle(int vehicleId, int customerId, String make, String model, 
                  int year, String licensePlate, String vin, String color, 
                  int mileage, LocalDate lastServiceDate) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.vin = vin;
        this.color = color;
        this.mileage = mileage;
        this.lastServiceDate = lastServiceDate;
    }
    
    // Getters and Setters
    public int getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public String getMake() {
        return make;
    }
    
    public void setMake(String make) {
        this.make = make;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public String getLicensePlate() {
        return licensePlate;
    }
    
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    
    public String getVin() {
        return vin;
    }
    
    public void setVin(String vin) {
        this.vin = vin;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public int getMileage() {
        return mileage;
    }
    
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
    
    public LocalDate getLastServiceDate() {
        return lastServiceDate;
    }
    
    public void setLastServiceDate(LocalDate lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }
    
    // Helper method to get vehicle description
    public String getVehicleDescription() {
        return year + " " + make + " " + model + " (" + color + ")";
    }
    
    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", customerId=" + customerId +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", licensePlate='" + licensePlate + '\'' +
                ", vin='" + vin + '\'' +
                ", color='" + color + '\'' +
                ", mileage=" + mileage +
                ", lastServiceDate=" + lastServiceDate +
                '}';
    }
}
