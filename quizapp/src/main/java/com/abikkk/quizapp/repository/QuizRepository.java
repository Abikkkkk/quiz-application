package com.abikkk.quizapp.repository;

import com.abikkk.quizapp.model.Question;
import com.abikkk.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
}

