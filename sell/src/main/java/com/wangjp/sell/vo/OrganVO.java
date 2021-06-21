package com.wangjp.sell.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/21 10:42 下午
 * @detail
 */
@Data
public class OrganVO {

    private Integer id;

    private String name;

    private Integer parentId;

    private String parentName;

    private Date createTime;

    private Date updateTime;
}
