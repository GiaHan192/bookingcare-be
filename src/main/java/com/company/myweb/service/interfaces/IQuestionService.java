package com.company.myweb.service.interfaces;

import com.company.myweb.dto.QuestionDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IQuestionService {
    List<QuestionDTO> getAllQuestions();
    Boolean importTest(MultipartFile testFile);
}
