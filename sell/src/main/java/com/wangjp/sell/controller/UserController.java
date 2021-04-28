package com.wangjp.sell.controller;

import com.wangjp.sell.entity.User;
import com.wangjp.sell.form.UserForm;
import com.wangjp.sell.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/27 9:35 下午
 * @detail
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("创建用户")
    @PostMapping("/create")
    public User create(@RequestBody @Validated UserForm userForm) {
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        user.setIsEnable(userForm.getIsEnable());
        return userService.save(user);
    }
}
