package com.xteam.ggq.web.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.security.Timestamp;

@Data
public class ActivityInfoVo {

    private Long id;

    // 发起人
    private String username;
    // 发起人昵称
    private String nickname;

    private String title;

    private String description;

    // 大图
    private String pic;

    // 活动参与人数上限，为null时表示无限制
    private Integer peopleLimit;

    // 活动参与人年龄下限
    private Integer minAge;
    // 上限，为null表示无限制
    private Integer maxAge;

    private boolean open;

    // 活动开始时间
    private Timestamp activityBeginTime;
    // 活动结束时间
    private Timestamp activityEndTime;

    // 活动报名开始时间
    private Timestamp applyBeginTime;
    // 活动报名结束时间
    private Timestamp applyEndTime;

    // 集合地点
    private String assemblingAddress;
    // 经纬度
    private Double assemblingAddressLng;
    private Double assemblingAddressLat;

    // 活动地点
    private String activityAddress;
    // 经纬度
    private Double activityAddressLng;
    private Double activityAddressLat;

    // 活动费用
    private BigDecimal price;

    // 是否已报名
    private boolean applied;

    private float distance;

}
