package com.tjd.invoicemanagement.service;

import com.tjd.invoicemanagement.model.StatusLog;
import com.tjd.invoicemanagement.repository.StatusLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatusLogService {

    @Autowired
    private StatusLogRepository logRepo;

    // Log အားလုံး သို့မဟုတ် Search Term ဖြင့် ရှာရန်
    public List<StatusLog> getAllLogs(String search) {
        if (search != null && !search.isEmpty()) {
            return logRepo.findAllWithSearch(search);
        }
        return logRepo.findAllByOrderByChangeDateDesc();
    }

    // Log တစ်ခုချင်းစီကို ပြင်ရန် (Remark နှင့် Date သာ ပြင်ခွင့်ပေးခြင်း)
    @Transactional
    public StatusLog updateLog(Integer id, String remark, LocalDateTime changeDate) {
        return logRepo.findById(id).map(log -> {
            log.setRemark(remark);
            log.setChangeDate(changeDate);
            return logRepo.save(log);
        }).orElseThrow(() -> new RuntimeException("Log record not found with id: " + id));
    }

    // Log ကို ဖျက်ရန်
    @Transactional
    public void deleteLog(Integer id) {
        if (logRepo.existsById(id)) {
            logRepo.deleteById(id);
        } else {
            throw new RuntimeException("Log record not found with id: " + id);
        }
    }
}