package com.xteam.ggq.model.service;

import com.xteam.ggq.model.bo.User;
import com.xteam.ggq.model.dao.UserRepository;
import com.xteam.ggq.model.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUser(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new BizException("没有找到相应的用户！");
        }

        return user;
    }

    public void addUser(User user) {
        userRepository.saveAndFlush(user);
    }

}
