package com.company.myweb.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;

@Data
@Entity
@Table(name = "booking_time")
public class BookingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime fromTime;
    private LocalTime toTime;
}
