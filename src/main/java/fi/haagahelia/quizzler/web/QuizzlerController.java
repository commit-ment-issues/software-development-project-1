package fi.haagahelia.quizzler.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.haagahelia.quizzler.domain.QuizRepository;

@Controller
public class QuizzlerController {

    @Autowired QuizRepository quizRepository;

    @RequestMapping(value = {"/quizlist"})
    public String quizList(Model model) {
        model.addAttribute("quizzes", quizRepository.findAll());
        return "quizlist";
    }

}
