package com.automobile.service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Billing model class representing a bill in the Automobile Service Manager
 */
public class Billing {
    private int billId;
    private int jobCardId;
    private LocalDateTime billDate;
    private BigDecimal totalServiceCost;
    private BigDecimal totalPartsCost;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private BigDecimal grandTotal;
    private String paymentStatus; // PENDING, PARTIAL, PAID
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private String notes;
    
    // Default constructor
    public Billing() {
    }
    
    // Parameterized constructor
    public Billing(int jobCardId, BigDecimal totalServiceCost, BigDecimal totalPartsCost, 
                  BigDecimal taxAmount, BigDecimal discountAmount, BigDecimal grandTotal) {
        this.jobCardId = jobCardId;
        this.totalServiceCost = totalServiceCost;
        this.totalPartsCost = totalPartsCost;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.grandTotal = grandTotal;
        this.paymentStatus = "PENDING";
        this.billDate = LocalDateTime.now();
    }
    
    // Full constructor
    public Billing(int billId, int jobCardId, LocalDateTime billDate, 
                  BigDecimal totalServiceCost, BigDecimal totalPartsCost, 
                  BigDecimal taxAmount, BigDecimal discountAmount, 
                  BigDecimal grandTotal, String paymentStatus, 
                  String paymentMethod, LocalDateTime paymentDate, String notes) {
        this.billId = billId;
        this.jobCardId = jobCardId;
        this.billDate = billDate;
        this.totalServiceCost = totalServiceCost;
        this.totalPartsCost = totalPartsCost;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.grandTotal = grandTotal;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.notes = notes;
    }
    
    // Getters and Setters
    public int getBillId() {
        return billId;
    }
    
    public void setBillId(int billId) {
        this.billId = billId;
    }
    
    public int getJobCardId() {
        return jobCardId;
    }
    
    public void setJobCardId(int jobCardId) {
        this.jobCardId = jobCardId;
    }
    
    public LocalDateTime getBillDate() {
        return billDate;
    }
    
    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }
    
    public BigDecimal getTotalServiceCost() {
        return totalServiceCost;
    }
    
    public void setTotalServiceCost(BigDecimal totalServiceCost) {
        this.totalServiceCost = totalServiceCost;
    }
    
    public BigDecimal getTotalPartsCost() {
        return totalPartsCost;
    }
    
    public void setTotalPartsCost(BigDecimal totalPartsCost) {
        this.totalPartsCost = totalPartsCost;
    }
    
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }
    
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }
    
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }
    
    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
    
    public BigDecimal getGrandTotal() {
        return grandTotal;
    }
    
    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString() {
        return "Billing{" +
                "billId=" + billId +
                ", jobCardId=" + jobCardId +
                ", billDate=" + billDate +
                ", totalServiceCost=" + totalServiceCost +
                ", totalPartsCost=" + totalPartsCost +
                ", taxAmount=" + taxAmount +
                ", discountAmount=" + discountAmount +
                ", grandTotal=" + grandTotal +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentDate=" + paymentDate +
                ", notes='" + notes + '\'' +
                '}';
    }
}
