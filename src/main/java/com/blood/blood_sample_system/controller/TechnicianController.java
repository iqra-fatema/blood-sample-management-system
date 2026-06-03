package com.blood.blood_sample_system.controller;

import com.blood.blood_sample_system.dto.TechnicianDTO.*;
import com.blood.blood_sample_system.entity.Technician;
import com.blood.blood_sample_system.service.TechnicianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technician")
@RequiredArgsConstructor
public class TechnicianController {
    private final TechnicianService technicianService;

    //admin only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<TechnicianResponse>
    addTechnician(@Valid @RequestBody TechnicianRequest request) {
        return ResponseEntity.ok(technicianService.addTechnician(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<TechnicianResponse>> getAllTechnicians() {
        return ResponseEntity.ok(technicianService.getAllTechnicians());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/available")
    public ResponseEntity<List<TechnicianResponse>>
    getAvailableTechnicianByCity(@RequestParam String city) {
        return ResponseEntity.ok(technicianService.getAvailableTechnicianByCity(city));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/status/{id}")
    public ResponseEntity<TechnicianResponse>
    updateStatus(@PathVariable Long id, @RequestParam Technician.STATUS status) {
return ResponseEntity.ok(technicianService.updateStatus(id, status));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String > deleteTechnician(@PathVariable Long id){
        technicianService.deleteTechnician(id);
        return ResponseEntity.ok("Technician deleted successfully!");
    }
}