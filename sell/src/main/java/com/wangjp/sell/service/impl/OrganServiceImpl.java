package com.wangjp.sell.service.impl;

import com.wangjp.sell.entity.Organ;
import com.wangjp.sell.repository.OrganRepository;
import com.wangjp.sell.service.OrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
    public List<Organ> findAll(Specification<Organ> specification) {
        return repository.findAll(specification);
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

    @Override
    public List<Organ> findSelfAndChildren(Integer organId) {
        Organ organ = repository.findById(organId).orElse(null);
        List<Organ> organList = findAllChildren(organId);
        organList.add(0, organ);
        return organList;
    }

    @Override
    public List<Organ> findAllChildren(Integer parentId) {
        List<Organ> organList = new ArrayList<>();
        setChildrenByParentId(organList, parentId);
        return organList;
    }

    private void setChildrenByParentId(List<Organ> organList, Integer parentId) {
        // 添加所有子元素
        List<Organ> children = repository.findByParentId(parentId);
        for (Organ organ : children) {
            organList.add(organ);
            setChildrenByParentId(organList, organ.getId());
        }
    }

    @Override
    public List<Organ> findSelfAndParent(Integer organId) {
        Organ organ = repository.findById(organId).orElse(null);
        List<Organ> organList = findAllParent(organId);
        organList.add(organ);
        return organList;
    }

    @Override
    public List<Organ> findAllParent(Integer organId) {
        List<Organ> organList = new ArrayList<>();
        setParentByOrganId(organList, organId);
        Collections.reverse(organList);
        return organList;
    }

    private void setParentByOrganId(List<Organ> organList, Integer organId) {
        // 添加所有父元素
        Organ organ = repository.findById(organId).orElse(null);
        Organ parent = repository.findById(organ.getParentId()).orElse(null);
        if (parent != null) {
            organList.add(parent);
            setParentByOrganId(organList, parent.getId());
        }
    }
}
