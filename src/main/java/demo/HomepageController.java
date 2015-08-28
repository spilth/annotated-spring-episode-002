package demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class HomepageController {
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("now", new Date());

        return "index";
    }
}
