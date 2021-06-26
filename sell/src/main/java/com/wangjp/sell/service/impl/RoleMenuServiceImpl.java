package com.wangjp.sell.service.impl;

import com.wangjp.sell.entity.RoleMenu;
import com.wangjp.sell.repository.RoleMenuRepository;
import com.wangjp.sell.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/26 5:16 下午
 * @detail
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    RoleMenuRepository roleMenuRepository;

    @Override
    public List<RoleMenu> findByRoleId(Integer roleId) {
        return roleMenuRepository.findByRoleId(roleId);
    }
}
