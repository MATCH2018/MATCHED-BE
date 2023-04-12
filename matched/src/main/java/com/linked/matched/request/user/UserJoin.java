package com.linked.matched.request.user;

import com.linked.matched.entity.User;
import com.linked.matched.entity.status.RoleStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class UserJoin {

    private String loginId;
    private String password;
    private String checkPassword;
    private String name;
    private String department;
    private Integer gradle;
    private Date birth;
    private String sex;
    private LocalDate createDate;
    private RoleStatus roleStatus;

    @Builder
    public UserJoin(String loginId, String password, String checkPassword, String name, String department, Integer gradle, Date birth, String sex, LocalDate createDate, RoleStatus roleStatus) {
        this.loginId = loginId;
        this.password = password;
        this.checkPassword = checkPassword;
        this.name = name;
        this.department = department;
        this.gradle = gradle;
        this.birth = birth;
        this.sex = sex;
        this.createDate = createDate;
        this.roleStatus = roleStatus;
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
                .roleStatus(RoleStatus.ROLE_USER)
                .build();
    }

}
