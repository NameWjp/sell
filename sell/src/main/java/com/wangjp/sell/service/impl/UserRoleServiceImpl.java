package com.wangjp.sell.service.impl;

import com.wangjp.sell.entity.UserRole;
import com.wangjp.sell.repository.UserRoleRepository;
import com.wangjp.sell.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/28 10:18 下午
 * @detail
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> findByUserId(Integer userId) {
        return userRoleRepository.findByUserId(userId);
    }
}
