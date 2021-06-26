package com.wangjp.sell.service;

import com.wangjp.sell.entity.RoleMenu;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/26 5:14 下午
 * @detail
 */
public interface RoleMenuService {

    List<RoleMenu> findByRoleId(Integer roleId);
}
