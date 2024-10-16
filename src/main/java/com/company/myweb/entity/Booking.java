package com.company.myweb.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "booking")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "booking_time_id")
    private BookingTime bookingTime;
    @Column(name = "booking_date")
    private Date bookingDate;
    @Column(name = "address")
    private String address;
    @Column(name = "reason")
    private String reason;
}
