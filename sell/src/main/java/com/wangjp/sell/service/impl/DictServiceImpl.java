package com.wangjp.sell.service.impl;

import com.wangjp.sell.entity.Dict;
import com.wangjp.sell.repository.DictRepository;
import com.wangjp.sell.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/5/23 12:24 下午
 * @detail
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictRepository repository;

    @Override
    public Dict save(Dict dict) {
        return repository.save(dict);
    }

    @Override
    public Dict findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Dict> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Dict> findAll(Specification<Dict> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public Dict findByDictValue(String dictValue) {
        return repository.findByDictValue(dictValue);
    }

    @Override
    public void deleteDictWithIds(List<Integer> ids) {
        repository.deleteDictWithIds(ids);
    }

    @Override
    public List<Dict> findByParentId(Integer parentId) {
        return repository.findByParentId(parentId);
    }
}
