package com.company.myweb.service;

import com.company.myweb.dto.BookingDTO;
import com.company.myweb.dto.BookingTimeDTO;
import com.company.myweb.dto.DoctorBookingDTO;
import com.company.myweb.entity.*;
import com.company.myweb.entity.common.ApiException;
import com.company.myweb.payload.request.BookAppointmentRequest;
import com.company.myweb.repository.BookingRepository;
import com.company.myweb.repository.BookingTimeRepository;
import com.company.myweb.repository.DoctorRepository;
import com.company.myweb.repository.UserRepository;
import com.company.myweb.service.interfaces.IBookingService;
import com.company.myweb.utils.SecurityUtil;
import com.company.myweb.utils.TimeIgnoringComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookingService implements IBookingService {

    private final BookingRepository bookingRepository;
    private final DoctorRepository doctorRepository;
    private final TimeIgnoringComparator ignoringComparator;
    private final BookingTimeRepository bookingTimeRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, DoctorRepository doctorRepository, TimeIgnoringComparator ignoringComparator, BookingTimeRepository bookingTimeRepository, BookingTimeRepository bookingTimeRepository1, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.doctorRepository = doctorRepository;
        this.ignoringComparator = ignoringComparator;
        this.bookingTimeRepository = bookingTimeRepository1;
        this.userRepository = userRepository;
    }

    @Override
    public Boolean bookAppointment(BookAppointmentRequest bookAppointmentRequest) {


        Long doctorId = bookAppointmentRequest.getDoctorId();
        Doctor doctor = doctorRepository
                .findById(doctorId).orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST)
                        .withMessage("Không tìm thấy bác sĩ với id:" + doctorId));

        BookingTime bookingTime = bookingTimeRepository.findById(bookAppointmentRequest.getBookingTimeId())
                .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST)
                        .withMessage("Không tìm lịch khám:" + bookAppointmentRequest.getBookingTimeId()));

        List<Booking> bookingsWithDateAndDoctor =
                bookingRepository.findAllByBookingDateAndDoctor_Id(bookAppointmentRequest.getBookingDate(), doctor.getId());

        // TODO: Check is duplicate calendar or not

        // --- End of Code Block ---
        Optional<String> currentUserEmailOptional = SecurityUtil.getCurrentUsernameLogin();
        if (currentUserEmailOptional.isEmpty()) {
            throw ApiException.create(HttpStatus.UNAUTHORIZED).withMessage("Cần đăng nhập để thực hiện chức năng này");
        }
        String userName = currentUserEmailOptional.get();
        User currentUser = userRepository.findByUserName(userName);
        if (currentUser != null) {
            Booking booking = new Booking();
            booking.setUser(currentUser);
            booking.setBookingDate(bookAppointmentRequest.getBookingDate());
            booking.setBookingTime(bookingTime);
            booking.setReason(bookAppointmentRequest.getReason());
            booking.setDoctor(doctor);
            bookingRepository.save(booking);
            return true;
        }
        return false;
    }

    @Override
    public DoctorBookingDTO getDoctorBooking(Long doctorId, Date bookingDate) {
        List<Booking> bookingsWithDateAndDoctor =
                bookingRepository.findAllByBookingDateAndDoctor_Id(bookingDate, doctorId);

        Map<Long, BookingTime> doctorBookingWithBookingKey = bookingsWithDateAndDoctor.
                stream()
                .collect(Collectors.toMap(booking -> booking.getBookingTime().getId(), Booking::getBookingTime));

        Doctor doctor = doctorRepository
                .findById(doctorId).orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST)
                        .withMessage("Không tìm thấy bác sĩ với id:" + doctorId));

        List<BookingPrice> bookingPrices = doctor.getBookingPrice();

        DoctorBookingDTO doctorBookingDto = new DoctorBookingDTO();
        doctorBookingDto.setDoctorId(doctorId);
        doctorBookingDto.setBookingDate(bookingDate);
        List<BookingTimeDTO> bookingTimeDtoList = doctorBookingDto.getBookingTimeDTOS();

        for (BookingPrice bookingPrice : bookingPrices) {
            BookingTime bookingTime = bookingPrice.getBookingTime();
            BookingTimeDTO bookingTimeDTO = new BookingTimeDTO();
            bookingTimeDTO.setPrice(bookingPrice.getPrice());
            bookingTimeDTO.setFromTime(bookingTime.getFromTime());
            bookingTimeDTO.setToTime(bookingTime.getToTime());
            BookingTime existBookingTime = doctorBookingWithBookingKey.get(bookingTime.getId());
            bookingTimeDTO.setBooked(existBookingTime != null);
            bookingTimeDtoList.add(bookingTimeDTO);
        }

        return doctorBookingDto;
    }
}
