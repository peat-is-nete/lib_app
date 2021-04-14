package com.lib.library.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class CheckedBookRemovalException extends RuntimeException{

    public CheckedBookRemovalException(String message) {
        super (message);
    }

}
