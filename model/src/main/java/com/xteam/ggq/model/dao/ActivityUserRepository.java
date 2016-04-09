package com.xteam.ggq.model.dao;

import com.xteam.ggq.model.bo.ActivityUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityUserRepository extends JpaRepository<ActivityUser, Long> {

    ActivityUser findByUsernameAndActivityId(String username, Long activityId);

}
