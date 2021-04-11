package com.lib.library.service;

import com.lib.library.model.User;
import com.lib.library.repository.UserRepository;
import com.lib.library.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getTest() {
        System.out.println("test method");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        // User user = userRepository.findByEmailAddress(userDetails.getUsername());
        User user = userDetails.getUser();
        if (isAdmin(user)) {
            System.out.println("is authorized");
        }

        System.out.println("Hello World!");
        return "Hello world";
    }

    public String putTest() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userDetails.getUser();
        if (isAdmin(user)) {
            return "Greetings";
        }

        throw new AccessDeniedException("Sorry you are not admin");
    }

    private boolean isAdmin(User user) {
        return user.getUserRole().getType() == 1;
    }
}
