package com.wangjp.sell.service;

import com.wangjp.sell.entity.Organ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/21 9:29 下午
 * @detail
 */
public interface OrganService {

    Organ save(Organ organ);

    Organ findById(Integer id);

    List<Organ> findByIdIn(List<Integer> idList);

    Page<Organ> findAll(Specification<Organ> specification, Pageable pageable);

    List<Organ> findAll(Specification<Organ> specification);

    List<Organ> findAll();

    void deleteOrganWithIds(List<Integer> ids);

    List<Organ> findByParentId(Integer parentId);
}
