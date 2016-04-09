package com.xteam.ggq.model.dao;

import com.xteam.ggq.model.bo.Activity;
import com.xteam.ggq.model.bo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("select a from Activity a, ActivityUser au where a.id = au.activityId and au.username = ?1 order by a.createTime desc")
    Page<Activity> findByUsername(String username, Pageable pageable);

    @Query("select a from Activity a, ActivityUser au where a.id = au.activityId and au.username = ?1 and a.activityBeginTime > ?2 order by a.createTime desc")
    Page<Activity> findByActivityBeginTimeGreaterThan(String username, Timestamp timestamp, Pageable pageable);

    @Query("select a from Activity a, ActivityUser au where a.id = au.activityId and au.username = ?1 and a.activityBeginTime < ?2 and a.activityEndTime > ?2 order by a.createTime desc")
    Page<Activity> findByActivityBeginTimeLessThanAndActivityEndTimeGreaterThan(String username, Timestamp timestamp,
            Pageable pageable);

    @Query("select a from Activity a, ActivityUser au where a.id = au.activityId and au.username = ?1 and a.activityEndTime < ?2 order by a.createTime desc")
    Page<Activity> findByActivityEndTimeLessThan(String username, Timestamp timestamp, Pageable pageable);

    @Query("select a from Activity a, ActivityUser au where a.id = au.activityId and au.username = ?1 order by a.createTime desc")
    List<Activity> findByUsername(String username);

    @Query("select u from ActivityUser au, User u where au.username = u.username and au.activityId = ?1")
    List<User> findUsersByActivityId(Long activityId);
}