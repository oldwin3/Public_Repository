package ru.itis.orisjavaproject.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.orisjavaproject.Entities.User;
import ru.itis.orisjavaproject.Repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Component(value = "customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findOneByEmail(email);

        if (userOptional.isPresent()) {
            return new UserDetailsImpl(userOptional.get());

        } else throw new SecurityException("User with email <" + email + "> not found");

    }
}