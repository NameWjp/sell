package com.wangjp.sell.service.impl;

import com.wangjp.sell.entity.*;
import com.wangjp.sell.repository.MenuRepository;
import com.wangjp.sell.repository.UserRepository;
import com.wangjp.sell.repository.UserRoleRepository;
import com.wangjp.sell.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/9/12 5:06 下午
 * @detail 实现 UserDetailsService 接口
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    MenuRepository menuRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        List<Integer> roleIds = userRoleRepository.findByUserId(user.getId()).stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Menu> menus = menuRepository.selectByRoleIds(roleIds);
        return UserInfoVO.create(user, roleIds, menus);
    }
}
