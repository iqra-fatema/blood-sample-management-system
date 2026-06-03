package com.blood.blood_sample_system.dto;

import com.blood.blood_sample_system.entity.Booking;
import com.blood.blood_sample_system.entity.TestPackage;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingDTO {
    //what user sends
    @Data
    public static class BookingRequest{
        @NotEmpty(message = "Select atleast one test!")
        private List<Long>testPackageIds;
        @NotNull(message = "Date is required")
        @Future(message = "Date must be in future")
        private LocalDate bookingDate;
        @NotNull(message = "Timeslot is required")
        private LocalTime timeSlot;
        @NotBlank(message = "Address is required")
        private String address;
        @NotBlank(message = "City is required!")
        private String city;

    }
    //what we send back
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingResponse{
        private Long id;//booking number
        private String userName;//who booked
        private List<String> testPackageNames;//list of testnames
        private LocalDate bookingDate;//appointmentdate
        private LocalTime timeSlot;//timeslot
        private String address;//collection address
        private String city;//collection city
        private Double totalPrice;//total price
        private String technicianName;//technician assigned
        private Booking.BookingStatus status;//confirmed/completed
        private Booking.PaymentStatus paymentStatus;//pending/paid
        private String reportUrl;//report link

    }
}
