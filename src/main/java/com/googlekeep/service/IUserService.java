package com.googlekeep.service;

import com.googlekeep.dto.LoginDTO;
import com.googlekeep.dto.RegistrationDTO;

import javax.mail.MessagingException;

public interface IUserService {

    String userRegistration(RegistrationDTO registrationDTO);

    String userLogin(LoginDTO logInDTO);

}

