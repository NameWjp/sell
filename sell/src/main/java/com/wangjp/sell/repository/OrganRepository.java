package com.wangjp.sell.repository;

import com.wangjp.sell.entity.Organ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/21 9:26 下午
 * @detail
 */
public interface OrganRepository extends JpaRepository<Organ, Integer>, JpaSpecificationExecutor<Organ> {

    List<Organ> findByParentId(Integer parentId);

    List<Organ> findByIdIn(List<Integer> idList);

    @Transactional
    @Modifying
    @Query("delete from Organ where id in ?1")
    void deleteOrganWithIds(List<Integer> ids);
}
