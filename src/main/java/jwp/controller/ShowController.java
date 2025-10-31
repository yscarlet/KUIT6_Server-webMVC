package jwp.controller;

import core.mvc.Controller;
import jwp.dao.QuestionDao;
import jwp.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowController implements Controller {
    private final QuestionDao questionDao = new QuestionDao();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String questionIdStr = req.getParameter("questionId");
        if (questionIdStr == null) {
            return "redirect:/";
        }

        Long questionId = Long.valueOf(questionIdStr);

        Question question = questionDao.findByQuestionId(questionId);
        if (question == null) {
            return "redirect:/";
        }

        req.setAttribute("question", question);

        // 5. JSP 경로 반환
        return "/qna/show.jsp";
    }
}
