package com.wangjp.sell.controller;

import com.wangjp.sell.converter.Page2PaginationVOConverter;
import com.wangjp.sell.converter.Role2RoleVOConverter;
import com.wangjp.sell.entity.Role;
import com.wangjp.sell.entity.RoleMenu;
import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import com.wangjp.sell.form.RoleForm;
import com.wangjp.sell.groups.Update;
import com.wangjp.sell.service.RoleMenuService;
import com.wangjp.sell.service.RoleService;
import com.wangjp.sell.utils.PaginationUtil;
import com.wangjp.sell.utils.ResultVOUtil;
import com.wangjp.sell.vo.PaginationVO;
import com.wangjp.sell.vo.ResultVO;
import com.wangjp.sell.vo.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/5 6:09 下午
 * @detail
 */
@Slf4j
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    RoleMenuService roleMenuService;

    @ApiOperation("创建角色")
    @PostMapping("/create")
    public ResultVO<Object> create(@RequestBody @Validated RoleForm roleForm) {
        Role preRole = roleService.findByName(roleForm.getName());
        if (preRole != null) {
            log.error("【新增角色】角色已存在，id={}，name={}", preRole.getId(), preRole.getName());
            throw new SellException(ResultEnum.ROLE_ALREADY_EXIST);
        }
        Role role = new Role();
        role.setName(roleForm.getName());
        role.setDescription(roleForm.getDescription());
        roleService.save(role, roleForm.getPrivilegeIds());
        return ResultVOUtil.success();
    }

    @ApiOperation("修改角色")
    @PostMapping("/update/{id}")
    public ResultVO<Object> update(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) RoleForm roleForm) {
        Role role = roleService.findById(id);
        if (role == null) {
            log.error("【修改角色】未找到角色信息，id={}", id);
            throw new SellException(ResultEnum.ROLE_NOT_FIND);
        }

        if (!role.getName().equals(roleForm.getName())) {
            Role preRole = roleService.findByName(roleForm.getName());
            if (preRole != null) {
                log.error("【修改角色】角色名称已存在，id={}，name={}", preRole.getId(), preRole.getName());
                throw new SellException(ResultEnum.ROLE_NAME_ALREADY_EXIST);
            }
        }

        role.setName(roleForm.getName());
        role.setDescription(roleForm.getDescription());
        roleService.save(role, roleForm.getPrivilegeIds());
        return ResultVOUtil.success();
    }

    @ApiOperation("查询角色详情")
    @GetMapping("/getInfo/{id}")
    public ResultVO<RoleVO> getRoleInfo(@PathVariable("id") Integer id) {
        Role role = roleService.findById(id);
        if (role == null) {
            log.error("【查询角色】未找到角色信息，id={}", id);
            throw new SellException(ResultEnum.ROLE_NOT_FIND);
        }

        List<Integer> privilegeIds = roleMenuService.findByRoleId(role.getId()).stream().map(RoleMenu::getMenuId).collect(Collectors.toList());

        RoleVO roleVO = Role2RoleVOConverter.convert(role, privilegeIds);

        return ResultVOUtil.success(roleVO);
    }

    @ApiOperation("删除角色")
    @PostMapping("/delete")
    public ResultVO<Object> delete(@RequestBody List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.error("【删除角色】id不能为空");
            throw new SellException(ResultEnum.ROLE_ID_NOT_EMPTY);
        }
        roleService.deleteUsersWithIds(ids);
        return ResultVOUtil.success();
    }

    @ApiOperation("查询角色")
    @GetMapping("/list")
    public ResultVO<PaginationVO<Role>> list(
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNum,
            @RequestParam(required = false) String name
    ) {
        PaginationVO<Role> result;

        Specification<Role> specification = new Specification<Role>() {
            @Override
            // Root 用于获取属性字段，CriteriaQuery 可以用于简单条件查询，CriteriaBuilder 用于构造复杂条件查询
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //集合 用于封装查询条件
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(name)) {
                    list.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
                }

                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };

        if (pageSize == 0) {
            List<Role> roleList = roleService.findAll(specification);
            result = PaginationUtil.genNotPaging(roleList);
        } else {
            PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
            result = Page2PaginationVOConverter.convert(roleService.findAll(specification, pageRequest));
        }


        return ResultVOUtil.success(result);
    }
}
