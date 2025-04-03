package com.abikkk.quizapp.service;

import com.abikkk.quizapp.dto.QuestionDTO;
import com.abikkk.quizapp.dto.SubmitResponse;
import com.abikkk.quizapp.exception.ResourceNotFoundException;
import com.abikkk.quizapp.mapper.ModelMapperConfig;
import com.abikkk.quizapp.model.Question;
import com.abikkk.quizapp.model.Quiz;
import com.abikkk.quizapp.repository.QuestionRepository;
import com.abikkk.quizapp.repository.QuizRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category,numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);

        return new ResponseEntity<>("Success !", HttpStatus.CREATED);

    }

    public List<QuestionDTO> getQuizQuestions(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with ID: " + id));

        return quiz.getQuestions().stream()
                .map(question -> modelMapper.map(question, QuestionDTO.class))
                .collect(Collectors.toList());
    }

    //to return the number of right questions answered.
    public ResponseEntity<Integer> submitQuiz(Long id, List<SubmitResponse> responses) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with ID: " + id));
        List<Question> questions = quiz.getQuestions();

        int correctResponses = 0;

        for(int i = 0; i < Math.min(responses.size(), questions.size()); i++){
            if(responses.get(i).getResponse().equals(questions.get(i).getRightAnswer())){
                correctResponses ++;
            }
        }
        return new ResponseEntity<>(correctResponses,HttpStatus.OK);
    }
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

}
