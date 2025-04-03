package com.abikkk.quizapp.controller.web;

import com.abikkk.quizapp.dto.QuestionDTO;
import com.abikkk.quizapp.dto.SubmitResponse;
import com.abikkk.quizapp.model.Question;
import com.abikkk.quizapp.model.Quiz;
import com.abikkk.quizapp.service.QuestionService;
import com.abikkk.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class WebController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("categories", questionService.getAllQuestions().stream()
                .map(Question::getCategory)
                .distinct()
                .collect(Collectors.toList()));
        return "home";
    }

    @GetMapping("/questions")
    public String questions(Model model) {
        model.addAttribute("questions", questionService.getAllQuestions());
        return "questions";
    }

    @GetMapping("/questions/add")
    public String addQuestionForm(Model model) {
        model.addAttribute("question", new Question());
        return "add-question";
    }

    @PostMapping("/questions/add")
    public String addQuestion(@ModelAttribute Question question) {
        questionService.addNewQuestion(question);
        return "redirect:/questions";
    }

    @GetMapping("/create-quiz")
    public String createQuizForm(Model model) {
        model.addAttribute("categories", questionService.getAllQuestions().stream()
                .map(Question::getCategory)
                .distinct()
                .collect(Collectors.toList()));
        return "create-quiz";
    }

    @PostMapping("/create-quiz")
    public String createQuiz(@RequestParam String category,
                             @RequestParam int numQ,
                             @RequestParam String title) {
        ResponseEntity<String> response = quizService.createQuiz(category, numQ, title);
        return "redirect:/quizzes";
    }

    @GetMapping("/quizzes")
    public String quizzes(Model model) {
        model.addAttribute("quizzes", quizService.getAllQuizzes());
        return "quizzes";
    }

    @GetMapping("/quiz/{id}")
    public String takeQuiz(@PathVariable Long id, Model model) {
        List<QuestionDTO> questions = quizService.getQuizQuestions(id);
        model.addAttribute("quizId", id);
        model.addAttribute("questions", questions);
        return "take-quiz";
    }

    @PostMapping("/{id}/submit")
    public String submitQuiz(@PathVariable Long id,
                             @RequestParam List<Long> questionIds,
                             @RequestParam List<String> answers,
                             Model model) {
        List<SubmitResponse> responses = new ArrayList<>();
        for (int i = 0; i < questionIds.size(); i++) {
            responses.add(new SubmitResponse(questionIds.get(i), answers.get(i)));
        }

        ResponseEntity<Integer> result = quizService.submitQuiz(id, responses);

        if (result == null || result.getBody() == null) {
            model.addAttribute("error", "Quiz submission failed. Please try again.");
            return "error-page"; // Create an error page or redirect to a fallback page.
        }

        model.addAttribute("score", result.getBody());
        model.addAttribute("total", questionIds.size());
        model.addAttribute("quizId", id);

        return "quiz-result";
    }

}
