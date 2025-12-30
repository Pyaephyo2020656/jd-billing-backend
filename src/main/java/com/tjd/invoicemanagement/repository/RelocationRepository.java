package com.tjd.invoicemanagement.repository;

import com.tjd.invoicemanagement.model.Relocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface RelocationRepository extends JpaRepository<Relocation, Integer> {
    // Customer ရဲ့ ID (Manual ID) နဲ့ History တစ်ခုလုံးကို ရက်စွဲအလိုက် စီပြီး ရှာရန်
    List<Relocation> findByCustomer_CustomerIdOrderByRelocationDateDesc(String customerId);
    
   
}