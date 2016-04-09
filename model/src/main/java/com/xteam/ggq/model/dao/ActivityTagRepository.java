package com.xteam.ggq.model.dao;

import com.xteam.ggq.model.bo.ActivityTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ActivityTagRepository extends JpaRepository<ActivityTag, Long> {

    Set<ActivityTag> findByActivityId(Long activityId);

}
