package com.xteam.ggq.model.service;

import com.xteam.ggq.model.bo.Activity;
import com.xteam.ggq.model.bo.ActivityUser;
import com.xteam.ggq.model.dao.ActivityRepository;
import com.xteam.ggq.model.dao.ActivityUserRepository;
import com.xteam.ggq.model.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void applyActivity(Long activityId, String username) {
        Activity activity = findActivity(activityId);
        ActivityUser activityUser = new ActivityUser(activity.getId(), username);
        
        activityUserRepository.save(activityUser);
    }

    public Activity save(Activity activity) {
        return activityRepository.saveAndFlush(activity);
    }

}
