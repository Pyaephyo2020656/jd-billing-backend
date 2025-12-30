package com.tjd.invoicemanagement.controller;

import com.tjd.invoicemanagement.model.Invoice;
import com.tjd.invoicemanagement.service.InvoiceService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin("*")
public class InvoiceController {
    private final InvoiceService service;
    public InvoiceController(InvoiceService s) { this.service = s; }

    @GetMapping
    public List<Invoice> list(@RequestParam(required = false) String search) { return service.getAll(search); }

    @PostMapping
    public Invoice save(@RequestBody Invoice inv) { return service.create(inv); }

    @GetMapping("/{id}")
    public Invoice get(@PathVariable Integer id) { return service.getById(id); }

    @PutMapping("/{id}")
    public Invoice update(@PathVariable Integer id, @RequestBody Invoice inv) { return service.update(id, inv); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { service.delete(id); }
}