package com.company.myweb.payload.request;

import lombok.Data;

@Data
public class AddAnswerRequest {
    private String answers;
    private float point = 0f;
}
