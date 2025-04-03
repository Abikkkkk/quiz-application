package com.abikkk.quizapp.service;

import com.abikkk.quizapp.dto.SubmitResponse;
import com.abikkk.quizapp.model.Question;
import com.abikkk.quizapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public boolean addNewQuestion(Question question){
        try{
            questionRepository.save(question);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addMultipleQuestions(List<Question> questions){
        try{
            questionRepository.saveAll(questions);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionRepository.findByCategory(category);
    }
}