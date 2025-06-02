package com.automobile.service.service;

import com.automobile.service.dao.JobCardDAO;
import com.automobile.service.model.JobCard;
import com.automobile.service.model.JobService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JobCardService class for handling business logic related to JobCard operations
 */
public class JobCardService {
    
    private JobCardDAO jobCardDAO;
    
    public JobCardService() {
        this.jobCardDAO = new JobCardDAO();
    }
    
    /**
     * Add a new job card
     * @param jobCard JobCard object to add
     * @return ID of the newly created job card, or -1 if failed
     */
    public int addJobCard(JobCard jobCard) {
        // Validate job card data
        if (!validateJobCard(jobCard)) {
            return -1;
        }
        
        // Set default values for new job card
        jobCard.setStatus("OPEN");
        jobCard.setOpenDate(LocalDateTime.now());
        
        return jobCardDAO.addJobCard(jobCard);
    }
    
    /**
     * Update an existing job card
     * @param jobCard JobCard object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateJobCard(JobCard jobCard) {
        // Validate job card data
        if (!validateJobCard(jobCard)) {
            return false;
        }
        
        return jobCardDAO.updateJobCard(jobCard);
    }
    
    /**
     * Update job card status
     * @param jobCardId ID of the job card
     * @param status New status
     * @return true if successful, false otherwise
     */
    public boolean updateJobCardStatus(int jobCardId, String status) {
        // Validate status
        if (!isValidStatus(status)) {
            return false;
        }
        
        // If status is COMPLETED, set close date
        LocalDateTime closeDate = null;
        if (status.equals("COMPLETED") || status.equals("DELIVERED")) {
            closeDate = LocalDateTime.now();
        }
        
        return jobCardDAO.updateJobCardStatus(jobCardId, status, closeDate);
    }
    
    /**
     * Get a job card by ID
     * @param jobCardId ID of the job card to retrieve
     * @return JobCard object if found, null otherwise
     */
    public JobCard getJobCardById(int jobCardId) {
        return jobCardDAO.getJobCardById(jobCardId);
    }
    
    /**
     * Get all job cards
     * @return List of JobCard objects
     */
    public List<JobCard> getAllJobCards() {
        return jobCardDAO.getAllJobCards();
    }
    
    /**
     * Get job cards by status
     * @param status Status to filter by
     * @return List of JobCard objects
     */
    public List<JobCard> getJobCardsByStatus(String status) {
        return jobCardDAO.getJobCardsByStatus(status);
    }
    
    /**
     * Get job cards for a specific vehicle
     * @param vehicleId ID of the vehicle
     * @return List of JobCard objects
     */
    public List<JobCard> getJobCardsByVehicleId(int vehicleId) {
        return jobCardDAO.getJobCardsByVehicleId(vehicleId);
    }
    
    /**
     * Add a service to a job card
     * @param jobCardId ID of the job card
     * @param serviceId ID of the service
     * @return true if successful, false otherwise
     */
    public boolean addServiceToJobCard(int jobCardId, int serviceId) {
        return jobCardDAO.addServiceToJobCard(jobCardId, serviceId);
    }
    
    /**
     * Get services for a job card
     * @param jobCardId ID of the job card
     * @return List of JobService objects
     */
    public List<JobService> getServicesForJobCard(int jobCardId) {
        return jobCardDAO.getServicesForJobCard(jobCardId);
    }
    
    /**
     * Update job service status
     * @param jobServiceId ID of the job service
     * @param status New status
     * @return true if successful, false otherwise
     */
    public boolean updateJobServiceStatus(int jobServiceId, String status) {
        // Validate status
        if (!isValidJobServiceStatus(status)) {
            return false;
        }
        
        return jobCardDAO.updateJobServiceStatus(jobServiceId, status);
    }
    
    /**
     * Validate job card data
     * @param jobCard JobCard object to validate
     * @return true if valid, false otherwise
     */
    private boolean validateJobCard(JobCard jobCard) {
        // Check for required fields
        if (jobCard.getVehicleId() <= 0) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Check if status is valid
     * @param status Status to check
     * @return true if valid, false otherwise
     */
    private boolean isValidStatus(String status) {
        return status != null && (
            status.equals("OPEN") ||
            status.equals("IN_PROGRESS") ||
            status.equals("COMPLETED") ||
            status.equals("DELIVERED") ||
            status.equals("CANCELLED")
        );
    }
    
    /**
     * Check if job service status is valid
     * @param status Status to check
     * @return true if valid, false otherwise
     */
    private boolean isValidJobServiceStatus(String status) {
        return status != null && (
            status.equals("PENDING") ||
            status.equals("IN_PROGRESS") ||
            status.equals("COMPLETED")
        );
    }
}
