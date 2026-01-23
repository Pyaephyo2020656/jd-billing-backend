package com.tjd.invoicemanagement.repository;

import com.tjd.invoicemanagement.model.Customer;
import com.tjd.invoicemanagement.model.PackagePlan;
import com.tjd.invoicemanagement.model.Quarter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // ၁။ ရှာဖွေရေး Logic (CustomerId, Name, Phone, DNSN)
	@Query("SELECT c FROM Customer c WHERE c.name LIKE %:kw% OR c.customerId LIKE %:kw% OR c.dnsn LIKE %:kw% OR c.onuSerial LIKE %:kw% ORDER BY c.id DESC")
	List<Customer> searchCustomers(@Param("kw") String keyword);
	
	
	 // ၂။ All Customers ကို Descending ဖြင့်ယူရန် (Frontend .reverse() မသုံးချင်ပါက ဤသည်ကိုသုံးပါ)
    List<Customer> findAllByOrderByIdDesc();
	

    // ၂။ Status အလိုက် Filter လုပ်ရန် (ACTIVE, DISABLE, TERMINATION)
    List<Customer> findByStatus(String status);

    // ၃။ Calendar Year အလိုက် တစ်လချင်းစီရဲ့ Customer အသစ်စာရင်း (Count)
    // ဥပမာ - [{month: 1, count: 10}, {month: 2, count: 5}] မျိုး ပြန်ပေးမှာပါ
    @Query("SELECT MONTH(c.installDate) as month, COUNT(c) as count " +
           "FROM Customer c " +
           "WHERE YEAR(c.installDate) = :year " +
           "GROUP BY MONTH(c.installDate)")
    List<Map<String, Object>> getMonthlyCustomerCount(@Param("year") int year);

    // ၄။ Date Range အလိုက် Customer စာရင်းထုတ်ရန်
    @Query("SELECT c FROM Customer c WHERE c.installDate BETWEEN :startDate AND :endDate")
    List<Customer> findByInstallDateBetween(java.time.LocalDate startDate, java.time.LocalDate endDate);
    
    // ၅။ Quarter အလိုက် Customer Count (ဘယ်ရပ်ကွက်မှာ ဘယ်နှစ်ယောက်လဲ)
    @Query("SELECT c.quarter.qtrName as quarter, COUNT(c) as count FROM Customer c GROUP BY c.quarter")
    List<Map<String, Object>> getCountByQuarter();
    
    @Query("SELECT q FROM Quarter q WHERE q.qtrId = :id")
    Quarter findQuarterById(@Param("id") Integer id);

    // p.id မဟုတ်ဘဲ p.planId ဖြစ်ရပါမည်
    @Query("SELECT p FROM PackagePlan p WHERE p.planId = :id")
    PackagePlan findPlanById(@Param("id") Integer id);
    
    @Query("SELECT status, COUNT(c) FROM Customer c GROUP BY status")
    List<Object[]> getStatusCounts();
    
 // ၁။ Quarter အလိုက် စာရင်း
    @Query("SELECT c.quarter.qtrName, COUNT(c) FROM Customer c GROUP BY c.quarter.qtrName")
    List<Object[]> getQuarterDistribution();

    @Query("SELECT CONCAT(c.packagePlan.planName, ' (', c.packagePlan.bandwidth, ')'), COUNT(c) " +
    	       "FROM Customer c " +
    	       "GROUP BY c.packagePlan.planName, c.packagePlan.bandwidth")
    	List<Object[]> getPlanDistribution();

    // ၃။ သက်တမ်းကုန်တော့မည့်သူများ (နောက် ၇ ရက်အတွင်း)
    @Query("SELECT c FROM Customer c WHERE c.expiryDate BETWEEN :start AND :end ORDER BY c.expiryDate ASC")
    List<Customer> findExpiringSoon(@Param("start") LocalDate start, @Param("end") LocalDate end);
    
    
    @Query("SELECT c FROM Customer c WHERE YEAR(c.installDate) = :year AND MONTH(c.installDate) = :month")
    List<Customer> findByInstallMonth(@Param("year") int year, @Param("month") int month);
    
    
    @Query("SELECT c.dnsn, COUNT(c) FROM Customer c WHERE c.dnsn IS NOT NULL GROUP BY c.dnsn")
    List<Object[]> getDnsnDistribution();
    
    List<Customer> findByDnsn(String dnsn);
    
    
    List<Customer> findByQuarter_QtrName(String qtrName);


 @Query("SELECT c FROM Customer c WHERE CONCAT(c.packagePlan.planName, ' (', c.packagePlan.bandwidth, ')') = :planName")
 List<Customer> findByPlanFullDisplayName(@Param("planName") String planName);
    
    

}