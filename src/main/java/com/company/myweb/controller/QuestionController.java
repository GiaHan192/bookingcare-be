package com.company.myweb.controller;

import com.company.myweb.dto.QuestionDTO;
import com.company.myweb.service.interfaces.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private IQuestionService questionServiceImp;

    @GetMapping("")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(){
        List<QuestionDTO> questions = questionServiceImp.getAll();
        return ResponseEntity.ok(questions);
    }


}
