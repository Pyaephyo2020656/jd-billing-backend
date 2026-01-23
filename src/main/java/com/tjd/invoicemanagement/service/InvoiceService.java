package com.tjd.invoicemanagement.service;

import com.tjd.invoicemanagement.model.Invoice;
import com.tjd.invoicemanagement.repository.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository repo;
    public InvoiceService(InvoiceRepository repo) { this.repo = repo; }

    private String generateInvNo() {
        String prefix = "INV-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        return String.format("%s-%03d", prefix, repo.countByInvoiceNoStartingWith(prefix) + 1);
    }

    @Transactional
    public Invoice create(Invoice inv) {
        inv.setInvoiceNo(generateInvNo());
        if (inv.getItems() != null) inv.getItems().forEach(i -> i.setInvoice(inv));
        return repo.save(inv);
    }
    
    public List<Invoice> getAll(String k) {
        if (k != null && !k.isEmpty()) {
            return repo.searchInvoices(k); // Repo ထဲက search logic ကို သုံးမယ်
        }
        return repo.findAll(); // search မပါရင် အကုန်ပြမယ်
    }


    public Invoice getById(Integer id) { return repo.findById(id).orElse(null); }

//    @Transactional
//    public Invoice update(Integer id, Invoice update) {
//        return repo.findById(id).map(existing -> {
//            existing.setCustomer(update.getCustomer());
//            existing.setTotalAmount(update.getTotalAmount());
//            existing.setStatus(update.getStatus());
//            existing.getItems().clear();
//            update.getItems().forEach(i -> { i.setInvoice(existing); existing.getItems().add(i); });
//            return repo.save(existing);
//        }).orElse(null);
//    }
    
//    
//    @Transactional
//    public Invoice update(Integer id, Invoice update) {
//        return repo.findById(id).map(existing -> {
//            existing.setCustomer(update.getCustomer());
//            existing.setInvoiceDate(update.getInvoiceDate()); 
//            
//            
//            existing.setSubTotal(update.getSubTotal());
//            existing.setDiscountAmount(update.getDiscountAmount());
//            
//            existing.setTotalAmount(update.getTotalAmount());
//            existing.setStatus(update.getStatus());
//            existing.setRemark(update.getRemark()); 
//            
//     
//            existing.getItems().clear();
//            update.getItems().forEach(i -> { 
//                i.setInvoice(existing); 
//                existing.getItems().add(i); 
//            });
//            
//            return repo.save(existing);
//        }).orElse(null);
//    }
    
    
    
    @Transactional
    public Invoice update(Integer id, Invoice update) {
        return repo.findById(id).map(existing -> {
      
            existing.setCustomer(update.getCustomer());
            existing.setInvoiceDate(update.getInvoiceDate()); 
            existing.setSubTotal(update.getSubTotal());
            existing.setDiscountAmount(update.getDiscountAmount());
            existing.setTotalAmount(update.getTotalAmount());
            existing.setStatus(update.getStatus());
            existing.setRemark(update.getRemark()); 

            if ("PAID".equals(update.getStatus()) && update.getNextExpiryDate() != null) {
                if (existing.getCustomer() != null) {
                
                    existing.getCustomer().setExpiryDate(update.getNextExpiryDate());
                }
            }
            
     
            existing.getItems().clear();
            update.getItems().forEach(i -> { 
                i.setInvoice(existing); 
                existing.getItems().add(i); 
            });
            
            return repo.save(existing);
        }).orElse(null);
    }

    @Transactional
    public void delete(Integer id) { repo.deleteById(id); }
}