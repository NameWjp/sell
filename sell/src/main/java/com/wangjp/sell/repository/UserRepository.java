package com.wangjp.sell.repository;

import com.wangjp.sell.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/27 9:10 下午
 * @detail
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);

    @Modifying // 告诉 jpa 这是个修改或删除操作，使用了 @Modifying 需要用 @Transactional 注解进行事务隔离 详见：https://www.debugger.wiki/article/html/1555426800335686
    @Query("delete from User u where u.id in ?1")
    void deleteUsersWithIds(List<Integer> ids);
}
