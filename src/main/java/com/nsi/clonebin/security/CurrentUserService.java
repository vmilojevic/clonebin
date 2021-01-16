package com.nsi.clonebin.security;

import com.nsi.clonebin.model.entity.UserAccount;
import com.nsi.clonebin.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurrentUserService {

    private final UserAccountService userAccountService;

    @Autowired
    public CurrentUserService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Transactional(readOnly = true)
    public UserAccount getCurrentUser() {
        UserAccountDetails user = (UserAccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null && user.getUsername() != null) {
            return userAccountService.getByUsername(user.getUsername());
        } else {
            return null;
        }
    }
}
