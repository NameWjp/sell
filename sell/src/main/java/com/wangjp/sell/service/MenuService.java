package com.wangjp.sell.service;

import com.wangjp.sell.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/6 5:20 下午
 * @detail
 */
public interface MenuService {

    Menu save(Menu menu);

    Menu findById(Integer id);

    Menu findByCode(String code);

    Page<Menu> findAll(Specification<Menu> specification, Pageable pageable);

    List<Menu> findAll();

    void deleteMenuWithIds(List<Integer> ids);

    List<Menu> findByParentId(Integer parentId);

    List<Menu> findMenuByCodeIn(List<String> codes);
}
