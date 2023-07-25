package com.linked.matched.service.user;

import com.linked.matched.config.jwt.TokenProvider;
import com.linked.matched.entity.RefreshToken;
import com.linked.matched.entity.User;
import com.linked.matched.exception.email.AlreadyExistsEmailException;
import com.linked.matched.exception.user.*;
import com.linked.matched.repository.jwt.RefreshTokenRepository;
import com.linked.matched.repository.user.UserRepository;
import com.linked.matched.request.jwt.DeleteTokenDto;
import com.linked.matched.request.jwt.TokenRequestDto;
import com.linked.matched.request.user.*;
import com.linked.matched.response.jwt.TokenDto;
import com.linked.matched.response.user.UserMail;
import com.linked.matched.response.user.UserProfile;
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
    public void join(UserJoin userJoin) {
        if(userRepository.findByLoginId(userJoin.getLoginId()).isPresent()){
            throw new AlreadyExistsEmailException();
        }
        if(!userJoin.getPassword().equals(userJoin.getCheckPassword())){
            throw new InvalidLoginInformation();
        }

        String encode = passwordEncoder.encode(userJoin.getPassword());
        userRepository.save(userJoin.toEntity(encode));
    }


    @Override
    public TokenDto login(UserLogin userLogin) {
        //1. Login ID/PW 를 기반으로 authenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = userLogin.toAuthentication();

        //2.실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

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
            throw new InvalidRefreshToken();
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(LogoutStatus::new);

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new TokenMismatch();
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
                .orElseThrow(TokenNotFound::new);

        refreshTokenRepository.delete(deleteToken);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(Long.valueOf(id)).orElseThrow(UserNotFound::new);

        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void edit(Long id, UserEditor userEdit) {
        User user = userRepository.findById(id).orElseThrow(UserNotFound::new);

        UserEditor.UserEditorBuilder userEditorBuilder = user.toEditor();

        if(userEdit.getName()!=null){
            userEditorBuilder.name(userEdit.getName());
        }
        if (userEdit.getDepartment()!=null){
            userEditorBuilder.department(userEdit.getDepartment());
        }
        if(userEdit.getGradle()!=null){
            userEditorBuilder.gradle(userEdit.getGradle());
        }
        if (userEdit.getBirth()!=null){
            userEditorBuilder.birth(userEdit.getBirth());
        }
        if(userEdit.getSex()!=null){
            userEditorBuilder.sex(userEdit.getSex());
        }

        user.edit(userEditorBuilder.build());
    }

    @Override
    @Transactional
    public void passwordEdit(Long id, PwdEdit pwdEdit) {
        User user = userRepository.findById(Long.valueOf(id)).orElseThrow(UserNotFound::new);

        if (!passwordEncoder.matches(pwdEdit.getNowPassword(), user.getPassword())||!pwdEdit.getNewPassword().equals(pwdEdit.getCheckPassword())) {
            throw new NotEqualPassword();
        }
        String encode = passwordEncoder.encode(pwdEdit.getNewPassword());
        user.passwordEdit(encode);
    }

    @Override
    @Transactional
    public void passwordChange(PwdChange pwdChange) {
        User user = userRepository.findByLoginId(pwdChange.getLoginId()).orElseThrow(() -> new UserNotFound());

        if (!pwdChange.getNewPassword().equals(pwdChange.getCheckPassword())) {
            throw new NotEqualPassword();
        }

        String encode = passwordEncoder.encode(pwdChange.getNewPassword());
        user.passwordEdit(encode);
    }

    @Override
    public UserMail findUserEmail(Long applicantId) {
        return userRepository.findById(applicantId).map(UserMail::new).orElseThrow(UserNotFound::new);
    }

    @Override
    public UserProfile viewUser(Long id) {
        return userRepository.findById(Long.valueOf(id))
                .map(UserProfile::new)
                .orElseThrow(() -> new UserNotFound());
    }


}
