package com.wangjp.sell.repository;

import com.wangjp.sell.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/6 5:15 下午
 * @detail
 */
public interface MenuRepository extends JpaRepository<Menu, Integer>, JpaSpecificationExecutor<Menu> {

    Menu findByCode(String code);

    List<Menu> findByParentId(Integer parentId);

    List<Menu> findMenuByCodeIn(List<String> codes);

    @Transactional
    @Modifying
    @Query("delete from Menu where id in ?1")
    void deleteMenuWithIds(List<Integer> ids);

    @Query(value = "select distinct menu.* from menu,role_menu where menu.id = role_menu.menu_id and role_menu.role_id in (:ids)", nativeQuery = true)
    List<Menu> selectByRoleIds(@Param("ids") List<Integer> ids);
}
