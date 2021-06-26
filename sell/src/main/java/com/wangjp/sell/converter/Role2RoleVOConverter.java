package com.wangjp.sell.converter;

import com.wangjp.sell.entity.Role;
import com.wangjp.sell.vo.RoleVO;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/26 5:20 下午
 * @detail
 */
public class Role2RoleVOConverter {

    public static RoleVO convert(Role role, List<Integer> privilegeIds) {
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(role, roleVO);
        roleVO.setPrivilegeIds(privilegeIds);
        return roleVO;
    }
}
