package jwp.controller;


import jwp.dao.UserDao;
import jwp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


// @RequestMapping("/user") 클래스 단위에서 중복 선언이 가능하지만 그 안에 정의된 메서드 매핑이 충돌하면 안됨
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController{
    private final UserDao userDao;

    // Spring MVC에서는 컨트롤러 메서드의 파라미터 순서가 전혀 중요하지 않음
    // 순서가 아닌 타입과 어노테이션을 기준으로 각각의 값을 찾아서 넣기 때문
    @PostMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String password, HttpSession session) throws Exception {
        User loginUser = new User(userId, password);
        User user = userDao.findByUserId(userId);

        if (user != null && user.isSameUser(loginUser)) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/form")
    public String form(){
        return "user/form";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/login";
    }

    @GetMapping("/loginFailed")
    public String loginFailed(){
        return "user/loginFailed";
    }
}
