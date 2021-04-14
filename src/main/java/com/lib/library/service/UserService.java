package com.lib.library.service;

import com.lib.library.exception.DataExistException;
import com.lib.library.exception.DataNotFoundException;
import com.lib.library.model.User;
import com.lib.library.model.UserRole;
import com.lib.library.model.request.LoginRequest;
import com.lib.library.model.response.LoginResponse;
import com.lib.library.repository.UserRepository;
import com.lib.library.repository.UserRoleRepository;
import com.lib.library.security.JwtUtils;
import com.lib.library.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }
    // * * * Auxiliary Methods * * *
    public boolean isAdmin(User user) {
        return user.getUserRole().getType() == 1;
    }

    public User getUserWithUserDetails() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUser();
    }
    // *****************************

    public User createUser(User userObject){
        System.out.println("Calling createUser");
        if (!userRepository.existsByEmailAddress(userObject.getPassword())){
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            // to set userRole by roleType that comes from request
            UserRole userRole = userRoleRepository.getUserRoleByType(userObject.getRoleType());
            userObject.setUserRole(userRole);
            return (User) userRepository.save(userObject);
        } else {
            throw new DataExistException("User with that emailAddress " + userObject.getEmailAddress() + " already exist");
        }
    }

    public ResponseEntity<Object> loginUser(LoginRequest loginRequest){
        System.out.println("service calling login");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                    loginRequest.getPassword()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            final String JWT = jwtUtils.generateToken(userDetails);
            return ResponseEntity.ok(new LoginResponse(JWT));
        } catch (NullPointerException e) {
            throw new DataNotFoundException("User with this emailAddress " + " not found");
        }
    }

    public User findUserByEmailAddress(String email) {
        return userRepository.findByEmailAddress(email);
    }
}
