package com.lib.library.repository;


import com.lib.library.model.Category;
import com.lib.library.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {


}
