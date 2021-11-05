package com.mediga.library.controller;

import com.mediga.library.entity.User;
import com.mediga.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

   @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        try {
            User user = userService.getUser(id);
            return user;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User responseUser = userService.addUser(user);
            URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/user/{id}").build()
                    .expand(responseUser.getId()).toUri();

            ResponseEntity<User> response = ResponseEntity.created(location).body(responseUser);
            return response;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
