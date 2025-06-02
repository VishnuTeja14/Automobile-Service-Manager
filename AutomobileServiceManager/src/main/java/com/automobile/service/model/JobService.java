package com.automobile.service.model;

import java.math.BigDecimal;

/**
 * JobService model class representing a service performed in a job card
 */
public class JobService {
    private int jobServiceId;
    private int jobCardId;
    private int serviceId;
    private BigDecimal actualPrice;
    private BigDecimal actualHours;
    private String notes;
    private String status; // PENDING, IN_PROGRESS, COMPLETED
    
    // Additional fields for service details
    private String serviceName;
    private String description;
    
    // Default constructor
    public JobService() {
    }
    
    // Getters and Setters
    public int getJobServiceId() {
        return jobServiceId;
    }
    
    public void setJobServiceId(int jobServiceId) {
        this.jobServiceId = jobServiceId;
    }
    
    public int getJobCardId() {
        return jobCardId;
    }
    
    public void setJobCardId(int jobCardId) {
        this.jobCardId = jobCardId;
    }
    
    public int getServiceId() {
        return serviceId;
    }
    
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
    
    public BigDecimal getActualPrice() {
        return actualPrice;
    }
    
    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }
    
    public void setActualPrice(double actualPrice) {
        this.actualPrice = BigDecimal.valueOf(actualPrice);
    }
    
    public BigDecimal getActualHours() {
        return actualHours;
    }
    
    public void setActualHours(BigDecimal actualHours) {
        this.actualHours = actualHours;
    }
    
    public void setActualHours(double actualHours) {
        this.actualHours = BigDecimal.valueOf(actualHours);
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "JobService{" +
                "jobServiceId=" + jobServiceId +
                ", jobCardId=" + jobCardId +
                ", serviceId=" + serviceId +
                ", actualPrice=" + actualPrice +
                ", actualHours=" + actualHours +
                ", notes='" + notes + '\'' +
                ", status='" + status + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
