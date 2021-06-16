package uz.pdp.shop.mvc.admin.controller.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shop.entity.category.CategoryDatabase;
import uz.pdp.shop.model.receive.CategoryReceiveModel;
import uz.pdp.shop.model.response.base.ResponseStatus;
import uz.pdp.shop.service.category.CategoryService;

import java.util.List;

@Controller
@RequestMapping("api/admin/category")
public class CategoryController implements ResponseStatus {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add") // categoriya qoshyabmiz
    public String addCategory(
            @ModelAttribute("category") CategoryReceiveModel categoryReceiveModel,
            Model model
    ) {
        categoryService.addCategory(categoryReceiveModel);
        model.addAttribute("category", categoryReceiveModel);
        model.addAttribute("categoryList", categoryService.getCategoryList());
        return "/admin/category/list";
    }

    @GetMapping("/list")
    public String getCategoryList(
            Model model
    ) {
        model.addAttribute("category", new CategoryDatabase());
        model.addAttribute("categoryList", categoryService.getCategoryList());
        return "/admin/category/list";
    }

    @GetMapping("/list/{id}")
    public String getCategoryList(
            @PathVariable("id") int categoryId,
            Model model
    ) {
        List<CategoryDatabase> categoryDatabaseList = categoryService.getSubCategories(categoryId);
        model.addAttribute("sub_category_list", categoryDatabaseList);
        return "admin/product/list";
    }
}
