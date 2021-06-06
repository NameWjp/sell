package com.wangjp.sell.service.impl;

import com.wangjp.sell.entity.Role;
import com.wangjp.sell.repository.RoleRepository;
import com.wangjp.sell.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/5 4:44 下午
 * @detail
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository repository;

    @Override
    public Role findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Role findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Page<Role> findAll(Specification<Role> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public void deleteUsersWithIds(List<Integer> ids) {
        repository.deleteRoleWithIds(ids);
    }
}
