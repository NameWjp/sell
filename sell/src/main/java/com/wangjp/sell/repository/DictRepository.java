package com.wangjp.sell.repository;

import com.wangjp.sell.entity.Dict;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/5/23 12:14 下午
 * @detail 字典映射
 */
public interface DictRepository extends JpaRepository<Dict, Integer> {

    Dict findByDictValue(String dictValue);
}
