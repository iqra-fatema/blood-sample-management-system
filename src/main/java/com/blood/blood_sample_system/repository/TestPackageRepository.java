package com.blood.blood_sample_system.repository;

import com.blood.blood_sample_system.entity.TestPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestPackageRepository extends JpaRepository<TestPackage,Long> {
List<TestPackage> findByNameContainingIgnoreCase(String keyword);
//ex: user enters Blood returns Blood Test, bloodsugar etc
List<TestPackage>findBySampleType(String sampleType);

}
