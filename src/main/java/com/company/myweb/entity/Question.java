package com.company.myweb.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "table")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private long id;
    @Column(name = "question_title")
    private String questionTitle;
    @OneToMany(mappedBy = "question")
    private List<Answers> answers = new ArrayList<>();
}
