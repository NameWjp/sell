package com.wangjp.sell.service;

import com.wangjp.sell.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/27 9:21 下午
 * @detail
 */
public interface UserService {

    User findById(Integer id);

    User findByUsername(String username);

    Page<User> findAll(Specification<User> specification, Pageable pageable);

    User save(User user);

    void deleteUsersWithIds(List<Integer> ids);
}
