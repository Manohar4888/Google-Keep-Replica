package com.googlekeep.utils.implementation;


import com.googlekeep.model.UserDetails;
import com.googlekeep.properties.FileProperties;
import com.googlekeep.utils.IToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Token implements IToken {

    @Autowired
    FileProperties jwtProperties;

    @Override
    public String generateVerificationToken(UserDetails userDetails) {
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setId(String.valueOf(userDetails.id))
                .setSubject(userDetails.fullName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTime + jwtProperties.getVerificationMs()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getJwtSecret())
                .compact();
    }
}
