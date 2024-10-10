package com.company.myweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswersDTO {
    private String answer;
    private Float point = 0f;
}
