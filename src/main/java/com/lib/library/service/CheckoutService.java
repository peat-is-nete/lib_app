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
    private CheckoutRepository checkoutRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private final int CHECKOUT_LIMIT = 5;

    @Autowired
    public void setCheckoutRepository(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private UserService userService = new UserService();

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
        Optional <User> borrowingUser = userRepository.findById(userId);
        if(borrowingUser.isPresent()) {
            throw new DataNotFoundException("User with userId " + userId + "not found");
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 21);
        Date dueDate = c.getTime();

        checkoutObject.setCheckoutDate(new Date());
        checkoutObject.setDueDate(dueDate);
        checkoutObject.setBook(book.get());
        checkoutObject.setUser(borrowingUser.get());
        return checkoutRepository.save(checkoutObject);
    }


}
