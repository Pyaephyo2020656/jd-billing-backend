
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
}

