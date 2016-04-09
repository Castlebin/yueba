package com.xteam.ggq.model.dao;

import com.xteam.ggq.model.bo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByMobile(String mobile);

}
