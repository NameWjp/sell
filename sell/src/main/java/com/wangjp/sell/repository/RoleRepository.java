package com.wangjp.sell.repository;

import com.wangjp.sell.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/5 4:40 下午
 * @detail
 */
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    Role findByName(String name);

    @Transactional
    @Modifying
    @Query("delete from Role where id in ?1")
    void deleteRoleWithIds(List<Integer> ids);
}
