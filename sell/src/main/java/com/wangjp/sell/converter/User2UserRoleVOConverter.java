package com.wangjp.sell.converter;

import com.wangjp.sell.entity.User;
import com.wangjp.sell.vo.UserRoleVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/28 10:22 下午
 * @detail
 */
public class User2UserRoleVOConverter {

    public static UserRoleVO convert(User user, List<Integer> roleIds) {
        UserRoleVO userRoleVO = new UserRoleVO();
        userRoleVO.setId(user.getId());
        userRoleVO.setIsEnable(user.getIsEnable());
        userRoleVO.setUsername(user.getUsername());
        userRoleVO.setCreateTime(user.getCreateTime());
        userRoleVO.setUpdateTime(user.getUpdateTime());
        userRoleVO.setOrganId(user.getOrganId());
        userRoleVO.setRoleIds(roleIds);

        return userRoleVO;
    }

    public static List<UserRoleVO> convert(List<User> userList, List<Integer> roleIds) {
        return userList.stream().map(user -> User2UserRoleVOConverter.convert(user, roleIds)).collect(Collectors.toList());
    }
}
