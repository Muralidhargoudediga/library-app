package com.mediga.library.mapper;

import com.mediga.library.UserDto;
import com.mediga.library.entity.Role;
import com.mediga.library.entity.User;
import com.mediga.library.entity.UserRole;
import com.mediga.library.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User mapToUser(UserDto userDto, RoleRepository roleRepository) {
        User user = new User();
        user.setName(userDto.getName());
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());

        if(userDto.getRoles() != null) {
            List<Role> roles = new ArrayList<>();
            List<Role> userRoles = userDto.getRoles().stream().map(role -> {
                role = "role_" + role;
                return roleRepository.findByName(UserRole.valueOf(role.toUpperCase()));
            }).collect(Collectors.toList());
            user.setRoles(userRoles);
        }

        return user;
    }
}
