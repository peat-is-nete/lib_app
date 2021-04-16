package com.lib.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CheckoutLimitReachException extends RuntimeException {

    public CheckoutLimitReachException(String message) {
        super (message);
    }
}
