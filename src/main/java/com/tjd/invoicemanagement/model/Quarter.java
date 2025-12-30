package com.tjd.invoicemanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "quarters")
public class Quarter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qtrId;

    @Column(nullable = false, unique = true)
    private String qtrName;

    public Quarter() {}

    public Integer getQtrId() { return qtrId; }
    public void setQtrId(Integer qtrId) { this.qtrId = qtrId; }
    public String getQtrName() { return qtrName; }
    public void setQtrName(String qtrName) { this.qtrName = qtrName; }
}