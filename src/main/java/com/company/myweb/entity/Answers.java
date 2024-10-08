package com.company.myweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "answers")
public class Answers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "answers")
    private String answers;
    @Column(name = "point")
    private float point = 0f;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
