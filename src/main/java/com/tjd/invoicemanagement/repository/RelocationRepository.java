package com.tjd.invoicemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tjd.invoicemanagement.model.Relocation;

public interface RelocationRepository extends JpaRepository<Relocation, Integer> {
    // Customer ရဲ့ ID (Manual ID) နဲ့ History တစ်ခုလုံးကို ရက်စွဲအလိုက် စီပြီး ရှာရန်
    List<Relocation> findByCustomer_CustomerIdOrderByRelocationDateDesc(String customerId);
    
    
    @Query("SELECT r FROM Relocation r WHERE " +
    	       "r.customer.customerId LIKE %:searchTerm% OR " +
    	       "LOWER(r.customer.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
    	       "ORDER BY r.relocationDate DESC")
    	List<Relocation> searchByCustomer(@Param("searchTerm") String searchTerm);
    
   
}