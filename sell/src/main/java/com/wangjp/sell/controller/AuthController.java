package com.wangjp.sell.controller;

import com.wangjp.sell.entity.User;
import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import com.wangjp.sell.form.LoginForm;
import com.wangjp.sell.service.UserService;
import com.wangjp.sell.utils.ResultVOUtil;
import com.wangjp.sell.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/9/12 5:43 下午
 * @detail
 */
@Api(tags = "权限")
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @ApiOperation("登录")
    @PostMapping("/login")
    public ResultVO<Object> login(@RequestBody @Validated LoginForm loginForm) {
        User user = userService.findByUsername(loginForm.getUsername());
        if (user == null) {
            throw new SellException(ResultEnum.AUTH_USER_NOT_EXIST);
        }
        if (!bCryptPasswordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            throw new SellException(ResultEnum.AUTH_PASSWORD_ERROR);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResultVOUtil.success();
    }
}
