package com.tjd.invoicemanagement.service;

import com.tjd.invoicemanagement.model.PackagePlan;
import com.tjd.invoicemanagement.repository.PackagePlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PackagePlanService {

    private final PackagePlanRepository planRepo;

    public PackagePlanService(PackagePlanRepository planRepo) {
        this.planRepo = planRepo;
    }

    // Plan အားလုံးကို ယူရန်
    public List<PackagePlan> getAllPlans() {
        return planRepo.findAll();
    }

    // Plan အသစ်သိမ်းရန်
    @Transactional
    public PackagePlan savePlan(PackagePlan plan) {
        return planRepo.save(plan);
    }

    // ID ဖြင့် Plan ရှာရန်
    public PackagePlan getPlanById(Integer id) {
        return planRepo.findById(id).orElse(null);
    }

    // Plan အချက်အလက် (Name, Bandwidth) ပြင်ရန်
    @Transactional
    public PackagePlan updatePlan(Integer id, PackagePlan planDetails) {
        PackagePlan existingPlan = planRepo.findById(id).orElse(null);
        if (existingPlan != null) {
            existingPlan.setPlanName(planDetails.getPlanName());
            existingPlan.setBandwidth(planDetails.getBandwidth());
            return planRepo.save(existingPlan);
        }
        return null;
    }

    // Plan ကို ဖျက်ရန်
    @Transactional
    public void deletePlan(Integer id) {
        planRepo.deleteById(id);
    }
}