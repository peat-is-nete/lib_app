package com.lib.library.service;

import com.lib.library.exception.DataExistException;
import com.lib.library.exception.DataNotFoundException;
import com.lib.library.model.Category;
import com.lib.library.model.User;
import com.lib.library.repository.BookRepository;
import com.lib.library.repository.CategoryRepository;
import com.lib.library.repository.UserRepository;
import com.lib.library.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CategoryService {
    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private CategoryRepository categoryRepository;
    private BookRepository bookRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setBookRepository( BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Category> getCategories() {
        System.out.println("Getting categories list ");

        User user = getUserWithUserDetails();
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()){
            throw new DataNotFoundException("No categories found.");
        }

        return categories;
    }

    public Category createCategory(@RequestBody Category categoryObject) {
        System.out.println("calling createCategory ==>");
        User user = getUserWithUserDetails();

        if (!isAdmin(user)) {
           throw new AccessDeniedException("Sorry, you are not authorized to create a category as you are not admin.");
        }

        Category category = categoryRepository.findByName(categoryObject.getName());
        if (category != null){
            throw new DataExistException(" Category with name" + category.getName() + "already exist");
        } else {
            return categoryRepository.save(categoryObject);
        }






    }










//    public String getTest() {
//        System.out.println("test method");
//        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
//                .getPrincipal();
//        // User user = userRepository.findByEmailAddress(userDetails.getUsername());
//        User user = userDetails.getUser();
//        if (isAdmin(user)) {
//            System.out.println("is authorized");
//        }
//
//        System.out.println("Hello World!");
//        return "Hello world";
//    }
//
//    public String putTest() {
//        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
//                .getPrincipal();
//        User user = userDetails.getUser();
//        if (isAdmin(user)) {
//            return "Greetings";
//        }
//
//        throw new AccessDeniedException("Sorry you are not admin");
//    }

    private boolean isAdmin(User user) {
        return user.getUserRole().getType() == 1;
    }

    private User getUserWithUserDetails() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
               .getPrincipal();
       return userDetails.getUser();
    }
}
