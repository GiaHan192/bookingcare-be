package com.company.myweb.payload.request;

import lombok.Data;

@Data
public class AddDoctorRequest {
    private String fullName = "";
    private String introduction = "";
    private String major = "";
    private String title = "";
}
