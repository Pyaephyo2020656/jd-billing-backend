package com.tjd.invoicemanagement.controller;

import com.tjd.invoicemanagement.model.PackagePlan;
import com.tjd.invoicemanagement.service.PackagePlanService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/plans")
@CrossOrigin(origins = "*")
public class PackagePlanController {
    private final PackagePlanService service;

    public PackagePlanController(PackagePlanService service) { this.service = service; }

    @GetMapping
    public List<PackagePlan> getAll() { return service.getAllPlans(); }

    @PostMapping
    public PackagePlan create(@RequestBody PackagePlan plan) { return service.savePlan(plan); }

    @PutMapping("/{id}")
    public PackagePlan update(@PathVariable Integer id, @RequestBody PackagePlan plan) { return service.updatePlan(id, plan); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { service.deletePlan(id); }
}