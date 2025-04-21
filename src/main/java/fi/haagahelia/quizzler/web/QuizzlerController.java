package fi.haagahelia.quizzler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.haagahelia.quizzler.domain.Answers;
import fi.haagahelia.quizzler.domain.AnswersRepository;
import fi.haagahelia.quizzler.domain.Question;
import fi.haagahelia.quizzler.domain.QuestionRepository;
import fi.haagahelia.quizzler.domain.Quiz;
import fi.haagahelia.quizzler.domain.QuizRepository;

@Controller
public class QuizzlerController {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswersRepository answersRepository;

    @RequestMapping(value = { "/" })
    public String showQuizList(Model model) {
        model.addAttribute("quizzes", quizRepository.findAll());
        return "quizlist";
    }

    @GetMapping("/quiz/{id}/questions")
    public String showQuizQuestions(@PathVariable("id") Long quizId, Model model) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        if (quiz == null) {
            return "redirect:/";
        }

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", quiz.getQuestions());
        return "questionlist";
    }

    @GetMapping("/question/{id}/answers")
    public String showAnswers(@PathVariable("id") Long questionId, Model model) {
        Question question = questionRepository.findById(questionId).orElse(null);

        model.addAttribute("question", question);
        model.addAttribute("answers", question.getAnswers());

        return "answerlist";
    }

    @GetMapping("/addquiz")
    public String showAddQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "addquiz";
    }

    @RequestMapping(value = "/quiz/save", method = RequestMethod.POST)
    public String saveQuiz(@ModelAttribute Quiz quiz) {
        quiz.setCreationDate(java.time.LocalDate.now());
        if (quiz.getPublishedStatus() == null) {
            quiz.setPublishedStatus(0);
        }
        quizRepository.save(quiz);
        return "redirect:/";
    }

    @RequestMapping(value = "/quiz/delete/{id}", method = RequestMethod.GET)
    public String deleteQuiz(@PathVariable("id") Long quizId) {
        quizRepository.deleteById(quizId);
        return "redirect:/";
    }

    @GetMapping("/quiz/edit/{id}")
    public String editQuizForm(@PathVariable("id") Long quizId, Model model) {
        model.addAttribute("quiz", quizRepository.findById(quizId));

        return "editquiz"; // editquiz.html
    }

    @GetMapping("/quiz/{id}/addquestion")
    public String showAddQuestionForm(@PathVariable("id") Long quizId, Model model) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        if (quiz == null) {
            return "redirect:/";
        }

        model.addAttribute("quiz", quiz);
        model.addAttribute("quizId", quizId);
        model.addAttribute("question", new Question());
        return "addquestion";
    }

    @RequestMapping(value = "/quiz/{id}/savequestion", method = RequestMethod.POST)
    public String saveQuestion(@PathVariable("id") Long quizId, @ModelAttribute Question question) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        if (quiz == null) {
            return "redirect:/";
        }

        question.setQuiz(quiz);
        questionRepository.save(question);
        return "redirect:/quiz/" + quizId + "/questions";
    }

    @RequestMapping(value = "/question/delete/{id}", method = RequestMethod.GET)
    public String deleteQuestion(@PathVariable("id") Long questionId) {
        Long quizId = questionRepository.findById(questionId).get().getQuiz().getQuizId();
        questionRepository.deleteById(questionId);
        return "redirect:/quiz/" + quizId + "/questions";
    }

    @RequestMapping(value = "/answers/delete/{id}", method = RequestMethod.GET)
    public String deleteAnswer(@PathVariable("id") Long Id) {
        Long questionId = answersRepository.findById(Id).get().getQuestion().getQuestionId();
        answersRepository.deleteById(Id);
        return "redirect:/question/" + questionId + "/answers";
    }

    @GetMapping("/question/{id}/addanswer")
    public String showAddAnswerForm(@PathVariable("id") Long questionId, Model model) {
        Question question = questionRepository.findById(questionId).orElse(null);

    if (question == null) {
        return "redirect:/"; 
    }

    model.addAttribute("question", question);
        model.addAttribute("questionId", questionId);
        model.addAttribute("answers", new Answers());
        return "addanswer";
    }

    @RequestMapping(value = "/question/{id}/saveanswer", method = RequestMethod.POST)
    public String saveAnswer(@PathVariable("id") Long questionId, @ModelAttribute Answers answer) {
        Question question = questionRepository.findById(questionId).orElse(null);

        answer.setId(null);
        answer.setQuestion(question);
        answersRepository.save(answer);
        return "redirect:/question/" + questionId + "/answers";
    }
}
