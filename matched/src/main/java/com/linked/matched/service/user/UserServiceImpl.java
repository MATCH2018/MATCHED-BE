package com.linked.matched.service.user;

import com.linked.matched.config.jwt.TokenProvider;
import com.linked.matched.entity.RefreshToken;
import com.linked.matched.repository.jwt.RefreshTokenRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.jwt.TokenDto;
import com.linked.matched.request.user.UserJoin;
import com.linked.matched.request.user.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    @Override
    public void join(UserJoin userJoin) throws Exception {
        if(userRepository.findByLoginId(userJoin.getLoginId()).isPresent()){
            throw new Exception("이미 존재하는 이메일입니다.");
        }
        if(!userJoin.getPassword().equals(userJoin.getCheckPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        String encode = passwordEncoder.encode(userJoin.getPassword());
        userRepository.save(userJoin.toEntity(encode));
    }

    @Transactional
    @Override
    public TokenDto login(UserLogin userLogin) {
        //1. Login ID/PW 를 기반으로 authenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = userLogin.toAuthentication();

        //2.실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //  authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication  = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        
        //3. 인증 정보를 기반으로 jwt 토큰 생성
        TokenDto tokenDto = tokenProvider.createToken(authentication);
        
        //4. refreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }
}
