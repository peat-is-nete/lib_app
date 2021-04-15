package com.lib.library.repository;

import com.lib.library.model.Book;
import com.lib.library.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    List<Checkout> findByUserId(Long userId);
    Checkout getCheckoutByUserIdAndBookId(Long userID, Long bookId);
    List<Checkout> getAllCheckoutsByUserId(Long checkoutId);
    Optional<Checkout> findByBookId(Long bookId);
}
