package com.tjd.invoicemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjd.invoicemanagement.model.Relocation;

public interface RelocationRepository extends JpaRepository<Relocation, Integer> {
    // Customer ရဲ့ ID (Manual ID) နဲ့ History တစ်ခုလုံးကို ရက်စွဲအလိုက် စီပြီး ရှာရန်
    List<Relocation> findByCustomer_CustomerIdOrderByRelocationDateDesc(String customerId);
    
   
}