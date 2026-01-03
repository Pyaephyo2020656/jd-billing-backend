package com.tjd.invoicemanagement.repository;

import com.tjd.invoicemanagement.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    long countByInvoiceNoStartingWith(String prefix);

    @Query("SELECT i FROM Invoice i WHERE i.invoiceNo LIKE %:k% OR i.customer.name LIKE %:k% ORDER BY i.invoiceNo DESC")
    List<Invoice> searchInvoices(String k);
}