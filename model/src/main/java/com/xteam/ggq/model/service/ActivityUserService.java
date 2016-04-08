package com.xteam.ggq.model.service;

import com.xteam.ggq.model.bo.ActivityUser;
import com.xteam.ggq.model.dao.ActivityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityUserService {

    @Autowired
    private ActivityUserRepository activityUserRepository;

    public ActivityUser find(Long id) {
        return activityUserRepository.findOne(id);
    }

}
