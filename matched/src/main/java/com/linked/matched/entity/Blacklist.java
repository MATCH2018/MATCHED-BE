package com.linked.matched.entity;

import javax.persistence.*;

@Entity
public class Blacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    //연동 해야한다.
}
