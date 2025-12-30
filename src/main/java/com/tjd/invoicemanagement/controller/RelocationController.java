package com.tjd.invoicemanagement.controller;

import com.tjd.invoicemanagement.model.Quarter;
import com.tjd.invoicemanagement.model.Relocation;
import com.tjd.invoicemanagement.service.RelocationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/relocations")
@CrossOrigin(origins = "*")
public class RelocationController {

	private final RelocationService service;
    private final com.tjd.invoicemanagement.repository.CustomerRepository customerRepo; // Quarter ရှာရန် လိုအပ်သည်

    public RelocationController(RelocationService service, com.tjd.invoicemanagement.repository.CustomerRepository customerRepo) {
        this.service = service;
        this.customerRepo = customerRepo;
    }

    @PostMapping("/{id}")
    public Relocation createRelocation(@PathVariable Integer id, @RequestBody java.util.Map<String, Object> payload) {
        // ၁။ Frontend က ပို့လိုက်တဲ့ JSON Key တွေအတိုင်း Data ယူမယ်
        String newAddress = (String) payload.get("newAddress");
        String newGps = (String) payload.get("newGps");
        String newDnsn = (String) payload.get("newDnsn");
        String remark = (String) payload.get("remark");

        // ၂။ newQtr (Object) ထဲက qtrId ကို ယူပြီး Quarter ရှာဖွေမယ်
        java.util.Map<String, Object> qtrMap = (java.util.Map<String, Object>) payload.get("newQtr");
        Integer qtrId = Integer.parseInt(qtrMap.get("qtrId").toString());
        
        // CustomerRepository ထဲက method ကိုသုံးပြီး Quarter အစစ်ကို ရှာဖွေပါ
        Quarter newQtr = customerRepo.findQuarterById(qtrId);

        // ၃။ Service ကို လှမ်းခေါ်ပြီး Database ထဲ သိမ်းဆည်းပါ
        return service.saveRelocation(id, newAddress, newGps, newDnsn, newQtr, remark);
    }
    
    @GetMapping("/history/all")
    public List<Relocation> getAllHistory() {
        return service.getAllRelocations();
    }
}