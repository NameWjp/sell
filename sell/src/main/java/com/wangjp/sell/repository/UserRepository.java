package com.wangjp.sell.repository;

import com.wangjp.sell.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/27 9:10 下午
 * @detail
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUsername(String username);
}
