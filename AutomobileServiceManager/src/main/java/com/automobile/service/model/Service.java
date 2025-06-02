package com.automobile.service.model;

import java.math.BigDecimal;

/**
 * Service model class representing a service in the Automobile Service Manager
 */
public class Service {
    private int serviceId;
    private String serviceName;
    private String description;
    private BigDecimal standardPrice;
    private BigDecimal estimatedHours;
    
    // Default constructor
    public Service() {
    }
    
    // Parameterized constructor
    public Service(String serviceName, String description, BigDecimal standardPrice, BigDecimal estimatedHours) {
        this.serviceName = serviceName;
        this.description = description;
        this.standardPrice = standardPrice;
        this.estimatedHours = estimatedHours;
    }
    
    // Full constructor
    public Service(int serviceId, String serviceName, String description, 
                  BigDecimal standardPrice, BigDecimal estimatedHours) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.description = description;
        this.standardPrice = standardPrice;
        this.estimatedHours = estimatedHours;
    }
    
    // Getters and Setters
    public int getServiceId() {
        return serviceId;
    }
    
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
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
    
    public BigDecimal getStandardPrice() {
        return standardPrice;
    }
    
    public void setStandardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
    }
    
    public BigDecimal getEstimatedHours() {
        return estimatedHours;
    }
    
    public void setEstimatedHours(BigDecimal estimatedHours) {
        this.estimatedHours = estimatedHours;
    }
    
    @Override
    public String toString() {
        return "Service{" +
                "serviceId=" + serviceId +
                ", serviceName='" + serviceName + '\'' +
                ", description='" + description + '\'' +
                ", standardPrice=" + standardPrice +
                ", estimatedHours=" + estimatedHours +
                '}';
    }
}
