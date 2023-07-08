package com.linked.matched.annotation;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = AuthUserSecurityContextFactory.class)
public @interface WithAuthUser {
    String loginId() default "1234";
    String password() default "match123";
    String authorityName() default "ROLE_USER";
}
