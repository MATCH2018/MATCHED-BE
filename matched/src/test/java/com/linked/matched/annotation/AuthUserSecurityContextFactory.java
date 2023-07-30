package com.linked.matched.annotation;

import com.linked.matched.config.jwt.UserPrincipal;
import com.linked.matched.entity.User;
import com.linked.matched.exception.user.UserNotFound;
import com.linked.matched.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class AuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public SecurityContext createSecurityContext(WithAuthUser annotation) {
        String name="asd@mju.ac.kr";
        String password="1234";
        String role=annotation.authorityName();

        User user = User.builder()
                .loginId("asd@mju.ac.kr")
                .password("1234")
                .authorityName(role)
                .build();


        userRepository.save(user);

        UserPrincipal userPrincipal = new UserPrincipal(user);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal,password , List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticationToken);
        return context;
    }
}
