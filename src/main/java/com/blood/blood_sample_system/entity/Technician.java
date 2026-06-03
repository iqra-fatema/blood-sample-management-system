package com.blood.blood_sample_system.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name="technician")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Technician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String phone;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String city;
    @Column(nullable=false)
    private Integer maxVisitsPerDay=6;
    @Enumerated(EnumType.STRING)
    private STATUS status=STATUS.AVAILABLE;

    public enum STATUS {
        AVAILABLE,OFF_DUTY;
    }
}
