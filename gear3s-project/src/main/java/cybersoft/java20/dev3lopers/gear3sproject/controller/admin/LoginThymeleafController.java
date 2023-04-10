package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class LoginThymeleafController {
    @GetMapping("")
    public String login(Model model) {
        model.addAttribute("message", "Dat");
        return "login/admin-login";
    }
}
