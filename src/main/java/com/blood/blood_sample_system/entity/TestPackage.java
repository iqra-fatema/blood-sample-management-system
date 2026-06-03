package com.blood.blood_sample_system.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
@Entity
@Table(name="test_packages")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TestPackage {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable=false)
    private String sampleType;
    @Column(nullable = false)
    @Positive
    private Double price;
    private String description;
    private Integer reportTimeHours;
}
