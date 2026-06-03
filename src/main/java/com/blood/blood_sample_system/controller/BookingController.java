package com.blood.blood_sample_system.controller;

import com.blood.blood_sample_system.dto.BookingDTO.*;
import com.blood.blood_sample_system.entity.Booking;
import com.blood.blood_sample_system.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
   @PostMapping("/create")
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(bookingService.createBooking(request, userDetails.getUsername()));
    }
    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse> >
    getMyBooking(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(bookingService.getMyBooking(userDetails.getUsername()));
    }
    @PutMapping("/cancel/{id}")
    public ResponseEntity<BookingResponse>
    cancelBooking( @PathVariable long bookingId,
                   @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId,userDetails.getUsername()));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/payment/{bookingId}")
    public ResponseEntity<BookingResponse>
    updatePayment(@PathVariable long bookingId,
                  @RequestParam Booking.PaymentStatus status){
        return ResponseEntity.ok(bookingService.updatePayment(bookingId,status));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/sample-collection/{bookingId}")
    public ResponseEntity<BookingResponse>
    markSampleCollected(@PathVariable long bookingId){
        return ResponseEntity.ok(bookingService.markSampleCollected(bookingId));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/report/{bookingId}")
    public ResponseEntity<BookingResponse>
    uploadReport(@PathVariable long bookingId,
                 @RequestParam String reportUrl){
        return ResponseEntity.ok(bookingService.uploadReport(bookingId,reportUrl));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<BookingResponse>> getAllBooking(){
        return ResponseEntity.ok(bookingService.getAllBooking());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status")
    public ResponseEntity<List<BookingResponse>>
    getBookingByStatus(@RequestParam Booking.BookingStatus status){
        return ResponseEntity.ok(bookingService.getBookingByStatus(status));
    }
}
