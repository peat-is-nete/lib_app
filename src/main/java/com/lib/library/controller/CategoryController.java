package com.lib.library.controller;

import com.lib.library.model.Book;
import com.lib.library.model.Category;
import com.lib.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path="/api")
public class CategoryController {
    CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        System.out.println("calling getCategories ==>");
        return categoryService.getCategories();
    }

    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category categoryObject) {
        System.out.println("calling createCategory ==>");
        return categoryService.createCategory(categoryObject);
    }
    // DELETE CATEGORIES BY ID
    @DeleteMapping("/categories/{categoryId}")
    public String deleteCategoryById(@PathVariable Long categoryId){
        System.out.println("calling deleteCategoryById ==>");
        return categoryService.deleteCategoryById(categoryId);
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
        System.out.println("calling createCategoryBook ==>");
        return categoryService.createCategoryBook(categoryId, bookObject);
    }

    @GetMapping("/categories/{categoryId}/books")
    public List<Book> getCategoryBooks(@PathVariable Long categoryId){
        System.out.println("calling getCategoryBooks ==>");
        return categoryService.getCategoryBooks(categoryId);
    }

    @GetMapping("/categories/{categoryId}/books/{bookId}")
    public Book getCategoryBook(@PathVariable Long categoryId,
                                @PathVariable Long bookId) {
        System.out.println("calling getCategoryBook ==>");
        return categoryService.getCategoryBook(categoryId, bookId);


    }

    @PutMapping("/categories/{categoryId}/books/{bookId}")
    public Book updateByCategoryIdAndBookId(@PathVariable Long categoryId,
                                              @PathVariable Long bookId,
                                              @RequestBody Book bookObj){
        System.out.println("calling updateByCategoryIdAndBookId ==>");

        return categoryService.updateByCategoryIdAndBookId(categoryId, bookId, bookObj);
    }

    @DeleteMapping("/categories/{categoryId}/books/{bookId}")
    public ResponseEntity<HashMap> deleteByCategoryIdAndBookId(
            @PathVariable(value = "categoryId") Long categoryId,
            @PathVariable(value = "bookId") Long bookId) {
        System.out.println("calling deleteByCategoryIdAndBookId ==>");
        categoryService.deleteByCategoryIdAndBookId(categoryId, bookId);
        HashMap responseMessage = new HashMap();
        responseMessage.put("status", "book with ID : " + bookId + " was successfully deleted.");
        return new ResponseEntity<HashMap>(responseMessage, HttpStatus.OK);
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
