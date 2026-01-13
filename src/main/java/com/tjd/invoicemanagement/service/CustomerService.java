package com.tjd.invoicemanagement.service;

import com.tjd.invoicemanagement.model.Customer;
import com.tjd.invoicemanagement.model.PackagePlan;
import com.tjd.invoicemanagement.model.Quarter;
import com.tjd.invoicemanagement.model.StatusLog;
import com.tjd.invoicemanagement.repository.CustomerRepository;
import com.tjd.invoicemanagement.repository.StatusLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepo;
    
    @Autowired
    private StatusLogRepository statusLogRepo;

    // Constructor Injection (Lombok မသုံးဘဲ ရေးခြင်း)
    public CustomerService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    // ၁။ Customer အားလုံးကို ယူရန် (သို့မဟုတ်) Keyword ဖြင့် ရှာရန်
    public List<Customer> getAllOrSearch(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return customerRepo.searchCustomers(keyword);
        }
        return customerRepo.findAllByOrderByIdDesc();
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        // ၁။ Database ထဲက Quarter အစစ်ကို ID နဲ့ ရှာပြီး Customer ထဲ ပြန်ထည့်ပေးပါ
        if (customer.getQuarter() != null && customer.getQuarter().getQtrId() != null) {
            Quarter managedQuarter = customerRepo.findQuarterById(customer.getQuarter().getQtrId());
            customer.setQuarter(managedQuarter); // DB ထဲက object အစစ်ဖြစ်သွားပါပြီ
        }

        // ၂။ Database ထဲက Plan အစစ်ကို ID နဲ့ ရှာပြီး Customer ထဲ ပြန်ထည့်ပေးပါ
        if (customer.getPackagePlan() != null && customer.getPackagePlan().getPlanId() != null) {
            PackagePlan managedPlan = customerRepo.findPlanById(customer.getPackagePlan().getPlanId());
            customer.setPackagePlan(managedPlan); // DB ထဲက object အစစ်ဖြစ်သွားပါပြီ
        }

        // ၃။ အားလုံး အဆင်ပြေမှ သိမ်းပါ (Transient Error မတက်တော့ပါ)
        return customerRepo.save(customer);
    }

    // ၃။ ID ဖြင့် ရှာရန်
    public Customer getById(Integer id) {
        return customerRepo.findById(id).orElse(null);
    }

    // ၄။ Status အလိုက် Filter လုပ်ရန် (ACTIVE, DISABLE, TERMINATION)
    public List<Customer> getByStatus(String status) {
        return customerRepo.findByStatus(status);
    }

    // ၅။ Calendar Year အလိုက် လအလိုက် Customer Count ထုတ်ရန်
    public List<Map<String, Object>> getYearlyStats(int year) {
        return customerRepo.getMonthlyCustomerCount(year);
    }

    // ၆။ Date Range အလိုက် Customer ရှာရန်
    public List<Customer> getCustomersBetweenDates(LocalDate start, LocalDate end) {
        return customerRepo.findByInstallDateBetween(start, end);
    }

    // ၇။ ရပ်ကွက်အလိုက် အရေအတွက် သိရန်
    public List<Map<String, Object>> getQuarterStats() {
        return customerRepo.getCountByQuarter();
    }

    // ၈။ Customer ကို ဖျက်ရန်
    @Transactional
    public void deleteCustomer(Integer id) {
        customerRepo.deleteById(id);
    }
    
    
    public Map<String, Long> getStatusSummary() {
        List<Object[]> results = customerRepo.getStatusCounts();
        Map<String, Long> summary = new HashMap<>();
        for (Object[] result : results) {
            summary.put(result[0].toString(), (Long) result[1]);
        }
        return summary;
    }
    
 // ၁။ Quarter အတွက် (ဘရိုရေးထားပြီးသား)
    public List<Map<String, Object>> getQuarterDistribution() {
        return customerRepo.getQuarterDistribution().stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", result[0]);  // Quarter နာမည်ကို "name" ဆိုတဲ့ key ထဲထည့်တယ်
            map.put("count", result[1]); // အရေအတွက်ကို "count" ဆိုတဲ့ key ထဲထည့်တယ်
            return map;
        }).collect(Collectors.toList());
    }

    // ၂။ Package Plan အတွက် (ဒါလေး ထပ်ဖြည့်ပေးပါ)
    public List<Map<String, Object>> getPlanDistribution() {
        return customerRepo.getPlanDistribution().stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", result[0]);  // Plan နာမည် (B2B, Home...) ကို "name" ထဲထည့်တယ်
            map.put("count", result[1]); // အဲ့ဒီ plan ယူထားတဲ့ user count ကို "count" ထဲထည့်တယ်
            return map;
        }).collect(Collectors.toList());
    }

    public List<Customer> getExpiringSoon() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        return customerRepo.findExpiringSoon(today, nextWeek);
    }
    
    
    @Transactional
    public Customer updateCustomerStatus(Integer id, String newStatus, String remark) {
        // ၁။ လက်ရှိ Customer ကို ရှာမယ်
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // ၂။ StatusLog ထဲမှာ ရာဇဝင် သိမ်းမယ်
        StatusLog log = new StatusLog(
                customer, 
                customer.getStatus(), // အရင် Status
                newStatus,            // အခု Status အသစ်
                remark
        );
        statusLogRepo.save(log);

        // ၃။ Customer Table ထဲမှာ Status ကို Update လုပ်မယ်
        customer.setStatus(newStatus);
        return customerRepo.save(customer);
    }
    
    public List<Customer> getCustomersByMonth(int year, int month) {
        // Repository ထဲက findByInstallMonth ကို လှမ်းခေါ်တာပါ
        return customerRepo.findByInstallMonth(year, month);
    }
    
    public List<Map<String, Object>> getDnsnDist() {
        List<Object[]> results = customerRepo.getDnsnDistribution();
        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", row[0]);
            map.put("count", row[1]);
            return map;
        }).collect(Collectors.toList());
    }

    // ၂။ DNSN တစ်ခုချင်းစီအလိုက် လူစာရင်း (Modal အတွက်)
    public List<Customer> getCustomersByDnsn(String dnsn) {
        return customerRepo.findByDnsn(dnsn); 
    }
    
   
    
}