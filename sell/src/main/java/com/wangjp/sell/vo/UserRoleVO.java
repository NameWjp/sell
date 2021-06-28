package com.wangjp.sell.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/28 10:14 下午
 * @detail
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserRoleVO extends UserVO {

    private List<Integer> roleIds;
}
