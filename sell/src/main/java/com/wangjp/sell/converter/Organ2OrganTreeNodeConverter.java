package com.wangjp.sell.converter;

import com.wangjp.sell.entity.Organ;
import com.wangjp.sell.pojo.OrganTreeNode;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/21 10:14 下午
 * @detail
 */
public class Organ2OrganTreeNodeConverter {

    public static OrganTreeNode convert(Organ organ) {
        OrganTreeNode organTreeNode = new OrganTreeNode();
        BeanUtils.copyProperties(organ, organTreeNode);
        return organTreeNode;
    }

    public static List<OrganTreeNode> convert(List<Organ> organList) {
        return organList.stream().map(Organ2OrganTreeNodeConverter::convert).collect(Collectors.toList());
    }
}
