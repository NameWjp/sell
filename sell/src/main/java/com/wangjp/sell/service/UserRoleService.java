package com.wangjp.sell.service;

import com.wangjp.sell.entity.UserRole;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/28 10:17 下午
 * @detail
 */
public interface UserRoleService {

    List<UserRole> findByUserId(Integer userId);
}
