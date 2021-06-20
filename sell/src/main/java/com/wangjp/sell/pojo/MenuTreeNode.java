package com.wangjp.sell.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/12 11:30 下午
 * @detail
 */
@Data
public class MenuTreeNode {

    private Integer id;

    private String url;

    private Integer type;

    private String icon;

    private String name;

    private Integer parentId;

    private Integer sort;

    private String code;

    private List<MenuTreeNode> children = new ArrayList<>();
}
