package jwp.controller;

import jwp.dao.UserDao;
import jwp.model.User;
import jwp.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller     // spring bean에 controller라고 기록 됨
@RequestMapping("/user")        // api 연결
@RequiredArgsConstructor
public class UserController {
    private final UserDao userDao;      // 원래는 DI를 통해 받아야 하는데 조금 있다가 보여줌 조금만 기다려~

    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user) throws Exception {
        System.out.println(user.getUserId());
        System.out.println(user.getName());
        userDao.insert(user);
        System.out.println("user 회원가입 완료");
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String listUsers(HttpSession session, Model model) throws Exception {
        if (UserSessionUtils.isLogined(session)) {
            model.addAttribute("users", userDao.findAll());
            return "user/list";
        }
        return "redirect:/user/loginForm";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User modifiedUser) throws Exception {
        userDao.update(modifiedUser);
        return "redirect:/user/list";
    }

    // req에서 받아오던 userId와 session을 별도의 param으로 받아옴
    @GetMapping("/updateForm")
    public String updateFormUser(@RequestParam String userId, HttpSession session) throws Exception {
        User user = userDao.findByUserId(userId);
        Object value = session.getAttribute("user");

        if (user != null && value != null) {
            if (user.equals(value)) {            // 수정되는 user와 수정하는 user가 동일한 경우
                return "/user/updateForm";
            }
        }
        return "redirect:/";
    }

}
