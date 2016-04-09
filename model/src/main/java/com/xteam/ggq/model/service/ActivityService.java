package com.xteam.ggq.model.service;

import com.xteam.ggq.model.bo.Activity;
import com.xteam.ggq.model.bo.ActivityTag;
import com.xteam.ggq.model.bo.ActivityUser;
import com.xteam.ggq.model.bo.User;
import com.xteam.ggq.model.dao.ActivityRepository;
import com.xteam.ggq.model.dao.ActivityTagRepository;
import com.xteam.ggq.model.dao.ActivityUserRepository;
import com.xteam.ggq.model.dao.UserRepository;
import com.xteam.ggq.model.enums.ActivityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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

        Set<ActivityTag> activityTags = activityTagRepository.findByActivityId(activityId);
        Set<String> tags = activityTags.stream().map(ActivityTag::getTagName).collect(Collectors.toSet());
        activity.setTags(tags);

        setActivityStatus(activity);
        return activity;
    }

    public Activity findActivityAndSetUsers(Long activityId) {
        Activity activity = findActivity(activityId);
        List<User> users = activityRepository.findUsersByActivityId(activityId);
        Iterator<User> userIterator = users.iterator();
        User user = null;
        while (userIterator.hasNext()) {
            User tmpUser = userIterator.next();
            if (tmpUser.getUsername().equals(activity.getUsername())) {
                userIterator.remove();
                user = tmpUser;
                break;
            }
        }
        if (user != null) {
            users.add(0, user);
        }
        activity.setUsers(users);
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
        // 非活动发起人，扣除额度
        if ( !activity.getUsername().equals(user.getUsername()) ) {
            user.setAmount(user.getAmount().subtract(activity.getPrice()));
        }

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

        setActivityStatus(activity);
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
        PageRequest pageRequest = new PageRequest(pageNum, pageSize, new Sort(Sort.Direction.DESC, "createTime"));
        Page<Activity> activities = activityRepository.findAll(pageRequest);
        setActivitiesStatus(activities);
        return activities;
    }

    public Page<Activity> activities(String username, int pageNum, int pageSize, ActivityStatus activityStatus) {
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Timestamp now = new Timestamp(new Date().getTime());
        Page<Activity> activityPage = null;
        switch (activityStatus) {
            case ALL:
                activityPage = activityRepository.findByUsername(username, pageRequest);
                break;
            case BEFORE:
                activityPage = activityRepository.findByActivityBeginTimeGreaterThan(username, now, pageRequest);
                break;
            case IN_PROGRESS:
                activityPage = activityRepository.findByActivityBeginTimeLessThanAndActivityEndTimeGreaterThan(username,
                        now, pageRequest);
                break;
            case FINISH:
                activityPage = activityRepository.findByActivityEndTimeLessThan(username, now, pageRequest);
                break;
            default:
                return null;
        }
        setActivitiesStatus(activityPage);
        return activityPage;
    }

    /**
     * 设置活动列表状态
     *
     * @param activityPage
     *            活动列表
     */
    public void setActivitiesStatus(Page<Activity> activityPage) {
        Assert.notNull(activityPage, "活动列表不能为空");
        for (Activity activity : activityPage.getContent()) {
            setActivityStatus(activity);
        }
    }

    /**
     * 设置单个活动状态
     *
     * @param activity
     *            活动
     */
    public void setActivityStatus(Activity activity) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        // 如果比活动开始时间早
        if (now.before(activity.getActivityBeginTime())) {
            if (now.before(activity.getApplyEndTime())) {// 并且比申请截止时间早
                activity.setActivityStatus(ActivityStatus.IN_ENROLLMENT);
            } else if (now.after(activity.getApplyEndTime())) {// 并且比申请截止时间晚
                activity.setActivityStatus(ActivityStatus.BEFORE);
            }
        }
        // 如果比活动开始时间晚
        else if (now.after(activity.getActivityEndTime())) {// 并且比活动结束时间早
            activity.setActivityStatus(ActivityStatus.FINISH);
        } else if (now.before(activity.getActivityEndTime())) {// 并且比活动结束时间晚
            activity.setActivityStatus(ActivityStatus.IN_PROGRESS);
        }
    }

    /**
     * 得到用户当前活动数量=还未结束的活动数量
     *
     * @param username
     *            用户名
     * @return 还未结束的活动数量
     */
    public int getCurrentActivityNum(String username) {
        List<Activity> activities = activityRepository.findByUsername(username);
        int num = 0;
        if (activities == null) {
            return num;
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        for (Activity activity : activities) {
            if (now.before(activity.getApplyEndTime())) {
                num++;
            }
        }
        return num;
    }
}
