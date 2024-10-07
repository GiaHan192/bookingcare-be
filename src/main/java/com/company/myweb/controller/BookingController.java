package com.company.myweb.controller;

import com.company.myweb.dto.BookingDTO;
import com.company.myweb.service.interfaces.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    IBookingService bookingServiceImp;

    @PostMapping()
    public ResponseEntity<?> booking(@RequestBody BookingDTO bookingDTO){
        bookingServiceImp.save(bookingDTO);
        return ResponseEntity.ok().build();
    }

}
