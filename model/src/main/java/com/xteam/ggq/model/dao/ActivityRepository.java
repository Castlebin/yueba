package com.xteam.ggq.model.dao;

import com.xteam.ggq.model.bo.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("select a from Activity a, ActivityUser au where a.id = au.activityId and au.username = ?1 and a.activityBeginTime > ?2")
    Page<Activity> findByActivityBeginTimeGreaterThan(String username, Timestamp timestamp, Pageable pageable);

    @Query("select a from Activity a, ActivityUser au where a.id = au.activityId and au.username = ?1 and a.activityBeginTime < ?2 and a.activityEndTime > ?2")
    Page<Activity> findByActivityBeginTimeLessThanAndActivityEndTimeGreaterThan(String username, Timestamp timestamp,
            Pageable pageable);

    @Query("select a from Activity a, ActivityUser au where a.id = au.activityId and au.username = ?1 and a.activityEndTime < ?2")
    Page<Activity> findByActivityEndTimeLessThan(String username, Timestamp timestamp, Pageable pageable);
}