package com.company.myweb.service;

import com.company.myweb.dto.BookingDTO;
import com.company.myweb.dto.QuestionDTO;
import com.company.myweb.entity.Booking;
import com.company.myweb.entity.Question;
import com.company.myweb.repository.BookingRepository;
import com.company.myweb.repository.QuestionRepository;
import com.company.myweb.service.imp.BookingServiceImp;
import com.company.myweb.service.imp.QuestionServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService implements QuestionServiceImp {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<QuestionDTO> getAll() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDTO> responses = new ArrayList<>();
        for (Question question : questions) {
            List<String> options = List.of(question.getOptions().split(","));
            List<String> answers = List.of(question.getAnswers().split(","));

            responses.add(new QuestionDTO(question.getId(), question.getQuestionTitle(), options, answers));
        }
        return responses;
    }
}
