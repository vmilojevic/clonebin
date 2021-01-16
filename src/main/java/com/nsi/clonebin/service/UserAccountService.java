package com.nsi.clonebin.service;

import com.nsi.clonebin.model.dto.UserRegistrationDTO;
import com.nsi.clonebin.model.entity.UserAccount;
import com.nsi.clonebin.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserAccount save(UserRegistrationDTO registrationDTO) {
        UserAccount userAccount = new UserAccount(registrationDTO.getUsername(),
                passwordEncoder.encode(registrationDTO.getPassword()));
        return userAccountRepository.save(userAccount);
    }

    @Transactional(readOnly = true)
    public UserAccount getByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public UserAccount getById(UUID id) {
        return userAccountRepository.getOne(id);
    }
}
