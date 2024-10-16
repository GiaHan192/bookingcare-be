package com.company.myweb.repository;

import com.company.myweb.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookingDateAndDoctor_Id(Date bookingTime, Long doctorId);
}
