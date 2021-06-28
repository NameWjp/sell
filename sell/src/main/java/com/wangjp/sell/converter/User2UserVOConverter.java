package com.wangjp.sell.converter;

import com.wangjp.sell.entity.User;
import com.wangjp.sell.vo.UserVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/5/22 5:32 下午
 * @detail
 */
public class User2UserVOConverter {

    public static UserVO convert(User user) {
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setIsEnable(user.getIsEnable());
        userVO.setUsername(user.getUsername());
        userVO.setCreateTime(user.getCreateTime());
        userVO.setUpdateTime(user.getUpdateTime());
        userVO.setOrganId(user.getOrganId());

        return userVO;
    }

    public static List<UserVO> convert(List<User> userList) {
        return userList.stream().map(User2UserVOConverter::convert).collect(Collectors.toList());
    }
}
