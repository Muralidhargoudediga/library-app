package com.mediga.library.service;

import com.mediga.library.entity.User;
import com.mediga.library.mapper.UserDetailsMapper;
import com.mediga.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *To authenticate, Spring Security needs user data with user names and password hashes.
 * That’s why we have to implement the UserDetailsService interface.
 * This interface loads user-specific data and needs read-only access to user data.
 */
@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    private UserDetailsMapper userDetailsMapper;


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserDetailsMapper(UserDetailsMapper userDetailsMapper) {
        this.userDetailsMapper = userDetailsMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(s);
        return userDetailsMapper.toUserDetails(user);
    }
}
