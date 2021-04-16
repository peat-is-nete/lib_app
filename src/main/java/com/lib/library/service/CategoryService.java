package com.lib.library.service;

import com.lib.library.exception.CheckedBookRemovalException;
import com.lib.library.exception.DataExistException;
import com.lib.library.exception.DataNotFoundException;
import com.lib.library.exception.DeleteNotEmptyCategoryException;
import com.lib.library.model.Book;
import com.lib.library.model.Category;
import com.lib.library.model.Checkout;
import com.lib.library.model.User;
import com.lib.library.repository.BookRepository;
import com.lib.library.repository.CategoryRepository;
import com.lib.library.repository.CheckoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private BookRepository bookRepository;
    private UserService userService;
    private CheckoutRepository checkoutRepository;

    @Autowired
    public void setCheckoutRepository(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<Category> getCategories() {
        System.out.println("Getting categories list ==> ");
        userService.getUserWithUserDetails(); // for authentication
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new DataNotFoundException("No categories found.");
        }

        return categories;
    }

    public Category createCategory(Category categoryObject) {
        System.out.println("Calling createCategory ==>");
        User user = userService.getUserWithUserDetails();

        if (!userService.isAdmin(user)) {
            throw new AccessDeniedException("Sorry, you are not authorized to create a category as you are not admin.");
        }

        Category category = categoryRepository.findByName(categoryObject.getName());
        if (category != null) {
            throw new DataExistException("Category with name" + category.getName() + "already exist");
        } else {
            return categoryRepository.save(categoryObject);
        }
    }

    public Category getCategory(Long categoryId) {
        System.out.println("Calling getCategory ==>");
        userService.getUserWithUserDetails(); // for authentication
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new DataNotFoundException("Category with id " + categoryId + " not found");
        } else {
            return category.get();
        }
    }

    public Category updateCategory(Long categoryId, Category categoryObject) {
        System.out.println("Calling updateCategory ==>");
        User user = userService.getUserWithUserDetails();

        if (!userService.isAdmin(user)) {
            throw new AccessDeniedException("Sorry, you are not authorized to update a category as you are not admin.");
        }

        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new DataNotFoundException("Category with id " + categoryId + " not found");
        } else {
            category.get().setDescription(categoryObject.getDescription());
            category.get().setName(categoryObject.getName());
            return categoryRepository.save(category.get());
        }
    }

    public Book createCategoryBook(Long categoryId, Book bookObject) {
        System.out.println("Calling createCategory book ==>");
        User user = userService.getUserWithUserDetails();

        if (!userService.isAdmin(user)) {
            throw new AccessDeniedException("Sorry, you are not authorized to add a book as you are not admin.");
        }

        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new DataNotFoundException("Category with id " + categoryId + " not found");
        } else {
            bookObject.setCategory(category.get());
            return bookRepository.save(bookObject);
        }
    }

    public List<Book> getCategoryBooks(Long categoryId) {
        System.out.println("Calling getCategoryBooks ==>");
        userService.getUserWithUserDetails();// for authentication
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new DataNotFoundException("Category with id " + categoryId + " not found");
        }
        return  category.get().getBookList();
    }

    public String deleteCategoryById(Long categoryId){
        System.out.println("Calling deleteCategoryById ==>");

        User user = userService.getUserWithUserDetails();

        if (!userService.isAdmin(user)) {
            throw new AccessDeniedException("Sorry, you are not authorized to delete a category as you are not admin.");
        }

        Optional<Category> category = categoryRepository.findById(categoryId);

        if(category.isPresent()) {
            // Before deleting a category check if it has books
            if(!category.get().getBookList().isEmpty()) {
                throw new DeleteNotEmptyCategoryException("This category contains books, can't delete it.");
            }
            categoryRepository.deleteById(categoryId);
            return "Category with Id: " + categoryId + " was successfully deleted.";
        } else {
            throw new DataNotFoundException("The category with ID: " + categoryId +
                                            " does not exist.");
        }

    }

    public Book getCategoryBook(Long categoryId, Long bookId) {
        System.out.println("Calling getCategoryBook ==>");

         userService.getUserWithUserDetails(); // for authentication

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
        System.out.println("Calling updateByCategoryIdAndBookId ==>");

        User user = userService.getUserWithUserDetails();

        if (!userService.isAdmin(user)) {
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

    public void deleteByCategoryIdAndBookId(Long categoryId, Long bookId) {
        System.out.println("Calling deleteByCategoryIdAndBookId ==>");

        User user = userService.getUserWithUserDetails();

        if (!userService.isAdmin(user)) {
            throw new AccessDeniedException("Sorry, you are not authorized to delete a category as you are not admin.");
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

        // Before deleting a book check if it is checked out
       Optional<Checkout> checkout = checkoutRepository.findById(bookId);
        if (checkout != null) {
            throw new CheckedBookRemovalException("Book is checked out you can't delete it.");

        }

        bookRepository.deleteById(book.get().getId());
    }
} // END OF CLASS
