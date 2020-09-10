package com.googlekeep.utils;

import com.googlekeep.model.UserDetails;

public interface IToken {

     String generateVerificationToken(UserDetails userDetails);

}
