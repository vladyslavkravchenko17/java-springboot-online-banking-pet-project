package com.project.service;

import com.project.domain.User;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    public void sendActivationMail(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Vl site. Please follow this link to activate your account: http://localhost:8080/activate/%s"
                    , user.getFirstName()
                    , user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation Code", message);
        }
    }

    public void sendPasswordMail(User user){
        String message = String.format(
                "Hello, %s \n" +
                        "You requested changing the password of your account on Brawl-VL! \n" +
                        "To change it, please follow this link: http://localhost:8080/changing-password/%s \n" +
                        "If you didn't request any password changing - just ignore this mail."
                , user.getFirstName()
                , user.getActivationCode());
        mailSender.send(user.getEmail(), "Password changing", message);
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);
        return true;
    }
}