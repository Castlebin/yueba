package com.xteam.ggq.model.bo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class ActivityTag extends BaseEntity {

    // 参与的活动
    private Long activityId;

    // tagName
    private String tagName;

}
