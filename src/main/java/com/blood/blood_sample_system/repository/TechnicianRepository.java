package com.blood.blood_sample_system.repository;
import com.blood.blood_sample_system.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicianRepository extends JpaRepository <Technician,Long>{
   //find available technicians by city & status
   List<Technician>findByCityAndStatus(
            String city,
            Technician.STATUS status);
    boolean existsByPhone(String phone);
    //find by email
    boolean existsByEmail(String email);
}
