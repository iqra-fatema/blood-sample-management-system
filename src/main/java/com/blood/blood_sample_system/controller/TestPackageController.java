package com.blood.blood_sample_system.controller;

import com.blood.blood_sample_system.dto.TestPackageDTO.*;
import com.blood.blood_sample_system.service.TestPackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class TestPackageController {
    private final TestPackageService testPackageService;

    //admin
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<TestPackageResponse>
    addPackage(@Valid @RequestBody TestPackageRequest request) {
        return ResponseEntity.ok(testPackageService.addPackage(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<TestPackageResponse> updatePackage(
            @PathVariable Long id,
            @Valid @RequestBody
            TestPackageRequest request) {
        return ResponseEntity.ok(testPackageService.updatePackage(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePackage(@PathVariable Long id) {
        testPackageService.deletePackage(id);
        return ResponseEntity.ok("Test package deleted successfully!");
    }


    //user and admin
    @GetMapping("/all")
    public ResponseEntity<List<TestPackageResponse>> getAllPackages() {
        return ResponseEntity.ok(testPackageService.getAllPackages());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TestPackageResponse> >
    searchPackages(@RequestParam String keyword){
        return ResponseEntity.ok(testPackageService.searchPackages(keyword));
    }

    @GetMapping("/sample")
    public ResponseEntity<List<TestPackageResponse>>
    getPackagesBySampleType(@RequestParam String sampleType){
        return ResponseEntity.ok(testPackageService.getPackagesBySampleType(sampleType));
    }
}