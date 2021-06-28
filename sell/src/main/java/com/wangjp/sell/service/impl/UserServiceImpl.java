package com.wangjp.sell.service.impl;

import com.wangjp.sell.entity.RoleMenu;
import com.wangjp.sell.entity.User;
import com.wangjp.sell.entity.UserRole;
import com.wangjp.sell.repository.UserRepository;
import com.wangjp.sell.repository.UserRoleRepository;
import com.wangjp.sell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/27 9:25 下午
 * @detail
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findAll(Specification<User> specification, Pageable pageable) {
        return userRepository.findAll(specification, pageable);
    }

    @Override
    public User save(User user, List<Integer> roleIds) {
        User saveUser = userRepository.save(user);

        List<UserRole> userRoleList = userRoleRepository.findByUserId(user.getId());
        List<Integer> commonIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        commonIds.retainAll(roleIds);

        List<Integer> deleteIds = userRoleList.stream().filter(item -> !commonIds.contains(item.getRoleId())).map(UserRole::getId).collect(Collectors.toList());
        List<UserRole> addUserRoles = roleIds.stream().filter(id -> !commonIds.contains(id)).map(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(saveUser.getId());
            userRole.setRoleId(roleId);
            return userRole;
        }).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(deleteIds)) {
            userRoleRepository.deleteUserRoleWithIds(deleteIds);
        }

        if (!CollectionUtils.isEmpty(addUserRoles)) {
            userRoleRepository.saveAll(addUserRoles);
        }

        return saveUser;
    }

    @Override
    public void deleteUsersWithIds(List<Integer> ids) {
        userRepository.deleteUsersWithIds(ids);
    }
}
