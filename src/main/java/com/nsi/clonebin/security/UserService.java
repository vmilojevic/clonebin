package com.nsi.clonebin.security;

import com.nsi.clonebin.model.dto.UserRegistrationDTO;
import com.nsi.clonebin.model.entity.User;
import com.nsi.clonebin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with username %s not found.", username));
        }
        return new UsersDetailsDTO(user);
    }

    @Transactional
    public User save(UserRegistrationDTO registrationDTO) {
        User user = new User(registrationDTO.getUsername(), passwordEncoder.encode(registrationDTO.getPassword()));
        return userRepository.save(user);
    }
}
