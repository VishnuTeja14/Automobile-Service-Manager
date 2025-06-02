package com.automobile.service.model;

import java.time.LocalDateTime;

/**
 * JobCard model class representing a service job card in the Automobile Service Manager
 */
public class JobCard {
    private int jobCardId;
    private int vehicleId;
    private LocalDateTime openDate;
    private LocalDateTime closeDate;
    private String status; // OPEN, IN_PROGRESS, COMPLETED, DELIVERED, CANCELLED
    private String technicianNotes;
    private String customerComplaints;
    
    // Default constructor
    public JobCard() {
    }
    
    // Parameterized constructor
    public JobCard(int vehicleId, String customerComplaints) {
        this.vehicleId = vehicleId;
        this.customerComplaints = customerComplaints;
        this.status = "OPEN";
        this.openDate = LocalDateTime.now();
    }
    
    // Full constructor
    public JobCard(int jobCardId, int vehicleId, LocalDateTime openDate, 
                  LocalDateTime closeDate, String status, String technicianNotes, 
                  String customerComplaints) {
        this.jobCardId = jobCardId;
        this.vehicleId = vehicleId;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.status = status;
        this.technicianNotes = technicianNotes;
        this.customerComplaints = customerComplaints;
    }
    
    // Getters and Setters
    public int getJobCardId() {
        return jobCardId;
    }
    
    public void setJobCardId(int jobCardId) {
        this.jobCardId = jobCardId;
    }
    
    public int getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public LocalDateTime getOpenDate() {
        return openDate;
    }
    
    public void setOpenDate(LocalDateTime openDate) {
        this.openDate = openDate;
    }
    
    public LocalDateTime getCloseDate() {
        return closeDate;
    }
    
    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getTechnicianNotes() {
        return technicianNotes;
    }
    
    public void setTechnicianNotes(String technicianNotes) {
        this.technicianNotes = technicianNotes;
    }
    
    public String getCustomerComplaints() {
        return customerComplaints;
    }
    
    public void setCustomerComplaints(String customerComplaints) {
        this.customerComplaints = customerComplaints;
    }
    
    @Override
    public String toString() {
        return "JobCard{" +
                "jobCardId=" + jobCardId +
                ", vehicleId=" + vehicleId +
                ", openDate=" + openDate +
                ", closeDate=" + closeDate +
                ", status='" + status + '\'' +
                ", technicianNotes='" + technicianNotes + '\'' +
                ", customerComplaints='" + customerComplaints + '\'' +
                '}';
    }
}
