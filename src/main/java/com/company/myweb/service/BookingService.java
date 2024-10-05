package com.company.myweb.service;

import com.company.myweb.dto.BookingDTO;
import com.company.myweb.entity.Booking;
import com.company.myweb.repository.BookingRepository;
import com.company.myweb.service.imp.BookingServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService implements BookingServiceImp {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void save(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setAddress(bookingDTO.getAddress());
        booking.setEmail(bookingDTO.getEmail());
        booking.setGender(bookingDTO.isGender());
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setBookingTime(bookingDTO.getBookingTime());
        booking.setFullName(bookingDTO.getFullName());
        booking.setDateOfBirth(bookingDTO.getDateOfBirth());
        booking.setReason(bookingDTO.getReason());
        booking.setPhoneNumber(bookingDTO.getPhoneNumber());
        bookingRepository.save(booking);
    }
}
