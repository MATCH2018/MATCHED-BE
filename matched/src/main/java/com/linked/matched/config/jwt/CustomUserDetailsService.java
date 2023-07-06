package com.linked.matched.config.jwt;

import com.linked.matched.entity.User;
import com.linked.matched.exception.user.UserNotFound;
import com.linked.matched.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByLoginId(username)
                .orElseThrow(UserNotFound::new);
        return new UserPrincipal(user);
    }
}
