package com.xteam.ggq.web.controller;

import com.xteam.ggq.model.bo.Activity;
import com.xteam.ggq.model.bo.ActivityUser;
import com.xteam.ggq.model.bo.User;
import com.xteam.ggq.model.service.ActivityService;
import com.xteam.ggq.model.service.ActivityUserService;
import com.xteam.ggq.web.controller.api.ApiResponse;
import com.xteam.ggq.web.vo.ActivityInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ApiResponse<ActivityInfoVo> getActivityInfo(Long activityId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        ActivityInfoVo activityInfoVo = new ActivityInfoVo();
        Activity activity = activityService.findActivity(activityId);
        BeanUtils.copyProperties(activity, activityInfoVo);
        // 本人是否已报名
        activityInfoVo.setApplied(activityUserService.hasApplied(user.getUsername(), activity));

        return ApiResponse.returnSuccess(activityInfoVo);
    }

    // 活动推荐
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public ApiResponse<Page<Activity>> recommend(@RequestParam(defaultValue = "0") int pageNum,
                                 @RequestParam(defaultValue = "10") int pageSize) {
        Page<Activity> activityPage = activityService.recommend(pageNum, pageSize);

        // 下面伪造一下地理位置数据
        int i = 0;
        for ( Activity activity : activityPage.getContent() ) {
            i++;
            activity.setDistance( 1000 * i);
        }

        return ApiResponse.returnSuccess(activityPage);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse<Activity> postActivity(@RequestBody Activity activity, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        // 校验
        if ( !activity.getActivityBeginTime().before(activity.getActivityEndTime()) ) {
            return ApiResponse.returnFail(-1, "活动开始时间应该早于活动截止时间！");
        }
        if ( !activity.getApplyEndTime().before(activity.getActivityBeginTime()) ) {
            return ApiResponse.returnFail(-1, "活动报名截止时间应该早于活动开始时间！");
        }

        return ApiResponse.returnSuccess(activityService.postActivity(activity, user));
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public ApiResponse apply(Long activityId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Activity activity = activityService.findActivity(activityId);

        // 时间校验
        if ( System.currentTimeMillis() > activity.getApplyEndTime().getTime() ) {
            return ApiResponse.returnFail(-1, "报名已截止！请下次赶早！么么哒！");
        }
        // 人数校验
        int maleCount = activity.getApplyMaleCount();
        int femaleCount = activity.getApplyFemaleCount();
        if ( (maleCount + femaleCount) >= activity.getPeopleLimit() ) {
            return ApiResponse.returnFail(-1, "报名人数已满，请下次赶早！么么哒！");
        }
        if ( (user.getGender() == User.Gender.MALE && (maleCount > femaleCount) )
                || (user.getGender() == User.Gender.FAMALE && (femaleCount > maleCount) )) {
            return ApiResponse.returnFail(-1, "报名人数性别比例不符，请稍后再试！么么哒！");
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
