package com.wangjp.sell.service.impl;

import com.wangjp.sell.entity.Organ;
import com.wangjp.sell.repository.OrganRepository;
import com.wangjp.sell.service.OrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/21 9:31 下午
 * @detail
 */
@Service
public class OrganServiceImpl implements OrganService {

    @Autowired
    OrganRepository repository;

    @Override
    public Organ save(Organ organ) {
        return repository.save(organ);
    }

    @Override
    public Organ findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Organ> findByIdIn(List<Integer> idList) {
        return repository.findByIdIn(idList);
    }

    @Override
    public Page<Organ> findAll(Specification<Organ> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public List<Organ> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteOrganWithIds(List<Integer> ids) {
        repository.deleteOrganWithIds(ids);
    }

    @Override
    public List<Organ> findByParentId(Integer parentId) {
        return repository.findByParentId(parentId);
    }
}
