package com.tjd.invoicemanagement.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;

    @Column(unique = true, nullable = false)
    private String invoiceNo;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private LocalDate invoiceDate;
    private Double subTotal;
    private Double discountAmount;
    private Double totalAmount;
    private String status; // UNPAID, PAID
    private String remark;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<InvoiceItem> items = new ArrayList<>();
    
    
    @Transient 
    private LocalDate nextExpiryDate;

    public Invoice() {}
    
    // Helper Method
    public void addItem(InvoiceItem item) {
        items.add(item);
        item.setInvoice(this);
    }

    // --- Getters & Setters (Standard) ---
    public Integer getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Integer invoiceId) { this.invoiceId = invoiceId; }
    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public LocalDate getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }
    public Double getSubTotal() { return subTotal; }
    public void setSubTotal(Double subTotal) { this.subTotal = subTotal; }
    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public List<InvoiceItem> getItems() { return items; }
    public void setItems(List<InvoiceItem> items) { this.items = items; }
    
    public LocalDate getNextExpiryDate() { return nextExpiryDate; }
    public void setNextExpiryDate(LocalDate nextExpiryDate) { this.nextExpiryDate = nextExpiryDate; }
}