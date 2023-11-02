package com.example.UserAuthenticationService.Security;

import com.example.UserAuthenticationService.Domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTSecurityTokenGeneratorImpl implements SecurityTokenGenerator {
    public String createToken(User user){
        Map<String,Object> obj=new HashMap<>();
        obj.put("USER",user.getEmail());
        return generateToken(obj,user.getEmail());
    }

    public String generateToken(Map<String,Object> claims,String subject) {
        String jwtToken = Jwts.builder()
                .setIssuer("user")
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"MYSCRET")
                .compact();
        return jwtToken;
    }


}
