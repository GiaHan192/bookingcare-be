package com.company.myweb.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "doctors")
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    private String fullName = "";
    @Column(name = "introduction")
    private String introduction = "";
    @Column(name = "major")
    private String major = "";
    @Column(name = "title")
    private String title = "";
}
