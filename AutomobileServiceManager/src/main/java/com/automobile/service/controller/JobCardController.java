package com.automobile.service.controller;

import com.automobile.service.model.JobCard;
import com.automobile.service.model.Service;
import com.automobile.service.model.Vehicle;
import com.automobile.service.service.JobCardService;
import com.automobile.service.service.ServiceCatalogService;
import com.automobile.service.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * JobCardController servlet for handling job card-related HTTP requests
 */
@WebServlet("/jobcards/*")
public class JobCardController extends HttpServlet {
    
    private JobCardService jobCardService;
    private VehicleService vehicleService;
    private ServiceCatalogService serviceCatalogService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        jobCardService = new JobCardService();
        vehicleService = new VehicleService();
        serviceCatalogService = new ServiceCatalogService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // List all job cards
            List<JobCard> jobCards = jobCardService.getAllJobCards();
            request.setAttribute("jobCards", jobCards);
            request.getRequestDispatcher("/WEB-INF/views/jobcards.jsp").forward(request, response);
            
        } else if (pathInfo.equals("/add")) {
            // Show add job card form
            request.setAttribute("vehicles", vehicleService.getAllVehicles());
            request.setAttribute("services", serviceCatalogService.getAllServices());
            request.getRequestDispatcher("/WEB-INF/views/jobcards-add.jsp").forward(request, response);
            
        } else if (pathInfo.startsWith("/edit/")) {
            // Show edit job card form
            try {
                int jobCardId = Integer.parseInt(pathInfo.substring(6));
                JobCard jobCard = jobCardService.getJobCardById(jobCardId);
                
                if (jobCard != null) {
                    request.setAttribute("jobCard", jobCard);
                    request.setAttribute("vehicle", vehicleService.getVehicleById(jobCard.getVehicleId()));
                    request.setAttribute("services", serviceCatalogService.getAllServices());
                    request.setAttribute("jobServices", jobCardService.getServicesForJobCard(jobCardId));
                    request.getRequestDispatcher("/WEB-INF/views/jobcards-edit.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Job card not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid job card ID");
            }
            
        } else if (pathInfo.startsWith("/view/")) {
            // Show job card details
            try {
                int jobCardId = Integer.parseInt(pathInfo.substring(6));
                JobCard jobCard = jobCardService.getJobCardById(jobCardId);
                
                if (jobCard != null) {
                    request.setAttribute("jobCard", jobCard);
                    request.setAttribute("vehicle", vehicleService.getVehicleById(jobCard.getVehicleId()));
                    request.setAttribute("jobServices", jobCardService.getServicesForJobCard(jobCardId));
                    request.getRequestDispatcher("/WEB-INF/views/jobcards-view.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Job card not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid job card ID");
            }
            
        } else if (pathInfo.equals("/filter")) {
            // Filter job cards by status
            String status = request.getParameter("status");
            
            if (status != null && !status.equals("all")) {
                List<JobCard> jobCards = jobCardService.getJobCardsByStatus(status);
                request.setAttribute("jobCards", jobCards);
                request.setAttribute("statusFilter", status);
                request.getRequestDispatcher("/WEB-INF/views/jobcards.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/jobcards");
            }
            
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Default POST not supported
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            
        } else if (pathInfo.equals("/add")) {
            // Add new job card
            JobCard jobCard = extractJobCardFromRequest(request);
            
            int jobCardId = jobCardService.addJobCard(jobCard);
            if (jobCardId > 0) {
                // Add selected services to the job card
                String[] serviceIds = request.getParameterValues("selectedServices");
                if (serviceIds != null) {
                    for (String serviceIdStr : serviceIds) {
                        try {
                            int serviceId = Integer.parseInt(serviceIdStr);
                            jobCardService.addServiceToJobCard(jobCardId, serviceId);
                        } catch (NumberFormatException e) {
                            // Skip invalid service IDs
                        }
                    }
                }
                
                response.sendRedirect(request.getContextPath() + "/jobcards/view/" + jobCardId);
            } else {
                request.setAttribute("jobCard", jobCard);
                request.setAttribute("vehicles", vehicleService.getAllVehicles());
                request.setAttribute("services", serviceCatalogService.getAllServices());
                request.setAttribute("errorMessage", "Failed to create job card. Please check the form and try again.");
                request.getRequestDispatcher("/WEB-INF/views/jobcards-add.jsp").forward(request, response);
            }
            
        } else if (pathInfo.startsWith("/edit/")) {
            // Update existing job card
            try {
                int jobCardId = Integer.parseInt(pathInfo.substring(6));
                JobCard jobCard = extractJobCardFromRequest(request);
                jobCard.setJobCardId(jobCardId);
                
                if (jobCardService.updateJobCard(jobCard)) {
                    // Update job card status if provided
                    String status = request.getParameter("status");
                    if (status != null && !status.isEmpty()) {
                        jobCardService.updateJobCardStatus(jobCardId, status);
                    }
                    
                    response.sendRedirect(request.getContextPath() + "/jobcards/view/" + jobCardId);
                } else {
                    request.setAttribute("jobCard", jobCard);
                    request.setAttribute("vehicle", vehicleService.getVehicleById(jobCard.getVehicleId()));
                    request.setAttribute("services", serviceCatalogService.getAllServices());
                    request.setAttribute("jobServices", jobCardService.getServicesForJobCard(jobCardId));
                    request.setAttribute("errorMessage", "Failed to update job card. Please check the form and try again.");
                    request.getRequestDispatcher("/WEB-INF/views/jobcards-edit.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid job card ID");
            }
            
        } else if (pathInfo.startsWith("/cancel/")) {
            // Cancel job card
            try {
                int jobCardId = Integer.parseInt(pathInfo.substring(8));
                
                if (jobCardService.updateJobCardStatus(jobCardId, "CANCELLED")) {
                    response.sendRedirect(request.getContextPath() + "/jobcards");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to cancel job card");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid job card ID");
            }
            
        } else if (pathInfo.startsWith("/complete/")) {
            // Mark job card as completed
            try {
                int jobCardId = Integer.parseInt(pathInfo.substring(10));
                
                if (jobCardService.updateJobCardStatus(jobCardId, "COMPLETED")) {
                    // Update vehicle's last service date
                    JobCard jobCard = jobCardService.getJobCardById(jobCardId);
                    if (jobCard != null) {
                        vehicleService.updateLastServiceDate(jobCard.getVehicleId(), LocalDateTime.now().toLocalDate());
                    }
                    
                    response.sendRedirect(request.getContextPath() + "/jobcards/view/" + jobCardId);
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to complete job card");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid job card ID");
            }
            
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * Extract job card data from request parameters
     * @param request HttpServletRequest containing form data
     * @return JobCard object populated with form data
     */
    private JobCard extractJobCardFromRequest(HttpServletRequest request) {
        JobCard jobCard = new JobCard();
        
        try {
            jobCard.setVehicleId(Integer.parseInt(request.getParameter("vehicleId")));
        } catch (NumberFormatException e) {
            // Handle invalid vehicle ID
        }
        
        jobCard.setCustomerComplaints(request.getParameter("customerComplaints"));
        jobCard.setTechnicianNotes(request.getParameter("technicianNotes"));
        
        return jobCard;
    }
}
