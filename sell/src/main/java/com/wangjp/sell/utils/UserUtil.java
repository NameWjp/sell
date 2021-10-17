package com.wangjp.sell.utils;

import com.wangjp.sell.constant.UserConstant;
import com.wangjp.sell.vo.UserInfoVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/10/8 11:14
 * @detail 当前用户工具类
 */
@Component
public class UserUtil {

    /**
     * 获取当前登录用户信息
     */
    public static UserInfoVO getCurrentUserInfoVO() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoVO userInfoVO = (UserInfoVO) authentication.getPrincipal();
        return userInfoVO;
    }

    /**
     * 获取当前登录用户 id
     */
    public static Integer getCurrentUserId() {
        UserInfoVO userInfoVO = getCurrentUserInfoVO();
        return userInfoVO.getId();
    }

    /**
     * 判断当前用户是否是管理员
     */
    public static Boolean isAdmin() {
        UserInfoVO userInfoVO = getCurrentUserInfoVO();
        return userInfoVO.getUsername().equals(UserConstant.adminName);
    }
}
