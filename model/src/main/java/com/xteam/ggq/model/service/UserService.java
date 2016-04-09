package com.xteam.ggq.model.service;

import com.xteam.ggq.model.bo.User;
import com.xteam.ggq.model.bo.UserTag;
import com.xteam.ggq.model.dao.UserRepository;
import com.xteam.ggq.model.dao.UserTagRepository;
import com.xteam.ggq.model.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTagRepository userTagRepository;

    public User findUser(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new BizException("没有找到相应的用户！");
        }

        Set<UserTag> userTags = userTagRepository.findByUsername(username);
        Set<String> tags = userTags.stream().map(UserTag::getTagName).collect(Collectors.toSet());
        user.setTags(tags);

        return user;
    }

    public void addUser(User user) {
        userRepository.saveAndFlush(user);
    }

}
