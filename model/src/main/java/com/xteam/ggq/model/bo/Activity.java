package com.xteam.ggq.model.bo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Activity extends BaseEntity {

    // 发起人
    private String username;

    @Column(nullable = false)
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

    // 已报名男性人数
    private int applyMaleCount;
    // 已报名女性人数
    private int applyFemaleCount;

    // 公开活动还是私密活动
    private boolean open = true;

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
    private BigDecimal price = BigDecimal.ZERO;

    // 发起人昵称
    private transient String nickname;

    // 是否已报名
    private transient boolean applied;

    private transient float distance;

    // 标签
    private transient Set<Tag> tags = new HashSet<>();

    @Version
    private int version;

}
