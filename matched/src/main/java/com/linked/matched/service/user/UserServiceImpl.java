package com.linked.matched.service.user;

import com.linked.matched.config.jwt.TokenProvider;
import com.linked.matched.entity.RefreshToken;
import com.linked.matched.entity.User;
import com.linked.matched.repository.jwt.RefreshTokenRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.jwt.DeleteTokenDto;
import com.linked.matched.request.jwt.TokenRequestDto;
import com.linked.matched.request.user.PwdEdit;
import com.linked.matched.request.user.UserEdit;
import com.linked.matched.response.jwt.TokenDto;
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

    @Override
    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.createToken(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

    @Override
    @Transactional
    public void refreshTokenDelete(DeleteTokenDto deleteTokenDto) {
        RefreshToken deleteToken = refreshTokenRepository.findByValue(deleteTokenDto.getRefreshToken())
                .orElseThrow(()->new IllegalArgumentException());

        refreshTokenRepository.delete(deleteToken);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public void edit(Long userId, UserEdit userEdit) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException());

        user.edit(userEdit);
    }

    @Override
    @Transactional
    public void passwordEdit(Long userId, PwdEdit pwdEdit) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());

        if (!passwordEncoder.matches(pwdEdit.getNowPassword(), user.getPassword())) {
            return;
        }
        if (!pwdEdit.getNewPassword().equals(pwdEdit.getCheckPassword())) {
            return;
        }
        user.passwordEdit(pwdEdit);
    }


}
