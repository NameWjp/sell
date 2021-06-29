package com.wangjp.sell.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/28 10:14 下午
 * @detail
 */
@Data
public class UserRoleVO {

    private Integer id;

    private String username;

    private Integer isEnable;

    private Integer organId;

    private Date createTime;

    private Date updateTime;

    private List<Integer> roleIds;
}
