package com.wangjp.sell.service;

import com.wangjp.sell.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/5 4:38 下午
 * @detail
 */
public interface RoleService {

    Role findById(Integer id);

    Role findByName(String name);

    Page<Role> findAll(Specification<Role> specification, Pageable pageable);

    Role save(Role role);

    void deleteUsersWithIds(List<Integer> ids);
}
