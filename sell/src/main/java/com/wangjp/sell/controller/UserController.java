package com.wangjp.sell.controller;

import com.wangjp.sell.constant.UserConstant;
import com.wangjp.sell.entity.User;
import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import com.wangjp.sell.form.UserForm;
import com.wangjp.sell.groups.Update;
import com.wangjp.sell.service.UserService;
import com.wangjp.sell.utils.ResultVOUtil;
import com.wangjp.sell.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/27 9:35 下午
 * @detail
 */
@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("创建用户")
    @PostMapping("/create")
    public ResultVO<Object> create(@RequestBody @Validated UserForm userForm) {
        User preUser = userService.findByUsername(userForm.getUsername());
        if (preUser != null) {
            log.error("【新增用户】用户已存在，id={}，username={}", preUser.getId(), preUser.getUsername());
            throw new SellException(ResultEnum.USER_ALREADY_EXIST);
        }
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(UserConstant.defaultPassword);
        user.setIsEnable(userForm.getIsEnable());
        userService.save(user);
        return ResultVOUtil.success();
    }

    @ApiOperation("修改用户")
    @PostMapping("/update/{id}")
    public ResultVO<Object> update(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) UserForm userForm) {
        User user = userService.findById(id);
        if (user == null) {
            log.error("【修改用户】未找到用户信息，id={}", id);
            throw new SellException(ResultEnum.USER_NOT_FIND);
        }
        user.setUsername(userForm.getUsername());
        user.setIsEnable(userForm.getIsEnable());
        userService.save(user);
        return ResultVOUtil.success();
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete")
    public ResultVO<Object> delete(@RequestBody List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.error("【删除用户】id不能为空");
            throw new SellException(ResultEnum.USER_ID_NOT_EMPTY);
        }
        userService.deleteUsersWithIds(ids);
        return ResultVOUtil.success();
    }
}
