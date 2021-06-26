package com.wangjp.sell.repository;

import com.wangjp.sell.entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/26 4:05 下午
 * @detail
 */
public interface RoleMenuRepository extends JpaRepository<RoleMenu, Integer> {

    List<RoleMenu> findByRoleId(Integer roleId);

    @Transactional
    @Modifying
    @Query("delete from RoleMenu where id in ?1")
    void deleteRoleMenuWithIds(List<Integer> ids);
}
