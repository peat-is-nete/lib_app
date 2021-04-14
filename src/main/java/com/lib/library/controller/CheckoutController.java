package com.lib.library.controller;

import com.lib.library.model.Checkout;
import com.lib.library.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkouts")
public class CheckoutController {

    private CheckoutService checkoutService;

    @Autowired
    public void setCheckoutService(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping(path = "/{userId}/books")
    public List<Checkout> getBookList(@PathVariable Long userId) {
        System.out.println("Calling getBookList");
        return checkoutService.getCheckoutListByUser(userId);
    }

    @PostMapping(path = "/{userId}/{bookId}")
    public Checkout createCheckout(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody Checkout checkoutObject) {
        System.out.println("Calling updateCheckout");
        return checkoutService.createCheckout(userId, bookId, checkoutObject);

    }
}
