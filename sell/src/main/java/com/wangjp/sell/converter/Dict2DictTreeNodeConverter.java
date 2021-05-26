package com.wangjp.sell.converter;

import com.wangjp.sell.entity.Dict;
import com.wangjp.sell.pojo.DictTreeNode;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/5/26 9:36 下午
 * @detail
 */
public class Dict2DictTreeNodeConverter {

    public static DictTreeNode convert(Dict dict) {
        DictTreeNode dictTreeNode = new DictTreeNode();
        BeanUtils.copyProperties(dict, dictTreeNode);
        return dictTreeNode;
    }

    public static List<DictTreeNode> convert(List<Dict> dictList) {
        return dictList.stream().map(Dict2DictTreeNodeConverter::convert).collect(Collectors.toList());
    }
}
