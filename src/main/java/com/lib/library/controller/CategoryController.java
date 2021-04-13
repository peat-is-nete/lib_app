package com.lib.library.controller;

import com.lib.library.model.Book;
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
    public List<Category> getCategories() {
        System.out.println("calling getCategories");
        return categoryService.getCategories();
    }


    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category categoryObject) {
        System.out.println("calling createCategory ==>");
        return categoryService.createCategory(categoryObject);
    }
    // DELETE CATEGORIES BY ID
    @DeleteMapping("/categories/{id}")
    public String deleteCategoryById(@PathVariable Long id){
        System.out.println("calling deleteCategoryById ==>");
        return categoryService.deleteCategoryById(id);
    }





    @GetMapping(path = "/categories/{categoryId}")
    public Category getCategory(@PathVariable Long categoryId) {
        System.out.println("calling getCategory ==>");
        return categoryService.getCategory(categoryId);
    }

    @PutMapping("/categories/{categoryId}")
    public Category updateCategory(@PathVariable Long categoryId, @RequestBody Category categoryObject) {
        System.out.println("calling updateCategory ==>");
        return categoryService.updateCategory(categoryId, categoryObject);
    }

    @PostMapping("/categories/{categoryId}/books")
    public Book createCategoryBook(@PathVariable Long categoryId, @RequestBody Book bookObject){
        System.out.println(("calling createBookCategory book"));
        return categoryService.createCategoryBook(categoryId, bookObject);
    }

    @GetMapping("/categories/{categoryId}/books")
    public List<Book> getCategoryBooks(@PathVariable Long categoryId){
        System.out.println("Calling getCategory book ==>");
        return categoryService.getCategoryBooks(categoryId);
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
