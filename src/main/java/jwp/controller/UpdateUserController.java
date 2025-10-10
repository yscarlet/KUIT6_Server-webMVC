package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;
import org.apache.catalina.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/update")
public class UpdateUserController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User(req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"));

        // db 업데이트
        MemoryUserRepository.getInstance().changeUserInfo(user);

        // session 업데이트
        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        System.out.println(user.getUserId() + "정보수정 완료");
        resp.sendRedirect("/user/list");
    }
}
