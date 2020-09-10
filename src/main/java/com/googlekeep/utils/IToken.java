package com.googlekeep.utils;

import com.googlekeep.model.UserDetails;
import org.springframework.stereotype.Component;


public interface IToken {
    String generateVerificationToken(UserDetails userDetails);
}
