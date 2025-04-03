package com.abikkk.quizapp.controller;

import com.abikkk.quizapp.dto.QuestionDTO;
import com.abikkk.quizapp.dto.SubmitResponse;
import com.abikkk.quizapp.model.Question;
import com.abikkk.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createNewQuiz(@RequestParam String category,@RequestParam int numQ,@RequestParam String title){
        return quizService.createQuiz(category,numQ,title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionDTO>> getQuizQuestions(@PathVariable Long id){
        return new ResponseEntity<>(quizService.getQuizQuestions(id), HttpStatus.OK);
    }

    //to return the number of right questions answered correctly.
    @PostMapping("{id}/submit")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Long id,@RequestBody List<SubmitResponse> responses){
        return quizService.submitQuiz(id,responses);
    }
}
