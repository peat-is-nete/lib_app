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

    // Get all the categories of books
    @GetMapping("/categories")
    public List<Category> getCategories() {
        System.out.println("Calling getCategories ==>");
        return categoryService.getCategories();
    }

    // Add a category of books
    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category categoryObject) {
        System.out.println("Calling createCategory ==>");
        return categoryService.createCategory(categoryObject);
    }

    // Delete category by categoryId
    @DeleteMapping("/categories/{categoryId}")
    public String deleteCategoryById(@PathVariable Long categoryId){
        System.out.println("Calling deleteCategoryById ==>");
        return categoryService.deleteCategoryById(categoryId);
    }

    // Get a category by categoryId
    @GetMapping(path = "/categories/{categoryId}")
    public Category getCategory(@PathVariable Long categoryId) {
        System.out.println("Calling getCategory ==>");
        return categoryService.getCategory(categoryId);
    }

    // Update a category by categoryId
    @PutMapping("/categories/{categoryId}")
    public Category updateCategory(@PathVariable Long categoryId, @RequestBody Category categoryObject) {
        System.out.println("Calling updateCategory ==>");
        return categoryService.updateCategory(categoryId, categoryObject);
    }

    // Add a book by categoryId
    @PostMapping("/categories/{categoryId}/books")
    public Book createCategoryBook(@PathVariable Long categoryId, @RequestBody Book bookObject){
        System.out.println("Calling createCategoryBook ==>");
        return categoryService.createCategoryBook(categoryId, bookObject);
    }

    // Get all the books under a particular categoryId
    @GetMapping("/categories/{categoryId}/books")
    public List<Book> getCategoryBooks(@PathVariable Long categoryId){
        System.out.println("Calling getCategoryBooks ==>");
        return categoryService.getCategoryBooks(categoryId);
    }
    // Get a particular book under a particular categoryId
    @GetMapping("/categories/{categoryId}/books/{bookId}")
    public Book getCategoryBook(@PathVariable Long categoryId,
                                @PathVariable Long bookId) {
        System.out.println("Calling getCategoryBook ==>");
        return categoryService.getCategoryBook(categoryId, bookId);
    }

    // Update a book under a particular categoryId
    @PutMapping("/categories/{categoryId}/books/{bookId}")
    public Book updateByCategoryIdAndBookId(@PathVariable Long categoryId,
                                              @PathVariable Long bookId,
                                              @RequestBody Book bookObj){
        System.out.println("Calling updateByCategoryIdAndBookId ==>");
        return categoryService.updateByCategoryIdAndBookId(categoryId, bookId, bookObj);
    }

    // Delete a book under a particular categoryId
    @DeleteMapping("/categories/{categoryId}/books/{bookId}")
    public ResponseEntity<HashMap> deleteByCategoryIdAndBookId(
            @PathVariable(value = "categoryId") Long categoryId,
            @PathVariable(value = "bookId") Long bookId) {
        System.out.println("Calling deleteByCategoryIdAndBookId ==>");
        categoryService.deleteByCategoryIdAndBookId(categoryId, bookId);
        HashMap responseMessage = new HashMap();
        responseMessage.put("status", "book with ID : " + bookId + " was successfully deleted.");
        return new ResponseEntity<HashMap>(responseMessage, HttpStatus.OK);
    }
}
