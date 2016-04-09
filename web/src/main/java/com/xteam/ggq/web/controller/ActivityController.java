package com.xteam.ggq.web.controller;

import com.xteam.ggq.model.bo.Activity;
import com.xteam.ggq.model.bo.ActivityUser;
import com.xteam.ggq.model.bo.User;
import com.xteam.ggq.model.enums.ActivityStatus;
import com.xteam.ggq.model.service.ActivityService;
import com.xteam.ggq.model.service.ActivityUserService;
import com.xteam.ggq.model.service.UserService;
import com.xteam.ggq.web.controller.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("/api/activity")
@Slf4j
public class ActivityController {

    public static final Random rnd = new Random();
    public static final int RT = 8000;

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityUserService activityUserService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse<Activity> getActivityInfo(Long activityId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Activity activity = activityService.findActivity(activityId);
        // 本人是否已报名
        activity.setApplied(activityUserService.hasApplied(user.getUsername(), activity));

        return ApiResponse.returnSuccess(activity);
    }

    // 活动推荐
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public ApiResponse<Page<Activity>> recommend(@RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Activity> activityPage = activityService.recommend(pageNum, pageSize);

        for (Activity activity : activityPage.getContent()) {
            activity.setNickname(userService.findUser(activity.getUsername()).getNickname());
            // 伪造一下地理位置数据
            activity.setDistance(rnd.nextInt(RT));
        }

        return ApiResponse.returnSuccess(activityPage);
    }

    /**
     * 活动列表
     *
     * @param pageNum
     *            分页数
     * @param pageSize
     *            分页大小
     * @param activityStatus
     *            活动类型 0:全量 1:活动未开始 2:活动进行中 3:活动已结束
     * @return 对应活动类型的活动列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ApiResponse<Page<Activity>> activityList(@RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int activityStatus, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || StringUtils.isEmpty(user.getUsername())) {
            return ApiResponse.returnFail(-1, "用户信息不全");
        }
        Page<Activity> activityPage = activityService.activities(user.getUsername(), pageNum, pageSize, ActivityStatus.valueOf(activityStatus));

        if (activityPage == null) {
            return ApiResponse.returnSuccess(null, "用户的活动列表为空");
        }

        // 下面伪造一下地理位置数据
        int i = 0;
        for (Activity activity : activityPage.getContent()) {
            i++;
            activity.setDistance(1000 * i);
        }

        return ApiResponse.returnSuccess(activityPage);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse<Activity> postActivity(@RequestBody Activity activity, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        // 校验
        if (!activity.getActivityBeginTime().before(activity.getActivityEndTime())) {
            return ApiResponse.returnFail(-1, "活动开始时间应该早于活动截止时间！");
        }
        if (!activity.getApplyEndTime().before(activity.getActivityBeginTime())) {
            return ApiResponse.returnFail(-1, "活动报名截止时间应该早于活动开始时间！");
        }

        return ApiResponse.returnSuccess(activityService.postActivity(activity, user));
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public ApiResponse apply(Long activityId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Activity activity = activityService.findActivity(activityId);

        // 时间校验
        if (System.currentTimeMillis() > activity.getApplyEndTime().getTime()) {
            return ApiResponse.returnFail(-1, "报名已截止！请下次赶早！么么哒！");
        }
        // 人数校验
        int maleCount = activity.getApplyMaleCount();
        int femaleCount = activity.getApplyFemaleCount();
        if ((maleCount + femaleCount) >= activity.getPeopleLimit()) {
            return ApiResponse.returnFail(-1, "报名人数已满，请下次赶早！么么哒！");
        }
        if ((user.getGender() == User.Gender.MALE && (maleCount > femaleCount))
                || (user.getGender() == User.Gender.FEMALE && (femaleCount > maleCount))) {
            return ApiResponse.returnFail(-1, "报名人数性别比例不符，请稍后再试！么么哒！");
        }
        // 年龄校验
        int age = new Date(System.currentTimeMillis()).getYear() - user.getBirthday().getYear();
        Integer minAge = activity.getMinAge();
        Integer maxAge = activity.getMaxAge();
        if ( minAge != null && age < minAge ) {
            return ApiResponse.returnFail(-1, "该活动发起者限制年龄最小为" + minAge + "岁！");
        }
        if ( maxAge != null && age > maxAge ) {
            return ApiResponse.returnFail(-1, "该活动发起者限制年龄最大为" + maxAge + "岁！");
        }
        // 已经报名过了？
        if ( activityUserService.hasApplied(user.getUsername(), activity) ) {
            return ApiResponse.returnFail(-1, "亲，你已经报名参加过该活动啦，无需重复报名！");
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
