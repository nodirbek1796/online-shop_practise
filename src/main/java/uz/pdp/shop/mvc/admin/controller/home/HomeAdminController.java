package uz.pdp.shop.mvc.admin.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.shop.model.receive.CategoryReceiveModel;

@Controller
@RequestMapping("/")
public class HomeAdminController {

    @GetMapping()
    public String getHomePage(){
        return "home/home";
    }

    @GetMapping("admin")
    public String getAdminPage(
            Model model
    ){
        model.addAttribute("category", new CategoryReceiveModel());
        return "admin/category/list";
    }

}
