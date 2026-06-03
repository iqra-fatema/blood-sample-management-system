package com.blood.blood_sample_system.service;

import com.blood.blood_sample_system.dto.TestPackageDTO;
import com.blood.blood_sample_system.dto.TestPackageDTO.*;
import com.blood.blood_sample_system.entity.TestPackage;
import com.blood.blood_sample_system.repository.TestPackageRepository;
import lombok.*;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestPackageService {
    private final TestPackageRepository testPackageRepository;
   //add new package(ADMIN)
    public TestPackageResponse addPackage(TestPackageRequest request) {

        TestPackage testPackage = TestPackage.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .sampleType(request.getSampleType())
                .reportTimeHours(request.getReportTimeHours())
                .build();

        TestPackage saved = testPackageRepository.save(testPackage);
        return toResponse(saved);
    }
    //get all packages (admin and user)
    public List<TestPackageResponse> getAllPackages(){
    return testPackageRepository.findAll()
            .stream()//list to stream to process list items
            .map(this::toResponse)//converts each testpackage to testPackageResponse
            .collect(Collectors.toList());//converts back to list
    }
    public List<TestPackageResponse> searchPackages(String keyword){
        return testPackageRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    public List<TestPackageResponse>getPackagesBySampleType(String sampleType){
        return testPackageRepository
                .findBySampleType(sampleType)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    //update package
    public TestPackageResponse updatePackage(Long id,TestPackageRequest request){
        TestPackage testPackage=testPackageRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Test package not found!"));
        testPackage.setName(request.getName());
        testPackage.setPrice(request.getPrice());
        testPackage.setSampleType(request.getSampleType());
        testPackage.setDescription(request.getDescription());
        testPackage.setReportTimeHours(request.getReportTimeHours());

        return toResponse(testPackageRepository.save(testPackage));
    }
    public void deletePackage(Long id ){
        if(!testPackageRepository.existsById(id))
        {
            throw new RuntimeException("Test package not found!");
        }
        testPackageRepository.deleteById(id);
    }
    private TestPackageResponse toResponse( TestPackage testPackage){
        TestPackageResponse response=new TestPackageResponse();
        response.setId(testPackage.getId());
        response.setName(testPackage.getName());
        response.setPrice(testPackage.getPrice());
        response.setDescription(testPackage.getDescription());
        response.setSampleType(testPackage.getSampleType());
        response.setReportTimeHours(testPackage.getReportTimeHours());
        return response;
    }
}


