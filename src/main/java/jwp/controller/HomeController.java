package jwp.controller;

//import jwp.dao.QuestionDao;
import jwp.dao.QuestionDao;
import jwp.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController{
    private final QuestionDao questionDao;

    @GetMapping
    public String execute(Model model) throws Exception {   // HttpServletRequest req 필요 없어서 지움
        model.addAttribute("questions", questionDao.findAll());
        return "/home";
    }
}
