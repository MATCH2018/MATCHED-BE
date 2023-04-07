package com.linked.matched.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long userId;
    private String loginId;
    private String password;
    private String name;
    private String department;
    private Integer gradle;
    private Date birth;
    private String sex;
    private Date createDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @Builder
    public User(Long userId, String loginId, String password, String name, String department, Integer gradle, Date birth, String sex, Date createDate) {
        this.userId = userId;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.department = department;
        this.gradle = gradle;
        this.birth = birth;
        this.sex = sex;
        this.createDate = createDate;
    }
}
