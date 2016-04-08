package com.xteam.ggq.model.bo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class ActivityUser extends BaseEntity {

    // 参与的活动
    private Long activityId;

    // 活动参与者人用户名
    private String participant;

    // 对活动发起者的评价和打分
    private BigDecimal commentForInitiatorUser;
    private String commentForInitiatorUserContent;

    // 对活动的评价和打分
    private BigDecimal commentForActivity;
    private String commentForActivityContent;

}
