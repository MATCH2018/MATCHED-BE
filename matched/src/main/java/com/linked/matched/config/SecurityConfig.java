package com.linked.matched.config;

import com.linked.matched.config.jwt.JwtAccessDeniedHandler;
import com.linked.matched.config.jwt.JwtAuthenticationEntryPoint;
import com.linked.matched.config.jwt.JwtSecurityConfig;
import com.linked.matched.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .antMatchers("/favicon.ico");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("http://39.120.169.42:3000");//권한하는거 한번 봐야한다.
        // 허용 출처 설정해주기(front)
//        configuration.addAllowedOrigin("*"); // local 테스트 시
        // 요청 시 오는 HTTP Method허용(*은 모두 허용)
        configuration.addAllowedMethod("*");
        // 헤더 허용(*은 모두 허용)
        configuration.addAllowedHeader("*");

        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

//        configuration.addExposedHeader("Authorization");
//        configuration.addExposedHeader("Refresh-Token");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf().disable()

                .cors()
                .configurationSource(corsConfigurationSource())

                .and()
                .headers().frameOptions().sameOrigin()

                //401,403 Exception 핸들링
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                //세션 사용하지 않는다.
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                
                //HttpServletRequest를 사용하는 요청들에 대한 접근 제한 설정
                .and()
                .authorizeRequests()
                // .antMatchers("/api/hello").permitAll() //추가적으로 예외처리할때 예시
                .antMatchers("/**").permitAll()
//                .anyRequest().authenticated()//나머지 request는 인증이 필요하다고 설정하는것

                //JwtSecurityConfig 설정
                .and()
                .apply(new JwtSecurityConfig(tokenProvider))

                .and().build();
    }

}
