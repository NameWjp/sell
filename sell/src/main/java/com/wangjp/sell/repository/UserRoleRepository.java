package com.wangjp.sell.repository;

import com.wangjp.sell.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/28 9:59 下午
 * @detail
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    List<UserRole> findByUserId(Integer userId);

    @Transactional
    @Modifying
    @Query("delete from UserRole where id in ?1")
    void deleteUserRoleWithIds(List<Integer> ids);
}
