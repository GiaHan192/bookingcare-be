package com.company.myweb.dto;

import java.util.Date;

public class BookingDTO {
    private String fullName;
    private boolean gender;
    private String phoneNumber;
    private String email;
    private Date dateOfBirth;
    private String address;
    private String reason;
    private Date bookingDate;
    private String bookingTime;

    public BookingDTO(String fullName, boolean gender, String phoneNumber, String email, Date dateOfBirth, String address,
                      String reason, Date bookingDate, String bookingTime) {
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.reason = reason;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", reason='" + reason + '\'' +
                ", bookingDate=" + bookingDate +
                ", bookingTime='" + bookingTime + '\'' +
                '}';
    }
}
