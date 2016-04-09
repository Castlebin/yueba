package com.xteam.ggq.web.vo;

import lombok.Data;

@Data
public class ActivtyListVo {

    private Long id;

    private String title;

    private String description;

    // 大图
    private String pic;

    // 单位 米(m)
    private float distance;

}
