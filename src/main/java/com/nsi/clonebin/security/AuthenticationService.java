package com.nsi.clonebin.security;

public interface AuthenticationService {

    boolean isAuthenticated();

    void autoLogin(String username, String password);
}
