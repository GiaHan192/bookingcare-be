package com.company.myweb.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int id;
    @Column(name = "question_title")
    private String questionTitle;
    @Column(name = "options")
    private String options;
    @Column(name = "answers")
    private String answers;

    public Question() {
    }

    public Question(int id, String questionTitle, String options, String answers) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.options = options;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionTitle='" + questionTitle + '\'' +
                ", options='" + options + '\'' +
                ", answers='" + answers + '\'' +
                '}';
    }
}
