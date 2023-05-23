package com.linked.matched.response.user;

import com.linked.matched.entity.User;
import lombok.Getter;

@Getter
public class UserProfile {

    private final String name;
    private final String department;
    private final Integer gradle;
    private final String sex;

    public UserProfile(User user) {
        this.name = user.getName();
        this.department = user.getDepartment();
        this.gradle = user.getGradle();
        this.sex = user.getSex();
    }
}
