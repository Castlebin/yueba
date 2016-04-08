package com.xteam.ggq.web.controller;

import com.xteam.ggq.model.bo.User;
import com.xteam.ggq.model.service.UserService;
import com.xteam.ggq.model.util.Md5Utils;
import com.xteam.ggq.model.util.TimeUtils;
import com.xteam.ggq.web.annotation.DoNotNeedLogin;
import com.xteam.ggq.web.controller.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse<User> getUserInfo(HttpServletRequest request) {
        String username = ((User) request.getSession().getAttribute("user")).getUsername();

        return ApiResponse.returnSuccess(userService.findUser(username));
    }

    @DoNotNeedLogin
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResponse<User> addUser(String username, String password, String nickname, User.Gender gender,
            String birthday, HttpServletRequest request)
                    throws UnsupportedEncodingException, NoSuchAlgorithmException, ParseException {
        String salt = UUID.randomUUID().toString();
        password = Md5Utils.EncoderByMd5(password + salt);
        User user = new User(username, password, salt, nickname, gender,
                new Timestamp(TimeUtils.DATA_FORMAT_YYYY_MM_DD.parse(birthday).getTime()));
        userService.addUser(user);

        request.getSession().setAttribute("user", user);

        return ApiResponse.returnSuccess(user);
    }

    @DoNotNeedLogin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResponse<User> login(String username, String password, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = userService.findUser(username);

        if (!Md5Utils.EncoderByMd5((password + user.getSalt())).equals(user.getPassword())) {
            return ApiResponse.returnFail(-1, "用户名或密码不正确！");
        }

        request.getSession().setAttribute("user", user);

        return ApiResponse.returnSuccess(user);
    }

}
