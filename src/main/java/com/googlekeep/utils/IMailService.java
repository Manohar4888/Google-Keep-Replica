package com.googlekeep.utils;
import javax.mail.MessagingException;

public interface IMailService {
    String sendMail(String body,String subject,String emailID) throws MessagingException;
}
