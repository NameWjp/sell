package com.wangjp.sell.service.impl;

import com.wangjp.sell.entity.Role;
import com.wangjp.sell.entity.RoleMenu;
import com.wangjp.sell.repository.RoleMenuRepository;
import com.wangjp.sell.repository.RoleRepository;
import com.wangjp.sell.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/5 4:44 下午
 * @detail
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleMenuRepository roleMenuRepository;

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Page<Role> findAll(Specification<Role> specification, Pageable pageable) {
        return roleRepository.findAll(specification, pageable);
    }

    @Override
    public List<Role> findAll(Specification<Role> specification) {
        return roleRepository.findAll(specification);
    }

    @Override
    public Role save(Role role, List<Integer> privilegeIds) {
        Role saveRole = roleRepository.save(role);

        List<RoleMenu> roleMenuList = roleMenuRepository.findByRoleId(saveRole.getId());
        List<Integer> commonIds = roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        commonIds.retainAll(privilegeIds);

        List<Integer> deleteIds = roleMenuList.stream().filter(item -> !commonIds.contains(item.getMenuId())).map(RoleMenu::getId).collect(Collectors.toList());
        List<RoleMenu> addRoleMenus = privilegeIds.stream().filter(id -> !commonIds.contains(id)).map(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(saveRole.getId());
            roleMenu.setMenuId(menuId);
            return roleMenu;
        }).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(deleteIds)) {
            roleMenuRepository.deleteRoleMenuWithIds(deleteIds);
        }

        if (!CollectionUtils.isEmpty(addRoleMenus)) {
            roleMenuRepository.saveAll(addRoleMenus);
        }

        return saveRole;
    }

    @Override
    public void deleteUsersWithIds(List<Integer> ids) {
        roleRepository.deleteRoleWithIds(ids);
    }
}
