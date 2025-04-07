package fi.haagahelia.quizzler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.haagahelia.quizzler.domain.Quiz;
import fi.haagahelia.quizzler.domain.QuizRepository;

@Controller
public class QuizzlerController {

    @Autowired
    QuizRepository quizRepository;

    @RequestMapping(value = { "/quizlist" })
    public String quizList(Model model) {
        model.addAttribute("quizzes", quizRepository.findAll());
        return "quizlist";
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
        return "redirect:/quizlist";
    }

    @RequestMapping(value = "/quiz/delete/{id}", method = RequestMethod.GET)
    public String deleteQuiz(@PathVariable("id") Long id) {
        quizRepository.deleteById(id);
        return "redirect:/quizlist";
    }

    @GetMapping("/quiz/edit/{id}")
    public String editQuizForm(@PathVariable("id") Long Id, Model model) {
        model.addAttribute("quiz", quizRepository.findById(Id));

        return "editquiz"; // editquiz.html
    }

}
