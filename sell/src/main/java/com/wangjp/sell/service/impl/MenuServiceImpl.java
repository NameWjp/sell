package com.wangjp.sell.service.impl;

import com.wangjp.sell.entity.Menu;
import com.wangjp.sell.repository.MenuRepository;
import com.wangjp.sell.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/6 5:38 下午
 * @detail
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository repository;

    @Override
    public Menu save(Menu menu) {
        return repository.save(menu);
    }

    @Override
    public Menu findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Menu findByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public Page<Menu> findAll(Specification<Menu> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public List<Menu> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteMenuWithIds(List<Integer> ids) {
        repository.deleteMenuWithIds(ids);
    }

    @Override
    public List<Menu> findByParentId(Integer parentId) {
        return repository.findByParentId(parentId);
    }
}
