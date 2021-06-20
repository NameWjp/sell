package com.wangjp.sell.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/5/26 9:28 下午
 * @detail 字典树结构
 */
@Data
public class DictTreeNode {

    private Integer id;

    private Integer parentId;

    private String parentIds;

    private String dictCode;

    @JsonProperty("name")
    private String dictName;

    private String dictValue;

    private List<DictTreeNode> children = new ArrayList<>();
}
