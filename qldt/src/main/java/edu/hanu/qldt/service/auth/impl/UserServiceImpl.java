package edu.hanu.qldt.service.auth.impl;

import edu.hanu.qldt.dto.response.UserResponseDTO;
import edu.hanu.qldt.exception.CustomException;
import edu.hanu.qldt.model.user.Authority;
import edu.hanu.qldt.model.user.User;
import edu.hanu.qldt.repository.user.UserRepository;
import edu.hanu.qldt.service.auth.AuthorityService;
import edu.hanu.qldt.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityService authService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityService authService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @Override
    public User save(UserResponseDTO userResponseDTO) {
        if(!userRepository.existsByUsername(userResponseDTO.getUsername())) {
            User newUser = new User();
            newUser.setUsername(userResponseDTO.getUsername());
            newUser.setPassword(passwordEncoder.encode(userResponseDTO.getPassword()));
            newUser.setFullName(userResponseDTO.getFullName());
            List<Authority> authorities = authService.findByName(userResponseDTO.getRole());
            newUser.setAuthorities(authorities);
            userRepository.save(newUser);
            return newUser;
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public User update(Long id, UserResponseDTO userResponseDTO) {
        User user = userRepository.getOne(id);

        if(!userRepository.existsByUsername(userResponseDTO.getUsername())){
            user.setUsername(userResponseDTO.getUsername());
            if(userResponseDTO.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(userResponseDTO.getPassword()));
            }
            user.setFullName(userResponseDTO.getFullName());
            userRepository.save(user);
            return user;
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public String delete(Long user_id) {
        User user = userRepository.getOne(user_id);
        userRepository.delete(user);
        return user_id.toString();
    }

    @Override
    public boolean isUsernameUnique(String username) {
        return !userRepository.existsByUsername(username);
    }

    @Override
    public void resetCredentials(String username) {
        User user = userRepository.findByUsername(username);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository
                .findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


}
