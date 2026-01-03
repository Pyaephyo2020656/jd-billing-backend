package com.tjd.invoicemanagement.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjd.invoicemanagement.model.Customer;
import com.tjd.invoicemanagement.model.Relocation;
import com.tjd.invoicemanagement.repository.CustomerRepository;
import com.tjd.invoicemanagement.repository.RelocationRepository;

@Service
public class RelocationService {

    private final RelocationRepository relocationRepo;
    private final CustomerRepository customerRepo;

    public RelocationService(RelocationRepository relocationRepo, CustomerRepository customerRepo) {
        this.relocationRepo = relocationRepo;
        this.customerRepo = customerRepo;
    }

    @Transactional
    public Relocation saveRelocation(Integer id, String newAddress, String newGps, String newDnsn, 
                                    com.tjd.invoicemanagement.model.Quarter newQtr, String remark) {
        
        // ၁။ လက်ရှိ Customer ဒေတာကို Database ကနေ ဆွဲထုတ်မယ်
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // ၂။ "နေရာဟောင်း" ဒေတာတွေကို Relocation Table ထဲ အရင်သိမ်းမယ်
        Relocation history = new Relocation();
        history.setCustomer(customer);
        history.setOldAddress(customer.getAddress());
        history.setOldQuarter(customer.getQuarter());
        history.setOldGps(customer.getGpsCoords());
        history.setOldDnsn(customer.getDnsn());
        history.setRelocationDate(LocalDate.now());
        history.setRemark(remark);
        
        relocationRepo.save(history);

        // ၃။ Customer Table မှာ "နေရာသစ်" အချက်အလက်တွေနဲ့ Update လုပ်မယ်
        customer.setAddress(newAddress);
        customer.setGpsCoords(newGps);
        customer.setDnsn(newDnsn);
        customer.setQuarter(newQtr);
        
        customerRepo.save(customer);

        return history;
    }

    // Customer တစ်ယောက်ချင်းစီရဲ့ History ကို ပြန်ကြည့်ရန်
    public List<Relocation> getHistoryByCustomerId(String customerId) {
        return relocationRepo.findByCustomer_CustomerIdOrderByRelocationDateDesc(customerId);
    }
    
    public List<Relocation> getAllRelocations() {
        return relocationRepo.findAll();
    }
    
    @Transactional
    public void deleteRelocation(Integer id) {
        if (relocationRepo.existsById(id)) {
            relocationRepo.deleteById(id);
        } else {
            throw new RuntimeException("History record not found");
        }
    }
}