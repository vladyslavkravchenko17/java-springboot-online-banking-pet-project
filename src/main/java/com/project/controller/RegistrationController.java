package com.project.controller;

import com.project.domain.User;
import com.project.exception.ActivationCodeNotFoundException;
import com.project.exception.UserAlreadyExistsException;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/registration")
    public String registration(Model model) {
        Map<String, String> errorsMap = (Map<String, String>) model.asMap().get("errorsMap");
        model.mergeAttributes(errorsMap);
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String createNewAccount(@ModelAttribute("user") @Valid User user,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
            if (bindingResult.hasErrors()) {
                Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
                redirectAttributes.addFlashAttribute("errorsMap", errorsMap);
                return "redirect:/registration";
            }
            User userFromDb = userRepository.findByEmail(user.getEmail());
            if (userFromDb != null) {
                throw new UserAlreadyExistsException();
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(false);
            user.setActivationCode((UUID.randomUUID().toString()));
            user.setRole("USER");
            userRepository.createUser(user);

            userService.sendActivationMail(user);
            model.addAttribute("message", "Check your email to confirm your account");
            return "auth/login";
    }

    @GetMapping("/activate/{code}")
    public ModelAndView activate(@PathVariable String code) {
        ModelAndView model = new ModelAndView("redirect:/login");
        boolean isActivated = userService.activateUser(code);
        if (!isActivated) {
            throw new ActivationCodeNotFoundException();
        }

        return model;
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "auth/forgotPassword";
    }

    @PostMapping("/forgot-password")
    public String changePassword(@RequestParam String email,
                                 Model model) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            model.addAttribute("message", "User with such email doesn't exist.");
            return "auth/forgotPassword";
        }
        user.setActivationCode((UUID.randomUUID().toString()));
        userRepository.save(user);
        userService.sendPasswordMail(user);
        model.addAttribute("message", "Check your email to change the password.");
        return "auth/login";
    }

    @GetMapping("/changing-password/{code}")
    public String enterNewPassword(@PathVariable String code,
                                   Model model) {
        model.addAttribute("code", code);
        return "auth/newPassword";
    }

    @PostMapping("/changing-password/{code}")
    public String setNewPassword(
            @RequestParam String password,
            @RequestParam String matchingPassword,
            @PathVariable String code,
            Model model) {
        if (!password.equals(matchingPassword)) {
            model.addAttribute("message", "Passwords don't match");
            return "redirect:/changing-password/{code}";
        }
        User user = userRepository.findByActivationCode(code);
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", "Password is successfully changed.");
        } else {
            model.addAttribute("message", "Activation code is not found.");
        }
        user.setPassword(password);
        user.setActivationCode(null);
        userRepository.save(user);
        return "auth/login";
    }
}
