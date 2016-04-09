package com.xteam.ggq.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.sql.Timestamp;

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

    // 头像
    private String avatar;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 用户星级
    private BigDecimal grade = BigDecimal.ZERO;

    public User() {
    }

    public User(String username, String password, String salt, String nickname, Gender gender, Timestamp birthday) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
    }

    public enum Gender {
        MALE, FEMALE
    }

}
