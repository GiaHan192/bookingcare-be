package com.company.myweb.service;

import com.company.myweb.dto.QuestionDTO;
import com.company.myweb.payload.request.AddTestRequest;
import com.company.myweb.repository.QuestionRepository;
import com.company.myweb.service.interfaces.IQuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(QuestionService.class);

    public QuestionService(QuestionRepository questionRepository, ObjectMapper objectMapper) {
        this.questionRepository = questionRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<QuestionDTO> getAll() {
        return List.of();
    }

    @Override
    public Boolean importTest(MultipartFile testFile) {
        if (testFile.isEmpty()) {
            return false;
        }
        try {
            AddTestRequest addTestRequest = objectMapper.readValue(testFile.getInputStream(), AddTestRequest.class);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;  // Return false if an error occurs
        }
    }
}
