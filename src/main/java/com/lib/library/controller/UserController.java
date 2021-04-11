package com.lib.library.controller;

import com.lib.library.model.User;
import com.lib.library.model.request.LoginRequest;
import com.lib.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth/users")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User createUser(@RequestBody User userObject) {
        System.out.println("Calling createUser");
        System.out.println(userObject.toString());

        return userService.createUser(userObject);
    }

    // http://localhost:PORT-NUMBER/auth/users/login
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest){
        System.out.println("Calling loginUser");
        return userService.loginUser(loginRequest);
    }
}
