package com.wangjp.sell.controller;

import com.wangjp.sell.constant.MenuConstant;
import com.wangjp.sell.converter.Menu2MenuTreeNodeConverter;
import com.wangjp.sell.converter.Page2PaginationVOConverter;
import com.wangjp.sell.entity.Menu;
import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import com.wangjp.sell.form.MenuForm;
import com.wangjp.sell.groups.Update;
import com.wangjp.sell.pojo.MenuTreeNode;
import com.wangjp.sell.service.MenuService;
import com.wangjp.sell.utils.ResultVOUtil;
import com.wangjp.sell.utils.TreeUtil;
import com.wangjp.sell.utils.UserUtil;
import com.wangjp.sell.vo.PaginationVO;
import com.wangjp.sell.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/6 6:22 下午
 * @detail
 */
@Slf4j
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @ApiOperation("创建菜单")
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('menu:create')")
    public ResultVO<Object> create(@RequestBody @Validated MenuForm menuForm) {
        Menu preMenu = menuService.findByCode(menuForm.getCode());
        if (preMenu != null) {
            log.error("【新建菜单】菜单编码已存在，preMenu={}", preMenu.toString());
            throw new SellException(ResultEnum.MENU_CODE_ALREADY_EXIST);
        }

        Integer parentId = menuForm.getParentId();
        if (parentId == null) {
            parentId = MenuConstant.rootParentId;
        } else {
            Menu parentMenu = menuService.findById(parentId);
            if (parentMenu == null) {
                log.error("【新建菜单】父级菜单未找到，parentId={}", parentId);
                throw new SellException(ResultEnum.MENU_PARENT_ID_NOT_EXIST);
            }
        }

        Menu menu = new Menu();

        menu.setUrl(menuForm.getUrl());
        menu.setType(menuForm.getType());
        menu.setIcon(menuForm.getIcon());
        menu.setName(menuForm.getName());
        menu.setParentId(parentId);
        menu.setSort(menuForm.getSort());
        menu.setCode(menuForm.getCode());

        menuService.save(menu);

        return ResultVOUtil.success();
    }

    @ApiOperation("删除菜单")
    @PostMapping("/delete")
    @PreAuthorize("hasAnyAuthority('menu:delete')")
    public ResultVO<Object> delete(@RequestBody List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.error("【删除菜单】id不能为空");
            throw new SellException(ResultEnum.MENU_ID_NOT_EMPTY);
        }
        for (Integer id : ids) {
            List<Menu> children = menuService.findByParentId(id);
            if (!CollectionUtils.isEmpty(children)) {
                log.error("【删除菜单】存在子节点，id={}", id);
                throw new SellException(ResultEnum.MENU_DELETE_EXIST_CHILDREN);
            }
        }
        menuService.deleteMenuWithIds(ids);
        return ResultVOUtil.success();
    }

    @ApiOperation("修改菜单")
    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('menu:update')")
    public ResultVO<Object> update(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) MenuForm menuForm) {
        Menu menu = menuService.findById(id);
        if (menu == null) {
            log.error("【修改菜单】菜单未找到，id={}", id);
            throw new SellException(ResultEnum.MENU_NOT_EXIST);
        }

        if (!menu.getCode().equals(menuForm.getCode())) {
            Menu preMenu = menuService.findByCode(menuForm.getCode());
            if (preMenu != null) {
                log.error("【修改菜单】菜单编码已存在，id={}，code={}", preMenu.getId(), preMenu.getCode());
                throw new SellException(ResultEnum.MENU_CODE_ALREADY_EXIST);
            }
        }

        Integer parentId = menuForm.getParentId();
        if (parentId == null) {
            parentId = MenuConstant.rootParentId;
        } else if (!parentId.equals(MenuConstant.rootParentId)){
            Menu parentMenu = menuService.findById(parentId);
            if (parentMenu == null) {
                log.error("【修改菜单】父级菜单未找到，parentId={}", parentId);
                throw new SellException(ResultEnum.MENU_PARENT_ID_NOT_EXIST);
            }
        }

        menu.setUrl(menuForm.getUrl());
        menu.setType(menuForm.getType());
        menu.setIcon(menuForm.getIcon());
        menu.setName(menuForm.getName());
        menu.setParentId(parentId);
        menu.setSort(menuForm.getSort());
        menu.setCode(menuForm.getCode());

        menuService.save(menu);

        return ResultVOUtil.success();
    }

    @ApiOperation("获取菜单列表")
    @GetMapping("/list")
    public ResultVO<PaginationVO<Menu>> list(
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNum,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) Integer parentId
    ) {
        List<String> privilegeCodes = UserUtil.getCurrentUserInfoVO().getPrivilegeList().stream().map(MenuTreeNode::getCode).collect(Collectors.toList());

        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        Specification<Menu> specification = new Specification<Menu>() {
            @Override
            // Root 用于获取属性字段，CriteriaQuery 可以用于简单条件查询，CriteriaBuilder 用于构造复杂条件查询
            public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                // 如果不是管理员，需要根据当前用户的权限code过滤
                if (!UserUtil.isAdmin()) {
                    list.add(root.get("code").in(privilegeCodes));
                }
                if (!StringUtils.isEmpty(name)) {
                    list.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
                }
                if (!StringUtils.isEmpty(code)) {
                    list.add(criteriaBuilder.like(root.get("code"), "%" + code + "%"));
                }
                if (parentId != null) {
                    list.add(criteriaBuilder.equal(root.get("parentId"), parentId));
                }

                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };

        Page<Menu> menuPage = menuService.findAll(specification, pageRequest);

        return ResultVOUtil.success(Page2PaginationVOConverter.convert(menuPage));
    }

    @ApiOperation("查询菜单")
    @GetMapping("/getInfo/{id}")
    public ResultVO<Menu> getMenuInfo(@PathVariable("id") Integer id) {
        Menu menu = menuService.findById(id);
        if (menu == null) {
            log.error("【查询菜单】菜单未找到，id={}", id);
            throw new SellException(ResultEnum.MENU_NOT_EXIST);
        }
        return ResultVOUtil.success(menu);
    }

    @ApiOperation("获取树结构")
    @GetMapping("/getTree")
    public ResultVO<Collection<MenuTreeNode>> getTree() {
        List<String> privilegeCodes;

        if (UserUtil.isAdmin()) {
            privilegeCodes = menuService.findAll().stream().map(Menu::getCode).collect(Collectors.toList());
        } else {
            privilegeCodes = UserUtil.getCurrentUserInfoVO().getPrivilegeList().stream().map(MenuTreeNode::getCode).collect(Collectors.toList());
        }

        List<MenuTreeNode> menuTreeNodeList = Menu2MenuTreeNodeConverter.convert(menuService.findMenuByCodeIn(privilegeCodes));
        List<MenuTreeNode> tree = (List<MenuTreeNode>)TreeUtil.list2Tree(menuTreeNodeList, MenuTreeNode.class);
        TreeUtil.sortTree(tree, MenuTreeNode.class);

        return ResultVOUtil.success(tree);
    }
}
