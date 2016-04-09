package com.xteam.ggq.model.dao;

import com.xteam.ggq.model.bo.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {

    Set<UserTag> findByUsername(String username);

}
