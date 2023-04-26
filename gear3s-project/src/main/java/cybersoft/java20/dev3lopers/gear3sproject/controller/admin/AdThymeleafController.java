package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@Controller
@RequestMapping("/admin")
public class AdThymeleafController {

    @GetMapping("/home")
    public String getHomePage() {
        return "pages/admin-home";
    }

    @GetMapping("/users")
    public String getUsersPage() {
        return "pages/admin-users";
    }

    @GetMapping("/categories")
    public String getCategoriesPage() {
        return "pages/admin-categories";
    }

    @GetMapping("/products")
    public String getProductsPage() {
        return "pages/admin-products";
    }

    @GetMapping("/profile")
    public String getProfilePage() {
        return "pages/admin-profile";
    }
}
