package com.wangjp.sell.service;

import com.wangjp.sell.entity.Dict;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/5/23 12:21 下午
 * @detail
 */
public interface DictService {

    Dict save(Dict dict);

    Dict findById(Integer id);

    Dict findByDictValue(String dictValue);

    List<Dict> findAll();
}
