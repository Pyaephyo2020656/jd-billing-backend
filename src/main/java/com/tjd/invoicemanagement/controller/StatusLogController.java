
package com.tjd.invoicemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.tjd.invoicemanagement.model.StatusLog;
import com.tjd.invoicemanagement.repository.StatusLogRepository;

@RestController
@RequestMapping("/api/status-logs")
@CrossOrigin(origins = "*")
public class StatusLogController {

    @Autowired
    private StatusLogRepository statusLogRepo;

    @GetMapping("/all")
    public List<StatusLog> getAllLogs(@RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            // Search ပါရင် ရှာပေးမယ်
            return statusLogRepo.findAllWithSearch(search);
        }
        // Search မပါရင် အကုန်ထုတ်ပေးမယ်
        return statusLogRepo.findAllByOrderByChangeDateDesc();
    }

    // Customer တစ်ယောက်ချင်းစီအတွက် သီးသန့်ယူရန် (လိုအပ်လျှင် သုံးဖို့)
    @GetMapping("/customer/{id}")
    public List<StatusLog> getLogsByCustomerId(@PathVariable Integer id) {
        return statusLogRepo.findByCustomer_IdOrderByChangeDateDesc(id);
    }
    
    
    @org.springframework.web.bind.annotation.PutMapping("/{id}")
    public org.springframework.http.ResponseEntity<?> updateLog(
            @PathVariable Integer id, 
            @org.springframework.web.bind.annotation.RequestBody java.util.Map<String, String> payload) {
        try {
            return statusLogRepo.findById(id).map(log -> {
                // Remark ကို ပြင်မယ်
                log.setRemark(payload.get("remark"));
                
                
                if (payload.get("changeDate") != null) {
                    java.time.LocalDate date = java.time.LocalDate.parse(payload.get("changeDate"));
                    log.setChangeDate(date.atStartOfDay());
                }
                
                statusLogRepo.save(log);
                return org.springframework.http.ResponseEntity.ok(log);
            }).orElse(org.springframework.http.ResponseEntity.notFound().build());
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // ၂။ Log ကို ဖျက်ရန် (DELETE /api/status-logs/{id})
    @org.springframework.web.bind.annotation.DeleteMapping("/{id}")
    public org.springframework.http.ResponseEntity<?> deleteLog(@PathVariable Integer id) {
        try {
            if (statusLogRepo.existsById(id)) {
                statusLogRepo.deleteById(id);
                return org.springframework.http.ResponseEntity.ok().build();
            }
            return org.springframework.http.ResponseEntity.notFound().build();
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
    

    
}

