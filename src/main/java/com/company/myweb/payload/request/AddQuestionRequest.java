package com.company.myweb.payload.request;

import com.company.myweb.entity.Answers;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddQuestionRequest {
    private String questionTitle;
    private List<AddAnswerRequest> answers = new ArrayList<>();
}
