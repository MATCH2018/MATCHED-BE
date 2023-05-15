package com.linked.matched.entity;

import com.linked.matched.entity.status.AuthUserStatus;
import com.linked.matched.entity.status.RoleStatus;
import com.linked.matched.request.user.UserEdit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
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
    private AuthUserStatus authUserStatus;
    private LocalDate createDate;

    @Column(nullable = false)
    private boolean reported;

    @Enumerated(EnumType.STRING)
    private RoleStatus roleStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notice> notices = new ArrayList<>();

    @Builder
    public User(Long userId, String loginId, String password, String name, String department, Integer gradle, Date birth, String sex, AuthUserStatus authUserStatus, RoleStatus roleStatus) {
        this.userId = userId;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.department = department;
        this.gradle = gradle;
        this.birth = birth;
        this.sex = sex;
        this.authUserStatus = authUserStatus;
        this.createDate = LocalDate.now();
        this.roleStatus = roleStatus;
    }

    public void edit(UserEdit userEdit) {
        this.name=userEdit.getName();
        this.department=userEdit.getDepartment();
        this.gradle=userEdit.getGradle();
        this.birth=userEdit.getBirth();
        this.sex=userEdit.getSex();
    }

    public void passwordEdit(String pwdEdit) {
        this.password=pwdEdit;
    }

    public void makeStatusReported() {
        this.reported = true;
    }
}
