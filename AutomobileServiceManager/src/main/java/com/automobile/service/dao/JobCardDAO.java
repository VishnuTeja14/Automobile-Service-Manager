package com.automobile.service.dao;

import com.automobile.service.model.JobCard;
import com.automobile.service.model.JobService;
import com.automobile.service.model.Service;
import com.automobile.service.util.DBConnectionUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JobCardDAO class for handling database operations related to JobCard
 */
public class JobCardDAO {
    
    private Connection connection;
    
    public JobCardDAO() {
        try {
            this.connection = DBConnectionUtil.getConnection();
        } catch (SQLException e) {
            System.err.println("Error initializing JobCardDAO: " + e.getMessage());
        }
    }
    
    /**
     * Add a new job card to the database
     * @param jobCard JobCard object to add
     * @return ID of the newly created job card, or -1 if failed
     */
    public int addJobCard(JobCard jobCard) {
        String sql = "INSERT INTO job_cards (vehicle_id, open_date, status, technician_notes, customer_complaints) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, jobCard.getVehicleId());
            
            if (jobCard.getOpenDate() != null) {
                stmt.setTimestamp(2, Timestamp.valueOf(jobCard.getOpenDate()));
            } else {
                stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            }
            
            stmt.setString(3, jobCard.getStatus());
            stmt.setString(4, jobCard.getTechnicianNotes());
            stmt.setString(5, jobCard.getCustomerComplaints());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Get the generated job card ID
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error adding job card: " + e.getMessage());
        }
        
        return -1;
    }
    
    /**
     * Update an existing job card in the database
     * @param jobCard JobCard object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateJobCard(JobCard jobCard) {
        String sql = "UPDATE job_cards SET vehicle_id = ?, status = ?, " +
                     "technician_notes = ?, customer_complaints = ? " +
                     "WHERE job_card_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, jobCard.getVehicleId());
            stmt.setString(2, jobCard.getStatus());
            stmt.setString(3, jobCard.getTechnicianNotes());
            stmt.setString(4, jobCard.getCustomerComplaints());
            stmt.setInt(5, jobCard.getJobCardId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating job card: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Update job card status
     * @param jobCardId ID of the job card
     * @param status New status
     * @param closeDate Close date (if status is COMPLETED or DELIVERED)
     * @return true if successful, false otherwise
     */
    public boolean updateJobCardStatus(int jobCardId, String status, LocalDateTime closeDate) {
        String sql = "UPDATE job_cards SET status = ?, close_date = ? WHERE job_card_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            
            if (closeDate != null) {
                stmt.setTimestamp(2, Timestamp.valueOf(closeDate));
            } else {
                stmt.setNull(2, Types.TIMESTAMP);
            }
            
            stmt.setInt(3, jobCardId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating job card status: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Get a job card by ID
     * @param jobCardId ID of the job card to retrieve
     * @return JobCard object if found, null otherwise
     */
    public JobCard getJobCardById(int jobCardId) {
        String sql = "SELECT * FROM job_cards WHERE job_card_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, jobCardId);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractJobCardFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting job card by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Get all job cards
     * @return List of JobCard objects
     */
    public List<JobCard> getAllJobCards() {
        List<JobCard> jobCards = new ArrayList<>();
        String sql = "SELECT * FROM job_cards ORDER BY open_date DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                jobCards.add(extractJobCardFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all job cards: " + e.getMessage());
        }
        
        return jobCards;
    }
    
    /**
     * Get job cards by status
     * @param status Status to filter by
     * @return List of JobCard objects
     */
    public List<JobCard> getJobCardsByStatus(String status) {
        List<JobCard> jobCards = new ArrayList<>();
        String sql = "SELECT * FROM job_cards WHERE status = ? ORDER BY open_date DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                jobCards.add(extractJobCardFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting job cards by status: " + e.getMessage());
        }
        
        return jobCards;
    }
    
    /**
     * Get job cards for a specific vehicle
     * @param vehicleId ID of the vehicle
     * @return List of JobCard objects
     */
    public List<JobCard> getJobCardsByVehicleId(int vehicleId) {
        List<JobCard> jobCards = new ArrayList<>();
        String sql = "SELECT * FROM job_cards WHERE vehicle_id = ? ORDER BY open_date DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, vehicleId);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                jobCards.add(extractJobCardFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting job cards by vehicle ID: " + e.getMessage());
        }
        
        return jobCards;
    }
    
    /**
     * Add a service to a job card
     * @param jobCardId ID of the job card
     * @param serviceId ID of the service
     * @return true if successful, false otherwise
     */
    public boolean addServiceToJobCard(int jobCardId, int serviceId) {
        // First, get the service details to get the standard price
        String serviceSql = "SELECT * FROM services WHERE service_id = ?";
        
        try (PreparedStatement serviceStmt = connection.prepareStatement(serviceSql)) {
            serviceStmt.setInt(1, serviceId);
            
            ResultSet serviceRs = serviceStmt.executeQuery();
            
            if (serviceRs.next()) {
                double standardPrice = serviceRs.getDouble("standard_price");
                
                // Now add the service to the job card
                String sql = "INSERT INTO job_services (job_card_id, service_id, actual_price, status) " +
                             "VALUES (?, ?, ?, 'PENDING')";
                
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, jobCardId);
                    stmt.setInt(2, serviceId);
                    stmt.setDouble(3, standardPrice);
                    
                    int rowsAffected = stmt.executeUpdate();
                    return rowsAffected > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error adding service to job card: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Get services for a job card
     * @param jobCardId ID of the job card
     * @return List of JobService objects
     */
    public List<JobService> getServicesForJobCard(int jobCardId) {
        List<JobService> jobServices = new ArrayList<>();
        String sql = "SELECT js.*, s.service_name, s.description FROM job_services js " +
                     "JOIN services s ON js.service_id = s.service_id " +
                     "WHERE js.job_card_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, jobCardId);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                JobService jobService = new JobService();
                jobService.setJobServiceId(rs.getInt("job_service_id"));
                jobService.setJobCardId(rs.getInt("job_card_id"));
                jobService.setServiceId(rs.getInt("service_id"));
                jobService.setActualPrice(rs.getDouble("actual_price"));
                jobService.setActualHours(rs.getDouble("actual_hours"));
                jobService.setNotes(rs.getString("notes"));
                jobService.setStatus(rs.getString("status"));
                
                // Add service details
                jobService.setServiceName(rs.getString("service_name"));
                jobService.setDescription(rs.getString("description"));
                
                jobServices.add(jobService);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting services for job card: " + e.getMessage());
        }
        
        return jobServices;
    }
    
    /**
     * Update job service status
     * @param jobServiceId ID of the job service
     * @param status New status
     * @return true if successful, false otherwise
     */
    public boolean updateJobServiceStatus(int jobServiceId, String status) {
        String sql = "UPDATE job_services SET status = ? WHERE job_service_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, jobServiceId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating job service status: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Helper method to extract a JobCard object from a ResultSet
     * @param rs ResultSet containing job card data
     * @return JobCard object
     * @throws SQLException if there's an error accessing the ResultSet
     */
    private JobCard extractJobCardFromResultSet(ResultSet rs) throws SQLException {
        JobCard jobCard = new JobCard();
        jobCard.setJobCardId(rs.getInt("job_card_id"));
        jobCard.setVehicleId(rs.getInt("vehicle_id"));
        
        Timestamp openDate = rs.getTimestamp("open_date");
        if (openDate != null) {
            jobCard.setOpenDate(openDate.toLocalDateTime());
        }
        
        Timestamp closeDate = rs.getTimestamp("close_date");
        if (closeDate != null) {
            jobCard.setCloseDate(closeDate.toLocalDateTime());
        }
        
        jobCard.setStatus(rs.getString("status"));
        jobCard.setTechnicianNotes(rs.getString("technician_notes"));
        jobCard.setCustomerComplaints(rs.getString("customer_complaints"));
        
        return jobCard;
    }
}
