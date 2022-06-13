package ru.geekbrains.controllers;

import ru.geekbrains.entites.Category;
import ru.geekbrains.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryRestController implements CategoryClient{

    CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Override
    public Category getCategory(@PathVariable Integer id) {
        return categoryService.getAllCategories().get(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
