package com.tjd.invoicemanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "status_logs")
public class StatusLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String oldStatus;
    private String newStatus;
    private LocalDateTime changeDate;
    private String remark;

    public StatusLog() {}

 // Constructor ကို parameter ၅ ခု လက်ခံနိုင်အောင် ပြင်လိုက်ပါပြီ
 public StatusLog(Customer customer, String oldStatus, String newStatus, String remark, LocalDateTime changeDate) {
     this.customer = customer;
     this.oldStatus = oldStatus;
     this.newStatus = newStatus;
     this.remark = remark;
     // အပြင်က ရက်စွဲ ပါလာရင် ယူမယ်၊ မပါရင် အခုအချိန်ကို ယူမယ်
     this.changeDate = (changeDate != null) ? changeDate : LocalDateTime.now();
 }

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	public LocalDateTime getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(LocalDateTime changeDate) {
		this.changeDate = changeDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    // Getters and Setters ...
    
    
    
}