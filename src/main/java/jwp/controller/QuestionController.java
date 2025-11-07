package jwp.controller;

import jwp.dao.QuestionDao;
import jwp.model.Question;
import jwp.model.User;
import jwp.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionDao questionDao;

    @PostMapping("/create")
    public String create(@ModelAttribute Question question) {
        // ModelAttribute의 동작 과정
        // Question 객체를 생성 -> 이때 @NoArgsConstructor(기본 생성자)가 사용됨, 개발자가 만든 생성자는 호출되지 않음
        // 모든 필드는 초기값, 0 또는 null로 시작됨
        // Setter를 이용해 각 필드에 값을 주입함 -> writer, title, contents는 채워짐
        // id는? -> persist에 의해 생성되는 쿼리: insert into QUESTIONS (questionId, contents, countOfAnswer, createdDate, title, writer) values (default, ?, ?, ?, ?, ?)
        // 알아서 잘 채워진다.
        // createdDate에 null이 들어가는 문제는 어떻게 해결할 수 있나
        // -> setter만들기, @PrePersist사용, 엔티티 필드에서 기본 값 세팅 (INSERT되기 전에 자동 호츌되는 콜백 메서드)
        questionDao.insert(question);
        return "redirect:/";
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (UserSessionUtils.isLogined(session)) {          // 회원만 질문 등록 가능
            return "qna/form";
        }
        return "redirect:/user/loginForm";
    }

    @GetMapping("/show")
    public String show(@RequestParam String questionId, Model model) throws Exception {
        Question question = questionDao.findByQuestionId(Integer.parseInt(questionId));
        model.addAttribute("question", question);
        return "qna/show";
    }

    // RequestParam: 이름이 일치하면 자동으로 mapping됨
    @PostMapping("update")
    public String execute(HttpSession session, @RequestParam String questionId, @RequestParam String title, @RequestParam String contents) throws Exception {
        if (!UserSessionUtils.isLogined(session)) {
            return "redirect:/users/loginForm";
        }
        User user = UserSessionUtils.getUserFromSession(session);
        Question question = questionDao.findByQuestionId(Integer.parseInt(questionId));
        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException("로그인된 유저와 질문 작성자가 다르면 질문을 수정할 수 없습니다.");
        }
        question.updateTitleAndContents(title, contents);
        questionDao.update(question);
        return "redirect:/";
    }

    @GetMapping("/updateForm")
    public String execute(HttpSession session, @RequestParam String questionId, Model model) throws Exception {
        if (!UserSessionUtils.isLogined(session)) {          // 회원만 질문 등록 가능
            return "redirect:/user/loginForm";
        }
        Question question = questionDao.findByQuestionId(Integer.parseInt(questionId));
        User user = UserSessionUtils.getUserFromSession(session);
        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException();
        }
        model.addAttribute("question", question);
        return "qna/updateForm";
    }
}
