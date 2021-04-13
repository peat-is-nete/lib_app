package com.lib.library.service;

import com.lib.library.exception.DataExistException;
import com.lib.library.exception.DataNotFoundException;
import com.lib.library.model.Book;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

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
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Category> getCategories() {
        System.out.println("Getting categories list ");
        getUserWithUserDetails(); // for authentication
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
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
        if (category != null) {
            throw new DataExistException(" Category with name" + category.getName() + "already exist");
        } else {
            return categoryRepository.save(categoryObject);
        }
    }

    public Category getCategory(Long categoryId) {
        System.out.println("service getCategory ==>");
        User user = getUserWithUserDetails(); // for authentication
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new DataNotFoundException("category with id " + categoryId + " not found");
        } else {
            return category.get();
        }
    }

    public Category updateCategory(Long categoryId, @RequestBody Category categoryObject) {
        System.out.println("Service calling updateCategory ==>");
        User user = getUserWithUserDetails();

        if (!isAdmin(user)) {
            throw new AccessDeniedException("Sorry, you are not authorized to create a category as you are not admin.");
        }
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new DataNotFoundException("category with id " + categoryId + " not found");
        } else {
            category.get().setDescription(categoryObject.getDescription());
            category.get().setName(categoryObject.getName());
            return categoryRepository.save(category.get());
        }
    }

    public Book createCategoryBook(Long categoryId, Book bookObject) {
        System.out.println("calling createCategory book ");
        User user  = getUserWithUserDetails();

        if (!isAdmin(user)) {
            throw new AccessDeniedException("Sorry, you are not authorized to create a category as you are not admin.");
        }

        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new DataNotFoundException("category with id " + categoryId + " not found");
        } else {
            bookObject.setCategory(category.get());
            return bookRepository.save(bookObject);
        }
    }

    public List<Book> getCategoryBooks(Long categoryId) {
        System.out.println("service calling getCategoryBooks ==>");
        User user = getUserWithUserDetails();// for authentication
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new DataNotFoundException("category with id " + categoryId + " not found");
        }
        return  category.get().getBookList();
    }

    // * * * Auxiliary Methods * * *
    private boolean isAdmin(User user) {
        return user.getUserRole().getType() == 1;
    }

    private User getUserWithUserDetails() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
               .getPrincipal();
       return userDetails.getUser();
    }
    // *****************************

    public String deleteCategoryById(Long categoryId){
        System.out.println("service calling deleteCategoryById ==>");

        User user = getUserWithUserDetails();

        if (!isAdmin(user)) {
            throw new AccessDeniedException("Sorry, you are not authorized to delete a category as you are not admin.");
        }

        Optional<Category> category = categoryRepository.findById(categoryId);

        if( category.isPresent() ){
            categoryRepository.deleteById(categoryId);
            return "Category with Id: " + categoryId + " was successfully deleted.";
        } else {
            throw new DataNotFoundException("The category with ID: " + categoryId +
                                            " does not exist.");
        }

    }

    public Book getCategoryBook(Long categoryId, Long bookId) {
        System.out.println("service calling getCategoryBook ==>");

        User user = getUserWithUserDetails();

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isEmpty()) {
            throw new DataNotFoundException("The category with ID: " + categoryId +
                    " does not exist.");
        }

        Optional<Book> book = bookRepository.findByCategoryId(categoryId).stream().filter(p -> p.getId().equals(bookId)).findFirst();
        if (book.isEmpty()) {
            throw new DataNotFoundException("The book with ID: " + bookId +
                    " doesn't exist.");
        }
        return book.get();
    }


    public Book updateByCategoryIdAndBookId(Long categoryId,
                                            Long bookId,
                                            Book bookObj) {
        System.out.println("service calling updateByCategoryIdAndBookId ==>");

        User user = getUserWithUserDetails();

        if (!isAdmin(user)) {
            throw new AccessDeniedException("Sorry, you are not authorized to update a category as you are not admin.");
        }

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isEmpty()) {
            throw new DataNotFoundException("Category with ID: " + categoryId + " does not exist.");
        }

        Optional<Book> book = bookRepository.findByCategoryId(categoryId).stream()
                .filter(p -> p.getId().equals(bookId)).findFirst();

        if (book.isEmpty()) {
            throw new DataNotFoundException("Book with ID: " + bookId + " does not exist.");
        }

        book.get().setTitle(bookObj.getTitle());
        book.get().setAuthor(bookObj.getAuthor());
        book.get().setPublisher(bookObj.getPublisher());
        return bookRepository.save(book.get());

    }












} // END OF CLASS
