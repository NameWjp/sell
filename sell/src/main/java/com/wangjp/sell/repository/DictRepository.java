package com.wangjp.sell.repository;

import com.wangjp.sell.entity.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/5/23 12:14 下午
 * @detail 字典映射
 */
public interface DictRepository extends JpaRepository<Dict, Integer>, JpaSpecificationExecutor<Dict> {

    Dict findByDictValue(String dictValue);

    List<Dict> findByParentId(Integer parentId);

    // 由于 Jpa 默认方法的事务是只读的，这里需要重新定义覆盖，详见：https://www.debugger.wiki/article/html/1555426800335686
    @Transactional
    // 告诉 jpa 这是个修改或删除操作
    @Modifying
    @Query("delete from Dict where id in ?1")
    void deleteDictWithIds(List<Integer> ids);
}
