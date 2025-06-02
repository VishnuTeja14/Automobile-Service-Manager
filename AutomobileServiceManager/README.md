# Automobile Service Manager

A comprehensive system for managing automobile service operations, built using Java, JDBC, SQL, HTML, and CSS.

## Project Overview

This application streamlines the process of servicing vehicles by maintaining a centralized database of customers, their vehicles, and the services performed. This eliminates manual errors and speeds up service operations.

### Key Features

- **Customer Management**: Register and manage customer information
- **Vehicle Management**: Track vehicle details and service history
- **Job Card Creation**: Create and manage service job cards
- **Service Tracking**: Monitor service progress and completion
- **Billing**: Generate and manage service bills

## Technology Stack

- **Backend**: Java, JDBC (Java Database Connectivity)
- **Database**: MySQL
- **Frontend**: HTML, CSS, JavaScript
- **Architecture**: MVC (Model-View-Controller)

## Project Structure

```
AutomobileServiceManager/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── automobile/
│   │   │           └── service/
│   │   │               ├── model/       # Data models
│   │   │               ├── dao/         # Data Access Objects
│   │   │               ├── service/     # Business logic
│   │   │               ├── controller/  # Servlet controllers
│   │   │               ├── util/        # Utility classes
│   │   │               └── test/        # Test classes
│   │   ├── resources/
│   │   │   └── sql/    # SQL scripts for database setup
│   │   └── webapp/
│   │       ├── css/    # Stylesheets
│   │       ├── js/     # JavaScript files
│   │       ├── images/ # Image resources
│   │       └── WEB-INF/# Web configuration files and JSPs
├── lib/                # External libraries
└── docs/               # Documentation
```

## Setup Instructions

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- MySQL Server 5.7 or higher
- Apache Tomcat 9.0 or similar servlet container
- Maven (optional, for dependency management)

### Database Setup
1. Install and start MySQL Server
2. Create a new database: `CREATE DATABASE automobile_service;`
3. Run the SQL script in `src/main/resources/sql/database_schema.sql` to set up the tables and sample data

### Application Setup
1. Clone this repository
2. Configure database connection in `com.automobile.service.util.DBConnectionUtil`
3. Deploy the application to your servlet container
4. Access the application through your web browser

## Usage Guide

### Customer Management
- Add new customers with contact information
- View and edit customer details
- Search for customers by name, phone, or email

### Vehicle Management
- Register vehicles with make, model, year, and other details
- Associate vehicles with customers
- Track vehicle service history

### Job Card Management
- Create job cards for vehicle services
- Add services and parts to job cards
- Track job status from open to completion

### Billing
- Generate bills for completed services
- Calculate service costs, parts costs, taxes, and discounts
- Track payment status

## Development

### Building from Source
1. Clone the repository
2. Configure your IDE to use the project structure
3. Set up the database as described above
4. Run the application on a servlet container

### Testing
- Unit tests are available in the `com.automobile.service.test` package
- Run tests to verify functionality of service and DAO classes

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments
- Developed as a portfolio project
- Inspired by real-world automotive service management systems
