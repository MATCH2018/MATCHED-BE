package com.linked.matched.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Blacklist {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String email;

}
