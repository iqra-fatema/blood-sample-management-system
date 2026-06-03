package com.blood.blood_sample_system.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blood.blood_sample_system.entity.User;
import java.util.Optional;
@Repository
public interface UserRepository
        extends JpaRepository <User,Long>{
    Optional<User> findByEmail(String email);
    //during login--user enters email->find them in db-> check pwd
    boolean existsByEmail(String email);
    //during registration--before saving new user check
}
