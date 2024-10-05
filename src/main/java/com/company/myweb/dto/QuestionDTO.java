package com.company.myweb.dto;

import jakarta.persistence.*;

import java.util.List;

public class QuestionDTO {
    private int id;
    private String questionTitle;
    private List<String> options;
    private List<String> answers;

    public QuestionDTO() {}

    public QuestionDTO(int id, String questionTitle, List<String> options, List<String> answers) {
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

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
