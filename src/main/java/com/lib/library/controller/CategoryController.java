package com.lib.library.controller;

import com.lib.library.model.Category;
import com.lib.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class CategoryController {
    CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<Category> getcategories() {
        System.out.println("calling get");
        return categoryService.getCategories();

    }
    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category categoryObject) {
        System.out.println("calling createCategory ==>");
        return categoryService.createCategory(categoryObject);
    }

//    @GetMapping("/test")
//    public String getTest() {
//        return categoryService.getTest();
//    }
//
//    @PutMapping("/test")
//    public String putTest() {
//        return categoryService.putTest();
//    }
}
