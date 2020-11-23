package edu.hanu.qldt.service.auth;

import edu.hanu.qldt.dto.response.UserResponseDTO;
import edu.hanu.qldt.model.user.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    void resetCredentials(String username);
    User findById(Long id);
    User save(UserResponseDTO userResponseDTO);
    User update(Long id, UserResponseDTO userResponseDTO);
    String delete(Long user_id);
    boolean isUsernameUnique(String username);
}
