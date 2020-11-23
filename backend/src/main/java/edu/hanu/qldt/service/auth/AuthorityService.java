package edu.hanu.qldt.service.auth;

import edu.hanu.qldt.model.user.Authority;
import edu.hanu.qldt.model.user.UserRoleName;

import java.util.List;

public interface AuthorityService {

    void save(UserRoleName userRoleName);
    List<Authority> findById(Long id);
    List<Authority> findByName(String name);
}
