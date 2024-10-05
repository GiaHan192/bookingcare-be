package com.company.myweb.service.imp;

import com.company.myweb.dto.BookingDTO;
import com.company.myweb.dto.QuestionDTO;
import com.company.myweb.entity.Question;

import java.util.List;

public interface QuestionServiceImp {
    List<QuestionDTO> getAll();
}
