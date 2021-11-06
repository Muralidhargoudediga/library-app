package com.mediga.library.mapper;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserDetailsMapper {
    public User toUserDetails(com.mediga.library.entity.User user) {
        return new User(user.getUserName(), user.getPassword(),
                user.getRoles().stream().filter(Objects::nonNull).
                        map(s -> new SimpleGrantedAuthority(s.getName().getAuthority())).collect(Collectors.toList()));
    }
}
