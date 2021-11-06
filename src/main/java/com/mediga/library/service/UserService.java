package com.mediga.library.service;

import com.mediga.library.UserDto;
import com.mediga.library.entity.Role;
import com.mediga.library.entity.User;
import com.mediga.library.entity.UserRole;
import com.mediga.library.exception.UserNotFoundException;
import com.mediga.library.helper.PatchHelper;
import com.mediga.library.jwt.JwtRequest;
import com.mediga.library.jwt.JwtResponse;
import com.mediga.library.jwt.JwtTokenProvider;
import com.mediga.library.mapper.UserMapper;
import com.mediga.library.repository.RoleRepository;
import com.mediga.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.json.JsonMergePatch;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private PatchHelper patchHelper;
    @Autowired
    private RoleRepository roleRepository;

    public User addUser(UserDto userDto) {
        if(userDto == null) {
            throw new IllegalArgumentException("user cannot be null : ");
        }

        User user = UserMapper.mapToUser(userDto, roleRepository);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepository.save(user);
        return user;
    }

    public JwtResponse authenticate(JwtRequest jwtRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
        User user = userRepository.findByUserName(jwtRequest.getUserName());
        String jwtToken = jwtTokenProvider.createToken(user.getUserName(), user.getRoles());
        return new JwtResponse(jwtToken);
    }

    public User getUser(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()) {
            throw new UserNotFoundException("User with id : " + id + " is not found");
        }
        return optionalUser.get();
    }

    public User updateUser(Long userId, JsonMergePatch jsonMergePatch) {
        User user = getUser(userId);
        UserDto userDto = new UserDto();
        userDto = patchHelper.mergePatch(jsonMergePatch, userDto, UserDto.class);
        List<Role> userRoles = userDto.getRoles().stream().map(role -> {
            role = "role_" + role;
            return roleRepository.findByName(UserRole.valueOf(role.toUpperCase()));
        }).collect(Collectors.toList());
        user.setRoles(userRoles);
        userRepository.save(user);
        return user;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setPatchHelper(PatchHelper patchHelper) {
        this.patchHelper = patchHelper;
    }
}
