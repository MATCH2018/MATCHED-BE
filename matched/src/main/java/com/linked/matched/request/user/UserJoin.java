package com.linked.matched.request.user;

import com.linked.matched.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoin {

    private String loginId;
    private String password;
    private String checkPassword;
    private String name;
    private String department;
    private Integer gradle;
    private LocalDate birth;
    private String sex;
    private String authorityName;
    private LocalDateTime createdAt;

    @Builder
    public UserJoin(String loginId, String password, String checkPassword, String name, String department, Integer gradle, LocalDate birth, String sex, String authorityName) {
        this.loginId = loginId;
        this.password = password;
        this.checkPassword = checkPassword;
        this.name = name;
        this.department = department;
        this.gradle = gradle;
        this.birth = birth;
        this.sex = sex;
        this.authorityName = authorityName;
        this.createdAt = LocalDateTime.now();
    }


    public User toEntity(String password){
        return User.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .department(department)
                .gradle(gradle)
                .birth(birth)
                .sex(sex)
                .authorityName("ROLE_USER") //admin은 ROLE_ADMIN
                .build();
    }

}
