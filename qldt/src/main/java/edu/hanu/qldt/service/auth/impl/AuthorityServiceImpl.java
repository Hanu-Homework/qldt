package edu.hanu.qldt.service.auth.impl;

import edu.hanu.qldt.model.user.Authority;
import edu.hanu.qldt.model.user.UserRoleName;
import edu.hanu.qldt.repository.user.AuthorityRepository;
import edu.hanu.qldt.service.auth.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void save(UserRoleName userRoleName) {
        authorityRepository.saveAuth(userRoleName.toString());
    }

    @Override
    public List<Authority> findById(Long id) {
        Authority authority = authorityRepository
                .findById(id).orElse(null);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        return authorities;
    }

    @Override
    public List<Authority> findByName(String name) {
        Authority authority = getAuthority(name);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        return authorities;
    }

    private Authority getAuthority(String name) {
        for(Authority authority : authorityRepository.findAll()) {
            if(authority.getAuthority().equals(name)) {
                return authority;
            }
        }
        return null;
    }
}
