package com.xteam.ggq.model.service;

import com.xteam.ggq.model.bo.Activity;
import com.xteam.ggq.model.bo.ActivityUser;
import com.xteam.ggq.model.bo.User;
import com.xteam.ggq.model.dao.ActivityRepository;
import com.xteam.ggq.model.dao.ActivityUserRepository;
import com.xteam.ggq.model.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityUserRepository activityUserRepository;

    public Activity findActivity(Long activityId) {
        Activity activity = activityRepository.findOne(activityId);

        if (activity == null) {
            throw new BizException("没有找到相应的活动！");
        }

        return activity;
    }



    @Transactional
    public void applyActivity(Activity activity, User user) {
        ActivityUser activityUser = new ActivityUser(activity.getId(), user.getUsername());

        if (user.getGender() == User.Gender.MALE) {
            activity.setApplyMaleCount(activity.getApplyMaleCount() + 1);
        } else if (user.getGender() == User.Gender.FAMALE) {
            activity.setApplyFemaleCount(activity.getApplyFemaleCount() + 1);
        }

        activityRepository.save(activity);
        activityUserRepository.save(activityUser);
    }

    @Transactional
    public Activity postActivity(Activity activity, User user) {
        // 设置活动的发起人
        activity.setUsername(user.getUsername());
        activity = activityRepository.saveAndFlush(activity);

        // 将活动的发起者作为第一个报名参加者
        applyActivity(activity, user);

        return activity;
    }

    public ActivityUser comment(ActivityUser originActivityUser,
                        BigDecimal commentForInitiator, String commentForInitiatorContent,
                        BigDecimal commentForActivity, String commentForActivityContent) {
        originActivityUser.setCommentForInitiator(commentForInitiator);
        originActivityUser.setCommentForInitiatorContent(commentForInitiatorContent);
        originActivityUser.setCommentForActivity(commentForActivity);
        originActivityUser.setCommentForActivityContent(commentForActivityContent);

        return activityUserRepository.saveAndFlush(originActivityUser);
    }
}
