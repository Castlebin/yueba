package com.xteam.ggq.model.bo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class UserTag extends BaseEntity {

    // 用户名
    private String username;

    // tagName
    private String tagName;

}
