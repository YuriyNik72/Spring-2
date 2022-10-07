package ru.geek.cloud.client.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class CategoryController {
    private CategoryClient categoryClient;


    @Autowired
    public void setCategoryClient(CategoryClient categoryClient) {
        this.categoryClient = categoryClient;
    }
    @RequestMapping("/get-category/{id}")
    public String getCategory(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryClient.getCategory(id));
        return "category-view";
    }
    @RequestMapping("/get-categories")
    public String getAllCategories(Model model){
        model.addAttribute("categories", categoryClient.getAllCategories());
        return "category-view";
    }
}
