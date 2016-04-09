package com.xteam.ggq.web.controller;

import com.xteam.ggq.model.bo.Activity;
import com.xteam.ggq.model.bo.ActivityUser;
import com.xteam.ggq.model.bo.User;
import com.xteam.ggq.model.service.ActivityService;
import com.xteam.ggq.model.service.ActivityUserService;
import com.xteam.ggq.web.controller.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/activity")
@Slf4j
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityUserService activityUserService;

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse<Activity> getActivityInfo(Long activityId) {
        return ApiResponse.returnSuccess(activityService.findActivity(activityId));
    }

    // 活动推荐
    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse recommend(@RequestParam(defaultValue = "0") int pageNum,
                                 @RequestParam(defaultValue = "10") int pageSize) {


        return ApiResponse.returnSuccess();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse<Activity> postActivity(@RequestBody Activity activity, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        // 校验
        if ( activity.getApplyEndTime().after(activity.getActivityBeginTime()) ) {
            return ApiResponse.returnFail(-1, "活动开始时间应该晚于活动报名截止时间！");
        }
        if ( activity.getActivityBeginTime().after(activity.getActivityEndTime()) ) {
            return ApiResponse.returnFail(-1, "活动开始时间应该早于活动结束时间！");
        }

        return ApiResponse.returnSuccess(activityService.postActivity(activity, user));
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public ApiResponse apply(Long activityId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Activity activity = activityService.findActivity(activityId);

        // 时间校验
        if ( System.currentTimeMillis() > activity.getApplyEndTime().getTime() ) {
            return ApiResponse.returnFail(-1, "报名已截止！");
        }
        // 人数校验
        int maleCount = activity.getApplyMaleCount();
        int femaleCount = activity.getApplyFemaleCount();
        if ( (user.getGender() == User.Gender.MALE && (maleCount > femaleCount) )
                || (user.getGender() == User.Gender.FAMALE && (femaleCount > maleCount) )) {
            return ApiResponse.returnFail(-1, "报名人数性别比例不符，请稍后再试！");
        }

        activityService.applyActivity(activity, user);

        return ApiResponse.returnSuccess();
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ApiResponse<ActivityUser> comment(ActivityUser activityUser, HttpServletRequest request) {
        String username = ((User) request.getSession().getAttribute("user")).getUsername();
        assert username.equals(activityUser.getUsername());

        ActivityUser originActivityUser = activityUserService.find(activityUser.getId());

        return ApiResponse.returnSuccess(activityService.comment(originActivityUser,
                activityUser.getCommentForInitiator(), activityUser.getCommentForInitiatorContent(),
                activityUser.getCommentForActivity(), activityUser.getCommentForActivityContent()));
    }

}
