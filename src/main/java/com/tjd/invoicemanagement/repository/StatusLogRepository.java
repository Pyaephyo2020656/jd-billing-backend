package com.tjd.invoicemanagement.repository;

import com.tjd.invoicemanagement.model.StatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatusLogRepository extends JpaRepository<StatusLog, Integer> {
    // Customer တစ်ယောက်ချင်းစီရဲ့ Status ရာဇဝင်ကို ပြန်ကြည့်ရန်
    List<StatusLog> findByCustomer_IdOrderByChangeDateDesc(Integer customerId);
    
 // Customer Name သို့မဟုတ် Customer ID (Manual ID) ဖြင့် ရှာဖွေရန်
    @Query("SELECT s FROM StatusLog s WHERE " +
           "LOWER(s.customer.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.customer.customerId) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "ORDER BY s.changeDate DESC")
    List<StatusLog> findAllWithSearch(@Param("search") String search);

    // Log အားလုံးကို ရက်စွဲအလိုက် စီထုတ်ရန်
    List<StatusLog> findAllByOrderByChangeDateDesc();
}
