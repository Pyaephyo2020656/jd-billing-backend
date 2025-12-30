package com.tjd.invoicemanagement.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "relocations")
public class Relocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer relocationId;

    // ဘယ် Customer လဲဆိုတာ သိဖို့ (Customer Table နဲ့ ချိတ်ဆက်မှု)
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // နေရာဟောင်းက အချက်အလက်များကို သိမ်းဆည်းရန် field များ
    @Column(columnDefinition = "TEXT")
    private String oldAddress;

    @ManyToOne
    @JoinColumn(name = "old_qtr_id")
    private Quarter oldQuarter;

    private String oldGps;
    private String oldDnsn;

    // Relocation အချက်အလက်များ
    private LocalDate relocationDate;
    private String remark;

    public Relocation() {}

    // --- Getters and Setters ---

    public Integer getRelocationId() { return relocationId; }
    public void setRelocationId(Integer relocationId) { this.relocationId = relocationId; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public String getOldAddress() { return oldAddress; }
    public void setOldAddress(String oldAddress) { this.oldAddress = oldAddress; }

    public Quarter getOldQuarter() { return oldQuarter; }
    public void setOldQuarter(Quarter oldQuarter) { this.oldQuarter = oldQuarter; }

    public String getOldGps() { return oldGps; }
    public void setOldGps(String oldGps) { this.oldGps = oldGps; }

    public String getOldDnsn() { return oldDnsn; }
    public void setOldDnsn(String oldDnsn) { this.oldDnsn = oldDnsn; }

    public LocalDate getRelocationDate() { return relocationDate; }
    public void setRelocationDate(LocalDate relocationDate) { this.relocationDate = relocationDate; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}