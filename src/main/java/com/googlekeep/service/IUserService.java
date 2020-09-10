package com.googlekeep.service;

import com.googlekeep.dto.LoginDTO;
import com.googlekeep.dto.RegistrationDTO;

public interface IUserService {

    String userRegistration(RegistrationDTO registrationDTO, String referer);

    String userLogin(LoginDTO logInDTO);
}
