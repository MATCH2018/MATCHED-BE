package com.linked.matched.jwt;

import com.linked.matched.entity.User;
import com.linked.matched.exception.user.UserNotFound;
import com.linked.matched.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

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
