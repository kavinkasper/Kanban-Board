package com.example.UserAuthenticationService.Security;

import com.example.UserAuthenticationService.Domain.User;

public interface SecurityTokenGenerator {
    String createToken(User user);
}
