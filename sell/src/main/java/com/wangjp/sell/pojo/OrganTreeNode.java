package com.wangjp.sell.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/21 10:12 下午
 * @detail
 */
@Data
public class OrganTreeNode {

    private Integer id;

    private String name;

    private Integer parentId;

    private List<MenuTreeNode> children = new ArrayList<>();
}
