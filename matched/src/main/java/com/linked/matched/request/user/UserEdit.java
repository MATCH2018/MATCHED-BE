package com.linked.matched.request.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEdit {

    private String name;
    private String department;
    private Integer gradle;
    private Date birth;
    private String sex;

    @Builder
    public UserEdit(String name, String department, Integer gradle, Date birth, String sex) {
        this.name = name;
        this.department = department;
        this.gradle = gradle;
        this.birth = birth;
        this.sex = sex;
    }
}
