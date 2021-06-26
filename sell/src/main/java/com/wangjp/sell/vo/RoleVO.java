package com.wangjp.sell.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/26 4:59 下午
 * @detail
 */
@Data
public class RoleVO {

    private Integer id;

    private String name;

    private String description;

    private List<Integer> privilegeIds;
}
