package com.tjd.invoicemanagement.service;

import com.tjd.invoicemanagement.model.Quarter;
import com.tjd.invoicemanagement.repository.QuarterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuarterService {

    private final QuarterRepository quarterRepo;

    public QuarterService(QuarterRepository quarterRepo) {
        this.quarterRepo = quarterRepo;
    }

    // ရပ်ကွက်အားလုံးကို ယူရန် (Dropdown ပြရန်အတွက်)
    public List<Quarter> getAllQuarters() {
        return quarterRepo.findAll();
    }

    // ရပ်ကွက်အသစ် ထည့်ရန်
    @Transactional
    public Quarter saveQuarter(Quarter quarter) {
        return quarterRepo.save(quarter);
    }

    // ID ဖြင့် ရပ်ကွက်ရှာရန်
    public Quarter getQuarterById(Integer id) {
        return quarterRepo.findById(id).orElse(null);
    }

    // ရပ်ကွက်နာမည် ပြင်ရန်
    @Transactional
    public Quarter updateQuarter(Integer id, Quarter quarterDetails) {
        Quarter existingQuarter = quarterRepo.findById(id).orElse(null);
        if (existingQuarter != null) {
            existingQuarter.setQtrName(quarterDetails.getQtrName());
            return quarterRepo.save(existingQuarter);
        }
        return null;
    }

    // ရပ်ကွက်ကို ဖျက်ရန်
    @Transactional
    public void deleteQuarter(Integer id) {
        quarterRepo.deleteById(id);
    }
}