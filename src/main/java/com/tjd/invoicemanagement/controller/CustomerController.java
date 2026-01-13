package com.tjd.invoicemanagement.controller;

import com.tjd.invoicemanagement.model.Customer;
import com.tjd.invoicemanagement.service.CustomerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) { this.service = service; }

    // Search ပါလုပ်နိုင်သော Get All
    @GetMapping
    public List<Customer> getAll(@RequestParam(required = false) String search) {
        return service.getAllOrSearch(search);
    }
    
    


    @PostMapping
    public Customer create(@RequestBody Customer customer) { return service.saveCustomer(customer); }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable Integer id) { return service.getById(id); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { service.deleteCustomer(id); }

    // Status Filter (ACTIVE, DISABLE, TERMINATION)
    @GetMapping("/status/{status}")
    public List<Customer> getByStatus(@PathVariable String status) {
        return service.getByStatus(status);
    }

    // Calendar Year Monthly Statistics
    @GetMapping("/stats/yearly/{year}")
    public List<Map<String, Object>> getYearlyStats(@PathVariable int year) {
        return service.getYearlyStats(year);
    }

    // Quarter Statistics
    @GetMapping("/stats/quarter")
    public List<Map<String, Object>> getQuarterStats() {
        return service.getQuarterStats();
    }
    
    @GetMapping("/search-dates")
    public List<Customer> getByDateRange(@RequestParam String start, @RequestParam String end) {
        // Frontend ကလာတဲ့ YYYY-MM-DD string ကို LocalDate အဖြစ် ပြောင်းတာပါ
        java.time.LocalDate startDate = java.time.LocalDate.parse(start);
        java.time.LocalDate endDate = java.time.LocalDate.parse(end);
        return service.getCustomersBetweenDates(startDate, endDate);
    }
    
    @GetMapping("/stats/summary")
    public Map<String, Long> getStatusSummary() {
        return service.getStatusSummary();
    }
    
    @GetMapping("/stats/quarter-dist")
    public List<Map<String, Object>> getQuarterDist() {
        return service.getQuarterDistribution();
    }

    @GetMapping("/stats/plan-dist")
    public List<Map<String, Object>> getPlanDist() {
        return service.getPlanDistribution();
    }

    // ၃။ Expiring Soon (Next 7 Days)
    @GetMapping("/stats/expiring-soon")
    public List<Customer> getExpiringSoon() {
        return service.getExpiringSoon();
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Integer id,
            @RequestParam String newStatus,
            @RequestParam String remark) {
        try {
            // Service ထဲက updateCustomerStatus ကို လှမ်းခေါ်ပါတယ်
            Customer updatedCustomer = service.updateCustomerStatus(id, newStatus, remark);
            return ResponseEntity.ok(updatedCustomer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/stats/monthly-users")
    public List<Customer> getMonthlyUsers(@RequestParam int year, @RequestParam int month) {
        return service.getCustomersByMonth(year, month);
    }
    
    
    @GetMapping("/stats/dnsn-dist")
    public List<Map<String, Object>> getDnsnDist() {
        return service.getDnsnDist();
    }

    @GetMapping("/stats/dnsn-users")
    public List<Customer> getCustomersByDnsn(@RequestParam String dnsn) {
        return service.getCustomersByDnsn(dnsn);
    }
    
}