//package com.nsi.clonebin.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class AuthenticationServiceImpl implements AuthenticationService {
//
//    private final AuthenticationManager authenticationManager;
//    private final UsersDetailsService usersDetailsService;
//
//    @Autowired
//    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UsersDetailsService usersDetailsService) {
//        this.authenticationManager = authenticationManager;
//        this.usersDetailsService = usersDetailsService;
//    }
//    @Override
//    public boolean isAuthenticated() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
//            return false;
//        }
//        return authentication.isAuthenticated();
//    }
//
//    @Override
//    public void autoLogin(String username, String password) {
//        UserDetails usersDetails = usersDetailsService.loadUserByUsername(username);
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(usersDetails, password, usersDetails.getAuthorities());
//
//        authenticationManager.authenticate(token);
//
//        if (token.isAuthenticated()) {
//            SecurityContextHolder.getContext().setAuthentication(token);
//            log.debug(String.format("Auto login %s successfully!", username));
//        }
//    }
//}
