package com.blood.blood_sample_system.service;
import com.blood.blood_sample_system.dto.BookingDTO.*;
import com.blood.blood_sample_system.entity.*;
import com.blood.blood_sample_system.repository.BookingRepository;
import com.blood.blood_sample_system.repository.TechnicianRepository;
import com.blood.blood_sample_system.repository.TestPackageRepository;
import com.blood.blood_sample_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TestPackageRepository testPackageRepository;
    private final TechnicianRepository technicianRepository;
    private final UserRepository userRepository;
    private final TechnicianService technicianService;
    public BookingResponse createBooking(BookingRequest request, String userEmail) {
        User user=userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new RuntimeException("User not found!"));
        if(bookingRepository.existsByUser_IdAndBookingDateAndTimeSlot(user.getId(),
                request.getBookingDate(),request.getTimeSlot())){
            throw new RuntimeException("You already have a booking "
            +"at this date and time!");
        }

            List<TestPackage> testPackages=testPackageRepository.findAllById(request.getTestPackageIds());
             if(testPackages.isEmpty()) {
                 throw new RuntimeException("No valid test packagefound!");
             }
                 Double totalPrice=testPackages.stream()
                         .mapToDouble(TestPackage::getPrice)
                         .sum();

                 List<Technician> availableTechnicians=technicianRepository
                         .findByCityAndStatus(request.getCity(), Technician.STATUS.AVAILABLE);

                 Technician assignedTechnician=null;
                 for(Technician technician:availableTechnicians){
                     boolean  slotTaken=bookingRepository.existsByTechnician_IdAndBookingDateAndTimeSlot(
                             technician.getId(),
                             request.getBookingDate(),
                             request.getTimeSlot());
                     long visitsOnthisDay=bookingRepository.countByTechnicianIdAndBookingDate(
                              technician.getId(), request.getBookingDate());
                     if(!slotTaken && visitsOnthisDay< technician.getMaxVisitsPerDay()){
                         assignedTechnician=technician;
                         break;
                     }
                 }
                if(assignedTechnician==null){
                    throw new RuntimeException(
                            "No technician available for "+
                                    "this date and time slot " +
                                    "Please choose a different slot"
                    );
                }
                     Booking booking=Booking.builder()
                             .user(user)
                             .testPackages(testPackages)
                             .bookingDate(request.getBookingDate())
                             .timeSlot(request.getTimeSlot())
                             .address(request.getAddress())
                             .city(request.getCity())
                             .totalPrice(totalPrice)
                             .technician(assignedTechnician)
                             .status(Booking.BookingStatus.CONFIRMED)
                             .paymentStatus(Booking.PaymentStatus.PENDING)
                             .build();

                   Booking saved=  bookingRepository.save(booking);
                   return toResponse(saved);
                 }
                 //get user's booking
                 public List<BookingResponse> getMyBooking(String userEmail ){
                     User user=userRepository.findByEmail(userEmail)
                             .orElseThrow(()-> new RuntimeException("User not found!"));
                     return bookingRepository.findByUser_Id(user.getId())
                             .stream()
                             .map(this::toResponse)
                             .collect(Collectors.toList());
                 }

                 public BookingResponse cancelBooking(long bookingId,String userEmail){
                     Booking booking=bookingRepository.findById(bookingId)
                             .orElseThrow(()->new RuntimeException("Boooking not found!"));
                     //check booking belongs to the user
                     if(!booking.getUser().getEmail().equals(userEmail)){
                         throw new RuntimeException("Unauthorized to cancel "+
                             "this booking");
                     }

                 if (booking.getStatus()== Booking.BookingStatus.CANCELLED){
                     throw new RuntimeException("Booking already cancelled!");
                 }

                 if (booking.getStatus()== Booking.BookingStatus.SAMPLE_COLLECTED){
                     throw new RuntimeException("Cannot cancel! "+
                             "Sample already collected!");
                 }
                 booking.setStatus(Booking.BookingStatus.CANCELLED);

                 return toResponse(bookingRepository.save(booking));

                 }

                 public BookingResponse updatePayment(long bookingId,
                 Booking.PaymentStatus status){
                     Booking booking =bookingRepository.findById(bookingId)
                             .orElseThrow(()->  new RuntimeException("Booking not found!"));

                     booking.setPaymentStatus(status);
                     return toResponse(bookingRepository.save(booking));
                 }

                 public BookingResponse markSampleCollected(long bookingId){
                     Booking booking=bookingRepository.findById(bookingId)
                             .orElseThrow(()-> new RuntimeException("Booking not found!"));
                     if(booking.getStatus()!= Booking.BookingStatus.CONFIRMED){
                         throw new RuntimeException("Booking is not "+
                                 "CONFIRMED status!");}
                         booking.setStatus(Booking.BookingStatus.SAMPLE_COLLECTED);

                     return toResponse(bookingRepository.save(booking));
                     }

                 public BookingResponse uploadReport(long bookingId,
                 String reportUrl){
                     Booking booking= bookingRepository.findById(bookingId)
                                     .orElseThrow(()-> new RuntimeException("Booking not found!"));
                     if(booking.getStatus()!= Booking.BookingStatus.SAMPLE_COLLECTED){
                         throw new RuntimeException("Sample not collected yet! "
                         +"Cannot upload report");
                     }

                     booking.setReportUrl(reportUrl);
                             booking.setStatus(Booking.BookingStatus.COMPLETED);
                     return toResponse(bookingRepository.save(booking));
                 }

//get all bookings(admin)
                 public List<BookingResponse>getAllBooking(){
                     return bookingRepository.findAll()
                             .stream()
                             .map(this::toResponse)
                             .collect(Collectors.toList());
                 }
                 //get all bookings by status(admin)
                 public List<BookingResponse> getBookingByStatus(Booking.BookingStatus status){
                     return bookingRepository.findByStatus(status)
                             .stream()
                             .map(this::toResponse)
                             .collect(Collectors.toList());
                 }

    private BookingResponse toResponse(Booking booking){
                     BookingResponse response= new BookingResponse();
                     response.setId(booking.getId());
                     response.setUserName(booking.getUser().getName());
                     response.setTestPackageNames(booking.getTestPackages()
                             .stream()
                             .map(TestPackage::getName)
                             .collect(Collectors.toList()));
                     response.setBookingDate(booking.getBookingDate());
                     response.setTimeSlot(booking.getTimeSlot());
                     response.setAddress(booking.getAddress());
                     response.setCity(booking.getCity());
                     response.setTotalPrice(booking.getTotalPrice());
                     response.setTechnicianName(booking
                             .getTechnician()!=null?booking.getTechnician().getName():"Not assigned yet");
                     response.setStatus(booking.getStatus());
                     response.setPaymentStatus(booking.getPaymentStatus());
                     response.setReportUrl(booking.getReportUrl());
                     return response;
    }
}

