package com.example.UserAuthenticationService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Customer Already Present")
public class UserAlreadyExistsException extends Exception{
}
