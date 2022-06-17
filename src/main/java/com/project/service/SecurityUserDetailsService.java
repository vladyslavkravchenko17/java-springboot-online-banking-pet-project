package com.project.service;

import com.project.domain.SecurityUserDetails;
import com.project.domain.User;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        SecurityUserDetails userDetails = new SecurityUserDetails();
        userDetails.setId(user.getId());
        userDetails.setFirstName(user.getFirstName());
        userDetails.setLastName(user.getLastName());
        userDetails.setEmail(email);
        userDetails.setPassword(user.getPassword());
        userDetails.setRole(user.getRole());
        userDetails.setActive(user.isActive());
        userDetails.setActivationCode(user.getActivationCode());
        userDetails.setCards(user.getCards());

        return userDetails;
    }
}
