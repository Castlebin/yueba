package com.xteam.ggq.model.bo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class ActivityUser extends BaseEntity {

    public ActivityUser() {}

    public ActivityUser(Long activityId, String username) {
        this.activityId = activityId;
        this.username = username;
    }

    // 参与的活动
    private Long activityId;

    // 活动参与者人用户名
    private String username;

    // 对活动发起者的评价和打分
    private BigDecimal commentForInitiator;
    private String commentForInitiatorContent;

    // 对活动的评价和打分
    private BigDecimal commentForActivity;
    private String commentForActivityContent;

}
