package com.company.myweb;

import com.company.myweb.entity.*;
import com.company.myweb.repository.*;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableCaching
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@EnableJpaAuditing
public class MywebApplication {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final BookingTimeRepository bookingTimeRepository;
    private final DoctorRepository doctorRepository;
    private final BookingPriceRepository bookingPriceRepository;
    public MywebApplication(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, BookingTimeRepository bookingTimeRepository, DoctorRepository doctorRepository, BookingPriceRepository bookingPriceRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookingTimeRepository = bookingTimeRepository;
        this.doctorRepository = doctorRepository;
        this.bookingPriceRepository = bookingPriceRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(MywebApplication.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void setInitData() {
        setInitRoles();
        setInitUser();
        setInitBookingTime();
        initDoctor();
    }

    private void setInitRoles() {
        List<Role> allRoles = roleRepository.findAll();
        if (allRoles.isEmpty()) {
            Role adminRole = new Role();
            Role userRole = new Role();

            adminRole.setRoleName("ROLE_ADMIN");
            adminRole.setCreateDate(new Date());
            userRole.setRoleName("ROLE_USER");
            userRole.setCreateDate(new Date());

            roleRepository.save(adminRole);
            roleRepository.save(userRole);
        }
    }

    private void setInitUser() {
        if (userRepository.findAll().isEmpty()) {
            Role userRole = roleRepository.findRoleByRoleName("ROLE_USER").get();
            Role adminRole = roleRepository.findRoleByRoleName("ROLE_ADMIN").get();

            User user = new User();
            user.setUserName("usertest@gmail.com");
            user.setPassword(passwordEncoder.encode("123456789@Ax")); // Encrypt the password
            user.setFullName("User A");
            user.setRoles(userRole);
            user.setActivate(true);
            userRepository.save(user);

            User admin = new User();
            admin.setUserName("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("123456789@Ax"));
            admin.setFullName("Admin");
            admin.setActivate(true);
            admin.setRoles(adminRole);
            userRepository.save(admin);
        }
    }
    private void setInitBookingTime(){
        if(bookingTimeRepository.findAll().isEmpty()){
            BookingTime morning = new BookingTime(LocalTime.of(8, 30), LocalTime.of(11, 30));
            bookingTimeRepository.save(morning);

            // Afternoon
            BookingTime afternoon = new BookingTime(LocalTime.of(13, 30), LocalTime.of(16, 30));
            bookingTimeRepository.save(afternoon);

            // Evening
            BookingTime evening = new BookingTime(LocalTime.of(17, 0), LocalTime.of(19, 0));
            bookingTimeRepository.save(evening);
        }
    }
    private void initDoctor() {
        if (doctorRepository.findAll().isEmpty() && bookingPriceRepository.findAll().isEmpty()) {
            // Tạo bác sĩ mẫu
            Doctor bacSi1 = new Doctor();
            bacSi1.setFullName("John Doe");
            bacSi1.setIntroduction("Bác sĩ đa khoa giàu kinh nghiệm.");
            bacSi1.setMajor("Y học tổng hợp");
            bacSi1.setTitle("MD");
            bacSi1.setImage("doctor1.jpg"); // Thay thế bằng URL hình ảnh thực tế

            Doctor bacSi2 = new Doctor();
            bacSi2.setFullName("Jane Smith");
            bacSi2.setIntroduction("Bác sĩ chuyên khoa răng trẻ em.");
            bacSi2.setMajor("Răng hàm mặt trẻ em");
            bacSi2.setTitle("DDS");
            bacSi2.setImage("doctor2.jpg"); // Thay thế bằng URL hình ảnh thực tế

            // Lưu bác sĩ
            doctorRepository.save(bacSi1);
            doctorRepository.save(bacSi2);

            // Tạo khung giờ đặt lịch mẫu (giả sử bạn đã tạo khung giờ đặt lịch)
            BookingTime khungGio1 = bookingTimeRepository.findById(1L).orElse(null); // Thay thế bằng ID thực tế
            BookingTime khungGio2 = bookingTimeRepository.findById(2L).orElse(null); // Thay thế bằng ID thực tế

            // Tạo giá đặt lịch mẫu
            BookingPrice giaDatLich1 = new BookingPrice();
            giaDatLich1.setDoctor(bacSi1);
            giaDatLich1.setBookingTime(khungGio1);
            giaDatLich1.setPrice(new BigDecimal("100.00"));

            BookingPrice giaDatLich2 = new BookingPrice();
            giaDatLich2.setDoctor(bacSi2);
            giaDatLich2.setBookingTime(khungGio2);
            giaDatLich2.setPrice(new BigDecimal("150.00"));

            // Lưu giá đặt lịch
            bookingPriceRepository.save(giaDatLich1);
            bookingPriceRepository.save(giaDatLich2);
        }
    }
}
