package com.company.myweb.service;

import com.company.myweb.dto.QuestionDTO;
import com.company.myweb.entity.Answers;
import com.company.myweb.entity.Question;
import com.company.myweb.payload.request.AddQuestionRequest;
import com.company.myweb.repository.QuestionRepository;
import com.company.myweb.service.interfaces.IQuestionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
            ArrayList<Question> questionList = new ArrayList<>();
            TypeReference<List<AddQuestionRequest>> typeRef = new TypeReference<List<AddQuestionRequest>>() {
            };

            List<AddQuestionRequest> addQuestionRequestList = objectMapper.readValue(testFile.getInputStream(), typeRef);
            for (AddQuestionRequest addQuestionRequest : addQuestionRequestList) {
                Question question = new Question();
                question.setQuestionTitle(addQuestionRequest.getQuestion());

                List<Answers> answersList = new ArrayList<>();
                List<String> options = addQuestionRequest.getOptions();
                for (int index = 0; index < options.size(); index++) {
                    Answers answer = Answers.builder()
                            .answers(options.get(index))
                            .point(addQuestionRequest.getAnswers().get(index))
                            .question(question)
                            .build();
                    answersList.add(answer);
                }
                question.setAnswers(answersList);
                questionList.add(question);
            }
            questionRepository.saveAll(questionList);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;  // Return false if an error occurs
        }
    }
}
