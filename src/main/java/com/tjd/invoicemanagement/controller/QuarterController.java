package com.tjd.invoicemanagement.controller;

import com.tjd.invoicemanagement.model.Quarter;
import com.tjd.invoicemanagement.service.QuarterService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/quarters")
@CrossOrigin(origins = "*") // Frontend ကနေ လှမ်းခေါ်လို့ရအောင်
public class QuarterController {
    private final QuarterService service;

    public QuarterController(QuarterService service) { this.service = service; }

    @GetMapping
    public List<Quarter> getAll() { return service.getAllQuarters(); }

    @PostMapping
    public Quarter create(@RequestBody Quarter qtr) { return service.saveQuarter(qtr); }

    @PutMapping("/{id}")
    public Quarter update(@PathVariable Integer id, @RequestBody Quarter qtr) { return service.updateQuarter(id, qtr); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { service.deleteQuarter(id); }
}