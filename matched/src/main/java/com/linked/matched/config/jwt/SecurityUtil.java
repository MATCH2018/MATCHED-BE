package com.linked.matched.config.jwt;

import com.linked.matched.exception.NotSecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil(){}

    // SecurityContext 에 유저 정보가 저장되는 시점 // 그럼 그냥 하나열고 확인하고 얼마나 있는지 확인 정도
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static Long getCurrentUserId(){
        final Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new NotSecurityContext();
        }

        return Long.parseLong(authentication.getName());
    }
}
