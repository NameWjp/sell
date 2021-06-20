package com.wangjp.sell.converter;

import com.wangjp.sell.entity.Menu;
import com.wangjp.sell.pojo.MenuTreeNode;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/12 11:36 下午
 * @detail
 */
public class Menu2MenuTreeNodeConverter {

    public static MenuTreeNode convert(Menu menu) {
        MenuTreeNode menuTreeNode = new MenuTreeNode();
        BeanUtils.copyProperties(menu, menuTreeNode);
        return menuTreeNode;
    }

    public static List<MenuTreeNode> convert(List<Menu> menuList) {
        return menuList.stream().map(Menu2MenuTreeNodeConverter::convert).collect(Collectors.toList());
    }
}
