package com.abikkk.quizapp.controller;


import com.abikkk.quizapp.dto.SubmitResponse;
import com.abikkk.quizapp.model.Question;
import com.abikkk.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("allQuestions")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @PostMapping("add")
    public ResponseEntity<String> addNewQuestion(@RequestBody Question question){
        if (questionService.addNewQuestion(question)) {
            return new ResponseEntity<>("Question added Successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Question Not added !!!",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("add-multiple")
    public ResponseEntity<String> addMultipleQuestions(@RequestBody List<Question> questions){
        if (questionService.addMultipleQuestions(questions)) {
            return new ResponseEntity<>("All Questions added Successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error adding Questions !!!",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        List<Question> questions = questionService.getQuestionsByCategory(category);
        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }


}
