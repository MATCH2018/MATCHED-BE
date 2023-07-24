package com.linked.matched.request.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserEditor {
    private String name;
    private String department;
    private Integer gradle;
    private LocalDate birth;
    private String sex;

    @Builder
    public UserEditor(String name, String department, Integer gradle, LocalDate birth, String sex) {
        this.name = name;
        this.department = department;
        this.gradle = gradle;
        this.birth = birth;
        this.sex = sex;
    }
}
