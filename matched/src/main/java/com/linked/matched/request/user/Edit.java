package com.linked.matched.request.user;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class Edit {

    private String name;
    private String department;
    private String gradle;
    private Date birth;
    private String sex;

    @Builder
    public Edit(String name, String department, String gradle, Date birth, String sex) {
        this.name = name;
        this.department = department;
        this.gradle = gradle;
        this.birth = birth;
        this.sex = sex;
    }
}
