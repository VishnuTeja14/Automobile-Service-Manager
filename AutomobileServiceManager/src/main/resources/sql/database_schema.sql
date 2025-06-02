-- Database Schema for Automobile Service Manager

-- Drop database if exists (for clean setup)
DROP DATABASE IF EXISTS automobile_service;

-- Create database
CREATE DATABASE automobile_service;

-- Use the database
USE automobile_service;

-- Create Customers table
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    address VARCHAR(255),
    city VARCHAR(50),
    state VARCHAR(50),
    zip_code VARCHAR(10),
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_email (email),
    UNIQUE KEY unique_phone (phone)
);

-- Create Vehicles table
CREATE TABLE vehicles (
    vehicle_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    license_plate VARCHAR(20) NOT NULL,
    vin VARCHAR(17),
    color VARCHAR(30),
    mileage INT,
    last_service_date DATE,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
    UNIQUE KEY unique_license_plate (license_plate),
    UNIQUE KEY unique_vin (vin)
);

-- Create Services table (service catalog)
CREATE TABLE services (
    service_id INT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(100) NOT NULL,
    description TEXT,
    standard_price DECIMAL(10, 2) NOT NULL,
    estimated_hours DECIMAL(4, 2)
);

-- Create Job Cards table
CREATE TABLE job_cards (
    job_card_id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id INT NOT NULL,
    open_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    close_date TIMESTAMP NULL,
    status ENUM('OPEN', 'IN_PROGRESS', 'COMPLETED', 'DELIVERED', 'CANCELLED') DEFAULT 'OPEN',
    technician_notes TEXT,
    customer_complaints TEXT,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id) ON DELETE CASCADE
);

-- Create Job Services table (services performed in a job card)
CREATE TABLE job_services (
    job_service_id INT AUTO_INCREMENT PRIMARY KEY,
    job_card_id INT NOT NULL,
    service_id INT NOT NULL,
    actual_price DECIMAL(10, 2) NOT NULL,
    actual_hours DECIMAL(4, 2),
    notes TEXT,
    status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED') DEFAULT 'PENDING',
    FOREIGN KEY (job_card_id) REFERENCES job_cards(job_card_id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services(service_id)
);

-- Create Parts table
CREATE TABLE parts (
    part_id INT AUTO_INCREMENT PRIMARY KEY,
    part_name VARCHAR(100) NOT NULL,
    part_number VARCHAR(50),
    description TEXT,
    unit_price DECIMAL(10, 2) NOT NULL,
    quantity_in_stock INT DEFAULT 0
);

-- Create Parts Used table
CREATE TABLE parts_used (
    parts_used_id INT AUTO_INCREMENT PRIMARY KEY,
    job_service_id INT NOT NULL,
    part_id INT NOT NULL,
    quantity INT NOT NULL,
    price_per_unit DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (job_service_id) REFERENCES job_services(job_service_id) ON DELETE CASCADE,
    FOREIGN KEY (part_id) REFERENCES parts(part_id)
);

-- Create Billing table
CREATE TABLE billing (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    job_card_id INT NOT NULL,
    bill_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_service_cost DECIMAL(10, 2) NOT NULL,
    total_parts_cost DECIMAL(10, 2) NOT NULL,
    tax_amount DECIMAL(10, 2) NOT NULL,
    discount_amount DECIMAL(10, 2) DEFAULT 0,
    grand_total DECIMAL(10, 2) NOT NULL,
    payment_status ENUM('PENDING', 'PARTIAL', 'PAID') DEFAULT 'PENDING',
    payment_method VARCHAR(50),
    payment_date TIMESTAMP NULL,
    notes TEXT,
    FOREIGN KEY (job_card_id) REFERENCES job_cards(job_card_id) ON DELETE CASCADE
);

-- Create Users table for authentication (optional)
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'MANAGER', 'TECHNICIAN', 'RECEPTIONIST') NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    last_login TIMESTAMP NULL,
    UNIQUE KEY unique_username (username),
    UNIQUE KEY unique_email (email)
);

-- Insert some sample services
INSERT INTO services (service_name, description, standard_price, estimated_hours) VALUES
('Oil Change', 'Change engine oil and filter', 49.99, 0.5),
('Brake Inspection', 'Inspect brake pads, rotors, and fluid', 29.99, 0.5),
('Tire Rotation', 'Rotate tires to ensure even wear', 19.99, 0.5),
('Engine Tune-up', 'Comprehensive engine maintenance', 199.99, 2.0),
('Air Filter Replacement', 'Replace engine air filter', 24.99, 0.25),
('Wheel Alignment', 'Align wheels to manufacturer specifications', 89.99, 1.0),
('Battery Replacement', 'Replace vehicle battery', 149.99, 0.5),
('Brake Pad Replacement', 'Replace front or rear brake pads', 199.99, 1.5),
('Coolant Flush', 'Flush and replace engine coolant', 89.99, 1.0),
('Transmission Service', 'Flush and replace transmission fluid', 149.99, 1.5);

-- Insert sample parts
INSERT INTO parts (part_name, part_number, description, unit_price, quantity_in_stock) VALUES
('Oil Filter', 'OF-1234', 'Standard oil filter for most vehicles', 9.99, 50),
('Engine Oil (1 Quart)', 'EO-5678', 'Synthetic blend engine oil', 7.99, 100),
('Air Filter', 'AF-9012', 'Engine air filter', 14.99, 30),
('Brake Pads (Front)', 'BP-3456', 'Front brake pads set', 49.99, 20),
('Brake Pads (Rear)', 'BP-7890', 'Rear brake pads set', 44.99, 20),
('Wiper Blades', 'WB-1234', 'Standard wiper blades (pair)', 24.99, 40),
('Battery', 'BAT-5678', 'Standard vehicle battery', 99.99, 15),
('Spark Plugs (set of 4)', 'SP-9012', 'Iridium spark plugs', 39.99, 25),
('Coolant (1 Gallon)', 'CL-3456', 'Engine coolant/antifreeze', 19.99, 30),
('Transmission Fluid (1 Quart)', 'TF-7890', 'Automatic transmission fluid', 8.99, 50);
