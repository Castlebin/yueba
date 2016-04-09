package com.xteam.ggq.web.vo;

import com.xteam.ggq.model.bo.User;
import lombok.Data;

@Data
public class UserBasicInfoVo {

    private String nickname;

    private User.Gender gender;

}
