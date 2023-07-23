package com.linked.matched.entity;


import com.linked.matched.request.user.UserEditor;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String loginId;
    private String password;
    private String name;
    private String department;
    private Integer gradle;
    private LocalDate birth;
    private String sex;
    private String authorityName;
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean reported;// 신고 기능

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notice> notices = new ArrayList<>();

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Applicant> applicant = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RefreshToken> refreshTokens = new ArrayList<>();

    @Builder
    public User(Long userId, String loginId, String password, String name, String department, Integer gradle, LocalDate birth, String sex, String authorityName) {
        this.userId = userId;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.department = department;
        this.gradle = gradle;
        this.birth = birth;
        this.sex = sex;
        this.authorityName = authorityName;
        this.createdAt = LocalDateTime.now();
    }

    public void edit(UserEditor userEditor) {
        this.name=userEditor.getName();
        this.department=userEditor.getDepartment();
        this.gradle=userEditor.getGradle();
        this.birth=userEditor.getBirth();
        this.sex=userEditor.getSex();
    }

    public UserEditor.UserEditorBuilder toEditor(){
        return UserEditor.builder()
                .name(name)
                .department(department)
                .gradle(gradle)
                .birth(birth)
                .sex(sex);
    }

    public void passwordEdit(String pwdEdit) {
        this.password=pwdEdit;
    }

    public void makeStatusReported() {
        this.reported = true;
    }
}
