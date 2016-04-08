package com.xteam.ggq.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @Column(nullable = false)
    private String salt;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public User() {
    }

    public User(String username, String password, String salt, String nickname, Gender gender) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.nickname = nickname;
        this.gender = gender;
    }

    public enum Gender {
        MALE, FAMALE
    }

}
