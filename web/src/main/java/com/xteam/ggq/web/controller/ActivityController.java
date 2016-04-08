package com.xteam.ggq.web.controller;

import com.xteam.ggq.model.bo.Activity;
import com.xteam.ggq.model.bo.ActivityUser;
import com.xteam.ggq.model.bo.User;
import com.xteam.ggq.model.service.ActivityService;
import com.xteam.ggq.model.service.ActivityUserService;
import com.xteam.ggq.web.controller.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse<Activity> save(Activity activity, HttpServletRequest request) {
        String username = ((User) request.getSession().getAttribute("user")).getUsername();
        activity.setUsername(username);

        return ApiResponse.returnSuccess(activityService.save(activity));
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public ApiResponse apply(Long activityId, HttpServletRequest request) {
        String username = ((User) request.getSession().getAttribute("user")).getUsername();
        activityService.applyActivity(activityId, username);

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
