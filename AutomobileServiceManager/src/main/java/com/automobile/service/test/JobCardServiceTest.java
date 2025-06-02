package com.automobile.service.test;

import com.automobile.service.model.JobCard;
import com.automobile.service.model.JobService;
import com.automobile.service.service.JobCardService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Test class for JobCardService
 */
public class JobCardServiceTest {
    
    public static void main(String[] args) {
        testJobCardService();
    }
    
    public static void testJobCardService() {
        JobCardService jobCardService = new JobCardService();
        
        System.out.println("===== Testing JobCardService =====");
        
        // Test adding a job card
        // Note: This requires a valid vehicle ID in the database
        JobCard jobCard = new JobCard();
        jobCard.setVehicleId(1); // Assuming vehicle ID 1 exists
        jobCard.setCustomerComplaints("Engine noise and vibration");
        jobCard.setTechnicianNotes("Initial inspection needed");
        
        int jobCardId = jobCardService.addJobCard(jobCard);
        System.out.println("Add job card result (ID): " + jobCardId);
        
        if (jobCardId > 0) {
            // Test getting job card by ID
            JobCard retrievedJobCard = jobCardService.getJobCardById(jobCardId);
            System.out.println("Retrieved job card: " + retrievedJobCard);
            
            // Test updating job card
            retrievedJobCard.setTechnicianNotes("Inspection completed. Found loose belt.");
            boolean updateResult = jobCardService.updateJobCard(retrievedJobCard);
            System.out.println("Update job card result: " + updateResult);
            
            // Test adding services to job card
            // Note: This requires valid service IDs in the database
            boolean addServiceResult1 = jobCardService.addServiceToJobCard(jobCardId, 1); // Assuming service ID 1 exists
            boolean addServiceResult2 = jobCardService.addServiceToJobCard(jobCardId, 2); // Assuming service ID 2 exists
            System.out.println("Add service 1 result: " + addServiceResult1);
            System.out.println("Add service 2 result: " + addServiceResult2);
            
            // Test getting services for job card
            List<JobService> jobServices = jobCardService.getServicesForJobCard(jobCardId);
            System.out.println("Job services count: " + jobServices.size());
            
            // Test updating job service status
            if (!jobServices.isEmpty()) {
                boolean updateServiceResult = jobCardService.updateJobServiceStatus(jobServices.get(0).getJobServiceId(), "IN_PROGRESS");
                System.out.println("Update job service status result: " + updateServiceResult);
            }
            
            // Test updating job card status
            boolean updateStatusResult = jobCardService.updateJobCardStatus(jobCardId, "IN_PROGRESS");
            System.out.println("Update job card status result: " + updateStatusResult);
            
            // Test getting job cards by status
            List<JobCard> jobCardsByStatus = jobCardService.getJobCardsByStatus("IN_PROGRESS");
            System.out.println("Job cards by status count: " + jobCardsByStatus.size());
            
            // Test getting job cards by vehicle ID
            List<JobCard> jobCardsByVehicleId = jobCardService.getJobCardsByVehicleId(1);
            System.out.println("Job cards by vehicle ID count: " + jobCardsByVehicleId.size());
            
            // Test getting all job cards
            List<JobCard> allJobCards = jobCardService.getAllJobCards();
            System.out.println("All job cards count: " + allJobCards.size());
            
            // Test completing job card
            boolean completeResult = jobCardService.updateJobCardStatus(jobCardId, "COMPLETED");
            System.out.println("Complete job card result: " + completeResult);
        }
        
        System.out.println("===== JobCardService Test Complete =====");
    }
}
