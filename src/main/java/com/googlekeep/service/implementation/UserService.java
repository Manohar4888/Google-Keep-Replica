package com.googlekeep.service.implementation;

import com.googlekeep.dto.LoginDTO;
import com.googlekeep.dto.RegistrationDTO;
import com.googlekeep.exceptions.UserServiceException;
import com.googlekeep.model.UserDetails;
import com.googlekeep.repository.IUserRepository;
import com.googlekeep.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public String userRegistration(RegistrationDTO registrationDTO, String referer) {
        Optional<UserDetails> userDetail = userRepository.findByEmailID(registrationDTO.emailID);
        if (userDetail.isPresent()) {
            throw new UserServiceException("USER ALREADY EXISTS WITH THIS EMAIL ID");
        }
        String password = bCryptPasswordEncoder.encode(registrationDTO.password);
        UserDetails userDetails = new UserDetails(registrationDTO);
        userDetails.password = password;
        userRepository.save(userDetails);
        return "Verification Mail Has Been Sent Successfully";
    }

    @Override
    public String userLogin(LoginDTO logInDTO) {
        Optional<UserDetails> userDetail = userRepository.findByEmailID(logInDTO.emailID);
        if (userDetail.isPresent()) {
            boolean password = bCryptPasswordEncoder.matches(logInDTO.password, userDetail.get().password);
        }
        throw new UserServiceException("INCORRECT PASSWORD");
    }
}