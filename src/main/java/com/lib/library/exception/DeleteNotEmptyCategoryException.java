package com.lib.library.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class DeleteNotEmptyCategoryException extends RuntimeException {

    public DeleteNotEmptyCategoryException(String message) {
        super (message);
    }

}
