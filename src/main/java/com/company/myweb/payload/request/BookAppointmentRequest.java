package com.company.myweb.payload.request;

import lombok.Data;

import java.util.Date;

@Data
public class BookAppointmentRequest {
    private Long doctorId;
    private Long bookingTimeId;
    private Date bookingDate;
    private String reason;
}
