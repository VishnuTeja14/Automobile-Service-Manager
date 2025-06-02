package com.automobile.service.controller;

import com.automobile.service.model.Vehicle;
import com.automobile.service.service.VehicleService;
import com.automobile.service.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * VehicleController servlet for handling vehicle-related HTTP requests
 */
@WebServlet("/vehicles/*")
public class VehicleController extends HttpServlet {
    
    private VehicleService vehicleService;
    private CustomerService customerService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        vehicleService = new VehicleService();
        customerService = new CustomerService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // List all vehicles
            List<Vehicle> vehicles = vehicleService.getAllVehicles();
            request.setAttribute("vehicles", vehicles);
            request.getRequestDispatcher("/WEB-INF/views/vehicles.jsp").forward(request, response);
            
        } else if (pathInfo.equals("/add")) {
            // Show add vehicle form
            request.setAttribute("customers", customerService.getAllCustomers());
            request.getRequestDispatcher("/WEB-INF/views/vehicles-add.jsp").forward(request, response);
            
        } else if (pathInfo.startsWith("/edit/")) {
            // Show edit vehicle form
            try {
                int vehicleId = Integer.parseInt(pathInfo.substring(6));
                Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
                
                if (vehicle != null) {
                    request.setAttribute("vehicle", vehicle);
                    request.setAttribute("customers", customerService.getAllCustomers());
                    request.getRequestDispatcher("/WEB-INF/views/vehicles-edit.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vehicle not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid vehicle ID");
            }
            
        } else if (pathInfo.startsWith("/view/")) {
            // Show vehicle details
            try {
                int vehicleId = Integer.parseInt(pathInfo.substring(6));
                Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
                
                if (vehicle != null) {
                    request.setAttribute("vehicle", vehicle);
                    request.setAttribute("customer", customerService.getCustomerById(vehicle.getCustomerId()));
                    request.getRequestDispatcher("/WEB-INF/views/vehicles-view.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vehicle not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid vehicle ID");
            }
            
        } else if (pathInfo.startsWith("/customer/")) {
            // List vehicles for a specific customer
            try {
                int customerId = Integer.parseInt(pathInfo.substring(10));
                List<Vehicle> vehicles = vehicleService.getVehiclesByCustomerId(customerId);
                request.setAttribute("vehicles", vehicles);
                request.setAttribute("customer", customerService.getCustomerById(customerId));
                request.getRequestDispatcher("/WEB-INF/views/vehicles-by-customer.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid customer ID");
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
            // Add new vehicle
            Vehicle vehicle = extractVehicleFromRequest(request);
            
            if (vehicleService.addVehicle(vehicle)) {
                response.sendRedirect(request.getContextPath() + "/vehicles");
            } else {
                request.setAttribute("vehicle", vehicle);
                request.setAttribute("customers", customerService.getAllCustomers());
                request.setAttribute("errorMessage", "Failed to add vehicle. Please check the form and try again.");
                request.getRequestDispatcher("/WEB-INF/views/vehicles-add.jsp").forward(request, response);
            }
            
        } else if (pathInfo.startsWith("/edit/")) {
            // Update existing vehicle
            try {
                int vehicleId = Integer.parseInt(pathInfo.substring(6));
                Vehicle vehicle = extractVehicleFromRequest(request);
                vehicle.setVehicleId(vehicleId);
                
                if (vehicleService.updateVehicle(vehicle)) {
                    response.sendRedirect(request.getContextPath() + "/vehicles");
                } else {
                    request.setAttribute("vehicle", vehicle);
                    request.setAttribute("customers", customerService.getAllCustomers());
                    request.setAttribute("errorMessage", "Failed to update vehicle. Please check the form and try again.");
                    request.getRequestDispatcher("/WEB-INF/views/vehicles-edit.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid vehicle ID");
            }
            
        } else if (pathInfo.startsWith("/delete/")) {
            // Delete vehicle
            try {
                int vehicleId = Integer.parseInt(pathInfo.substring(8));
                
                if (vehicleService.deleteVehicle(vehicleId)) {
                    response.sendRedirect(request.getContextPath() + "/vehicles");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete vehicle");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid vehicle ID");
            }
            
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * Extract vehicle data from request parameters
     * @param request HttpServletRequest containing form data
     * @return Vehicle object populated with form data
     */
    private Vehicle extractVehicleFromRequest(HttpServletRequest request) {
        Vehicle vehicle = new Vehicle();
        
        try {
            vehicle.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
        } catch (NumberFormatException e) {
            // Handle invalid customer ID
        }
        
        vehicle.setMake(request.getParameter("make"));
        vehicle.setModel(request.getParameter("model"));
        
        try {
            vehicle.setYear(Integer.parseInt(request.getParameter("year")));
        } catch (NumberFormatException e) {
            // Handle invalid year
        }
        
        vehicle.setLicensePlate(request.getParameter("licensePlate"));
        vehicle.setVin(request.getParameter("vin"));
        vehicle.setColor(request.getParameter("color"));
        
        try {
            vehicle.setMileage(Integer.parseInt(request.getParameter("mileage")));
        } catch (NumberFormatException e) {
            // Handle invalid mileage
        }
        
        return vehicle;
    }
}
