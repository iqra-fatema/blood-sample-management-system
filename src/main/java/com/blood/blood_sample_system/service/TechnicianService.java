package com.blood.blood_sample_system.service;

import com.blood.blood_sample_system.dto.TechnicianDTO.*;

import java.util.List;
import java.util.stream.Collectors;

import com.blood.blood_sample_system.entity.Technician;
import com.blood.blood_sample_system.repository.TechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechnicianService {
    private final TechnicianRepository technicianRepository;
    //addtechnician(admin)
    public TechnicianResponse addTechnician(TechnicianRequest request){
        if(technicianRepository.existsByPhone(request.getPhone())){
            throw new RuntimeException("Phone already registered!");
        }
        if(technicianRepository.existsByEmail(request.getEmail()))
        {
            throw new RuntimeException("Email already registered!");
        }
        Technician technician=Technician.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .city(request.getCity())
                .maxVisitsPerDay(request.getMaxVisitsPerDay())
                .status(Technician.STATUS.AVAILABLE).
                build();
        return toResponse(technicianRepository.save(technician));
    }

   // (admin)
    public List<TechnicianResponse> getAllTechnicians(){
        return technicianRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    public List<TechnicianResponse> getAvailableTechnicianByCity(String city)

    {
        return technicianRepository
                .findByCityAndStatus(city, Technician.STATUS.AVAILABLE)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

    }
    //update techni status
    public TechnicianResponse updateStatus(Long id,Technician.STATUS status)
    {Technician technician=technicianRepository.findById(id)
            .orElseThrow(
                    ()->new RuntimeException("Technician not found!")
            );
        technician.setStatus(status);
        return toResponse(technicianRepository.save(technician));
    }
    public void deleteTechnician(Long id){
        if(!technicianRepository.existsById(id)){
            throw new RuntimeException("Technician not found!");
        }
        technicianRepository.deleteById(id);
    }
    private TechnicianResponse toResponse(Technician technician){
        TechnicianResponse response=new   TechnicianResponse();
        response.setId(technician.getId());
        response.setName(technician.getName());
        response.setPhone(technician.getPhone());
        response.setEmail(technician.getEmail());
        response.setCity(technician.getCity());
        response.setStatus(technician.getStatus().name());
        response.setMaxVisitsPerDay(technician.getMaxVisitsPerDay());
        return response;
    }
}
