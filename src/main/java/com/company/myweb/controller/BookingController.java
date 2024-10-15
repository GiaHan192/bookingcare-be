package com.company.myweb.controller;

import com.company.myweb.dto.BookingDTO;
import com.company.myweb.dto.DoctorBookingDTO;
import com.company.myweb.entity.common.ApiResponse;
import com.company.myweb.payload.request.BookAppointmentRequest;
import com.company.myweb.service.interfaces.IBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final IBookingService bookingService;

    public BookingController(IBookingService bookingServiceImp) {
        this.bookingService = bookingServiceImp;
    }


    @PostMapping
    public ResponseEntity<?> booking(@RequestBody BookAppointmentRequest bookAppointmentRequest){
        bookingService.bookAppointment(bookAppointmentRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/doctor")
    public ResponseEntity<ApiResponse<DoctorBookingDTO>> getBookingOfDoctor(@RequestParam Long doctorId,
                                                                            @RequestParam Date bookingDate) {
        return ResponseEntity.ok(ApiResponse.success(bookingService.getDoctorBooking(doctorId, bookingDate)));
    }

}
