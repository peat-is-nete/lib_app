package com.lib.library.service;

import com.lib.library.exception.CheckoutLimitReachException;
import com.lib.library.exception.DataExistException;
import com.lib.library.exception.DataNotFoundException;
import com.lib.library.model.Book;
import com.lib.library.model.Checkout;
import com.lib.library.model.User;
import com.lib.library.repository.BookRepository;
import com.lib.library.repository.CheckoutRepository;
import com.lib.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CheckoutService {

    UserRepository userRepository;
    BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;
    private UserService userService;
    private final int CHECKOUT_LIMIT = 5;
    private final int RENEWAL_DAYS = 21;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setCheckoutRepository(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<Checkout> getCheckoutListByUser(Long userId)  {
        System.out.println("Service calling getBookList");
        List<Checkout> checkouts = checkoutRepository.findByUserId(userId);
        if(checkouts == null || checkouts.isEmpty()) {
            throw new DataNotFoundException("Checkouts for user "  + userId + " do not exist");
        }
        return checkouts;
    }

    public Checkout createCheckout(Long userId, Long bookId, Checkout checkoutObject) {
        System.out.println("Calling createCheckout");
        User user = userService.getUserWithUserDetails();
        if (!userService.isAdmin(user)) {
            throw new AccessDeniedException("Sorry, you are not authorized to update a checkout as you are not admin.");
        }

        Checkout checkout = checkoutRepository.getCheckoutByUserIdAndBookId(user.getId(), bookId);
        if (checkout != null) {
            throw new DataExistException("Checkout with userID " + user.getId() + " already has "  + checkout.getBook().getTitle()  + " book.");
        }
        List<Checkout> checkoutList = checkoutRepository.getAllCheckoutsByUserId(user.getId());
        if(checkoutList.size() >= CHECKOUT_LIMIT){
            throw new CheckoutLimitReachException("Checkout Limit of " + checkoutList.size() + " has been reached.");
        }

        Optional<Book> book = bookRepository.findById(bookId);
        if(book.isEmpty()) {
            throw new DataNotFoundException("Book with bookId " + bookId  + " not found");
        }

        // get user that is checking out book by userId
        Optional<User> borrowingUser = userRepository.findById(userId);
        if(borrowingUser.isEmpty()) {
            throw new DataNotFoundException("User with userId " + userId + "not found");
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, RENEWAL_DAYS);
        Date dueDate = c.getTime();

        checkoutObject.setCheckoutDate(new Date());
        checkoutObject.setDueDate(dueDate);
        checkoutObject.setBook(book.get());
        checkoutObject.setUser(borrowingUser.get());
        return checkoutRepository.save(checkoutObject);
    }

    public Checkout updateCheckout(Long checkoutId) {
        System.out.println("Calling updateCheckout");
        User user = userService.getUserWithUserDetails();
        if(!userService.isAdmin(user)) {
            throw new AccessDeniedException("Sorry, you are not authorized to update a checkout as you are not admin.");
        }

        Optional<Checkout> checkout  = checkoutRepository.findById(checkoutId);

        if (checkout.isEmpty()) {
            throw new DataNotFoundException("Checkout with checkoutId " + checkoutId + " does not exist.");
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, RENEWAL_DAYS);
        Date dueDate = c.getTime();
        checkout.get().setDueDate(dueDate);

        return checkoutRepository.save(checkout.get());
    }

    public void deleteCheckout(Long userId, Long bookId) {
        System.out.println("Service calling deleteCheckout ==>");

        // Getting user access rights from JWT
        User user = userService.getUserWithUserDetails();

        // If login is not by an admin
        if (!userService.isAdmin(user)) {
            throw new AccessDeniedException("Sorry, you are not authorized to delete a checkout as you are not admin.");
        }

        // Searching whole user database for userId
        Optional<User> borrowingUser = userRepository.findById(userId);
        if(borrowingUser.isEmpty()) {
            throw new DataNotFoundException("There is no such user in database with userId : " + userId + " .");
        }

        // Finding entry in Checkout table matching userId and bookId
        Checkout checkout1 = checkoutRepository.getCheckoutByUserIdAndBookId(userId, bookId);
        if (checkout1 == null) {
            throw new DataExistException("Checkout entry matching the userId: " + userId +
                                         " and bookId: " + bookId + " does not exist.");
        }git 

        checkoutRepository.deleteById(checkout1.getId());
    }
} // END OF CLASS
