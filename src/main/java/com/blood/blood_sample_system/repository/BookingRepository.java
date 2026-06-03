package com.blood.blood_sample_system.repository;
import com.blood.blood_sample_system.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    //all bookings booked by one user
      List<Booking> findByUser_Id(Long user_id);
    boolean existsByUser_IdAndBookingDateAndTimeSlot(
            Long UserId,
            LocalDate bookingDate,
            LocalTime timeSlot
    );
    long countByTechnicianIdAndBookingDate(
            Long technicianId,LocalDate  bookingDate);
    List<Booking>findByBookingDate(LocalDate date);
    List <Booking>findByStatus(Booking.BookingStatus status);
    List<Booking>findByTechnician_Id(Long technicianId);
    boolean existsByTechnician_IdAndBookingDateAndTimeSlot(
            Long technicianId,
            LocalDate bookingDate,
            LocalTime timeSlot
    );
}
