package com.epam.tradingapp.service;

import com.epam.tradingapp.exception.UsernameNotFoundException;
import com.epam.tradingapp.model.User;
import com.epam.tradingapp.repository.UserRepository;
import com.epam.tradingapp.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;

    public User registerUser(String username, String rawPwd) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(rawPwd));
        user.setRoles(Roles.USER);
        return userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRoles().toString())));
    }
}



