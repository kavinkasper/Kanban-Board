package com.example.kanbanService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Team_Member Already Exists")
public class TeamMemberAlreadyExists extends Exception{
}
