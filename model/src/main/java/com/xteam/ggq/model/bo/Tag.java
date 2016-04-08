package com.xteam.ggq.model.bo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Tag extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

}
