package com.blood.blood_sample_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name="bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="technician_id",nullable = false)
    private Technician technician;
    @ManyToMany
    @JoinTable(
            name="booking_test_packages",
            joinColumns = @JoinColumn(name="booking_id"),
            inverseJoinColumns = @JoinColumn(name="test_package_id")
    )
    private List<TestPackage> testPackages;
    @Column(nullable = false)
    private LocalDate bookingDate;
    @Column(nullable = false)
    private LocalTime timeSlot;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String city;
    private String reportUrl;
    @Column(nullable = false)
    @Positive
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    private BookingStatus status=BookingStatus.CONFIRMED;
    public enum BookingStatus{
        CONFIRMED,CANCELLED,COMPLETED,SAMPLE_COLLECTED
    }
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus=PaymentStatus.PENDING;
    public enum PaymentStatus{
        PENDING,PAID
    }

//user--> many to one
    // testpackages--> many to many
    //technician--> many to one

}
