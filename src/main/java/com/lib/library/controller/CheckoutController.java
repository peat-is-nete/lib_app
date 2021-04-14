package com.lib.library.controller;

import com.lib.library.model.Checkout;
import com.lib.library.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @DeleteMapping(path = "/{userId}/{bookId}")
    public ResponseEntity<HashMap> deleteCheckout(@PathVariable(value = "userId") Long userId,
                                                  @PathVariable(value = "bookId") Long bookId) {

        System.out.println("Calling deleteCheckout");
        checkoutService.deleteCheckout(userId,bookId);
        HashMap responseMessage = new HashMap();
        responseMessage.put("status", "Checkout with userID : " + userId + " and bookID : " +
                                       bookId + " was successfully deleted.");
        return new ResponseEntity<HashMap>(responseMessage, HttpStatus.OK);

    }


}
