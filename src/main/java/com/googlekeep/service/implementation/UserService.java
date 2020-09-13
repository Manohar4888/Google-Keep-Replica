package com.googlekeep.service.implementation;

import com.googlekeep.dto.LoginDTO;
import com.googlekeep.dto.RegistrationDTO;
import com.googlekeep.exceptions.UserServiceException;
import com.googlekeep.model.UserDetails;
import com.googlekeep.repository.IUserRepository;
import com.googlekeep.service.IUserService;
import com.googlekeep.utils.IToken;
import com.googlekeep.utils.implementation.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    IToken jwtToken;

    @Autowired
    MailService mailService;

    @Override
    public String userRegistration(RegistrationDTO registrationDTO) throws MessagingException {
        Optional<UserDetails> userDetail = userRepository.findByEmailID(registrationDTO.emailID);
        if (userDetail.isPresent()) {
            throw new UserServiceException("USER ALREADY EXISTS WITH THIS EMAIL ID");
        }
        String password = bCryptPasswordEncoder.encode(registrationDTO.password);
        UserDetails userDetails = new UserDetails(registrationDTO);
        userDetails.password = password;
        userRepository.save(userDetails);
        sendVerificationMail(registrationDTO.emailID);
        return "Verification Mail Has Been Sent Successfully";
    }

    @Override
    public String userLogin(LoginDTO logInDTO) {
        Optional<UserDetails> userDetail = userRepository.findByEmailID(logInDTO.emailID);
        if (userDetail.isPresent()) {
            boolean password = bCryptPasswordEncoder.matches(logInDTO.password, userDetail.get().password);
            if (password) {
                return "Login Successful";
            }
        }
        throw new UserServiceException("INCORRECT PASSWORD");
    }

    @Override
    public String sendVerificationMail(String email) throws MessagingException {
        UserDetails user = userRepository.findByEmailID(email).orElseThrow(() -> new UserServiceException("User Not Found"));
        String token = jwtToken.generateVerificationToken(user);
        String subject = "Email Verification";
        mailService.sendMail(token, subject, user.emailID);
        return "Verification Mail Has Been Sent Successfully";
    }
}