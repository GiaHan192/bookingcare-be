package com.company.myweb.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "submition")
@Data
public class Submition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "email")
    private String email;
    @Column(name = "test_date")
    private Date testDate;
    @OneToMany(mappedBy = "submition", cascade = CascadeType.ALL)
    private List<SubmitAnswer> submitAnswers = new ArrayList<>();
}
