package com.ayush.userdesk.service;

import com.ayush.userdesk.domain.entity.User;
import com.ayush.userdesk.domain.entity.UserPrincipal;
import com.ayush.userdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyCustomeUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserPrincipal loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Email not found in the Database" + email));

        return new UserPrincipal(user);
    }
}
