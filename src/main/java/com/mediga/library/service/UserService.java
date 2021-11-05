package com.mediga.library.service;

import com.mediga.library.entity.User;
import com.mediga.library.exception.UserNotFoundException;
import com.mediga.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public User addUser(User user) {
        if(user == null) {
            throw new IllegalArgumentException("user cannot be null : ");
        }

        return userRepository.save(user);
    }

    public User getUser(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()) {
            throw new UserNotFoundException("User with id : " + id + " is not found");
        }
        return optionalUser.get();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
