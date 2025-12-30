package com.tjd.invoicemanagement.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String customerId; // Manual Input ID

    private String name;
    private String primaryPhone;
    private String secondaryPhone;
    private String gpsCoords;
    private String dnsn;
    private String onuSerial;
    
    @Column(columnDefinition = "TEXT")
    private String address;

    private String status; // ACTIVE, DISABLE, TERMINATION
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate installDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "qtr_id")
    private Quarter quarter;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private PackagePlan packagePlan;

    public Customer() {}

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPrimaryPhone() { return primaryPhone; }
    public void setPrimaryPhone(String primaryPhone) { this.primaryPhone = primaryPhone; }
    public String getSecondaryPhone() { return secondaryPhone; }
    public void setSecondaryPhone(String secondaryPhone) { this.secondaryPhone = secondaryPhone; }
    public String getGpsCoords() { return gpsCoords; }
    public void setGpsCoords(String gpsCoords) { this.gpsCoords = gpsCoords; }
    public String getDnsn() { return dnsn; }
    public void setDnsn(String dnsn) { this.dnsn = dnsn; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getInstallDate() { return installDate; }
    public void setInstallDate(LocalDate installDate) { this.installDate = installDate; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public Quarter getQuarter() { return quarter; }
    public void setQuarter(Quarter quarter) { this.quarter = quarter; }
    public PackagePlan getPackagePlan() { return packagePlan; }
    public void setPackagePlan(PackagePlan packagePlan) { this.packagePlan = packagePlan; }
    public String getOnuSerial() { return onuSerial; }
    public void setOnuSerial(String onuSerial) { this.onuSerial = onuSerial; }
}