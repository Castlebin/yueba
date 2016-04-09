package com.xteam.ggq.model.service;

import com.xteam.ggq.model.bo.Activity;
import com.xteam.ggq.model.bo.ActivityTag;
import com.xteam.ggq.model.bo.ActivityUser;
import com.xteam.ggq.model.bo.User;
import com.xteam.ggq.model.dao.ActivityRepository;
import com.xteam.ggq.model.dao.ActivityTagRepository;
import com.xteam.ggq.model.dao.ActivityUserRepository;
import com.xteam.ggq.model.enums.ActivityStatus;
import com.xteam.ggq.model.dao.UserRepository;
import com.xteam.ggq.model.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityUserRepository activityUserRepository;
    @Autowired
    private ActivityTagRepository activityTagRepository;
    @Autowired
    private UserRepository userRepository;

    public Activity findActivity(Long activityId) {
        Activity activity = activityRepository.findOne(activityId);

        if (activity == null) {
            throw new BizException("没有找到相应的活动！");
        }

        Set<ActivityTag> activityTags = activityTagRepository.findByActivityId(activityId);
        Set<String> tags = activityTags.stream().map(ActivityTag::getTagName).collect(Collectors.toSet());
        activity.setTags(tags);

        return activity;
    }

    @Transactional
    public void applyActivity(Activity activity, User user) {
        ActivityUser activityUser = new ActivityUser(activity.getId(), user.getUsername());

        if (user.getGender() == User.Gender.MALE) {
            activity.setApplyMaleCount(activity.getApplyMaleCount() + 1);
        } else if (user.getGender() == User.Gender.FEMALE) {
            activity.setApplyFemaleCount(activity.getApplyFemaleCount() + 1);
        }

        // 用户参加活动次数+1
        user.setParticipateCount(user.getParticipateCount() + 1);

        activityRepository.save(activity);
        activityUserRepository.save(activityUser);
        userRepository.save(user);
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

    public ActivityUser comment(ActivityUser originActivityUser, BigDecimal commentForInitiator,
            String commentForInitiatorContent, BigDecimal commentForActivity, String commentForActivityContent) {
        originActivityUser.setCommentForInitiator(commentForInitiator);
        originActivityUser.setCommentForInitiatorContent(commentForInitiatorContent);
        originActivityUser.setCommentForActivity(commentForActivity);
        originActivityUser.setCommentForActivityContent(commentForActivityContent);

        return activityUserRepository.saveAndFlush(originActivityUser);
    }

    public Page<Activity> recommend(int pageNum, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum, pageSize, new Sort("activityBeginTime"));
        return activityRepository.findAll(pageRequest);
    }

    public Page<Activity> activities(String username, int pageNum, int pageSize, ActivityStatus activityStatus) {
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Timestamp now = new Timestamp(new Date().getTime());
        switch (activityStatus) {
            case ALL:
                return activityRepository.findByUsername(username, pageRequest);
            case BEFORE:
                return activityRepository.findByActivityBeginTimeGreaterThan(username, now, pageRequest);
            case IN_PROGRESS:
                return activityRepository.findByActivityBeginTimeLessThanAndActivityEndTimeGreaterThan(username, now,
                        pageRequest);
            case FINISH:
                return activityRepository.findByActivityEndTimeLessThan(username, now, pageRequest);
            default:
                return null;
        }
    }
}
