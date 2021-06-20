package com.wangjp.sell.controller;

import com.wangjp.sell.constant.UserConstant;
import com.wangjp.sell.converter.Page2PaginationVOConverter;
import com.wangjp.sell.converter.User2UserVOConverter;
import com.wangjp.sell.entity.User;
import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import com.wangjp.sell.form.UserForm;
import com.wangjp.sell.groups.Update;
import com.wangjp.sell.service.UserService;
import com.wangjp.sell.utils.ResultVOUtil;
import com.wangjp.sell.vo.PaginationVO;
import com.wangjp.sell.vo.ResultVO;
import com.wangjp.sell.vo.UserVO;
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

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/27 9:35 下午
 * @detail
 */
@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("创建用户")
    @PostMapping("/create")
    public ResultVO<Object> create(@RequestBody @Validated UserForm userForm) {
        User preUser = userService.findByUsername(userForm.getUsername());
        if (preUser != null) {
            log.error("【新增用户】用户已存在，id={}，username={}", preUser.getId(), preUser.getUsername());
            throw new SellException(ResultEnum.USER_ALREADY_EXIST);
        }
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(UserConstant.defaultPassword);
        user.setIsEnable(userForm.getIsEnable());
        userService.save(user);
        return ResultVOUtil.success();
    }

    @ApiOperation("修改用户")
    @PostMapping("/update/{id}")
    public ResultVO<Object> update(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) UserForm userForm) {
        User user = userService.findById(id);
        if (user == null) {
            log.error("【修改用户】未找到用户信息，id={}", id);
            throw new SellException(ResultEnum.USER_NOT_FIND);
        }

        if (!user.getUsername().equals(userForm.getUsername())) {
            User preUser = userService.findByUsername(userForm.getUsername());
            if (preUser != null) {
                log.error("【修改用户】用户名称已存在，id={}，username={}", preUser.getId(), preUser.getUsername());
                throw new SellException(ResultEnum.USER_USERNAME_ALREADY_EXIST);
            }
        }

        user.setUsername(userForm.getUsername());
        user.setIsEnable(userForm.getIsEnable());
        userService.save(user);
        return ResultVOUtil.success();
    }

    @ApiOperation("查询用户")
    @GetMapping("/getInfo/{id}")
    public ResultVO<UserVO> getUserInfo(@PathVariable("id") Integer id) {
        User user = userService.findById(id);
        if (user == null) {
            log.error("【查询用户】未找到用户信息，id={}", id);
            throw new SellException(ResultEnum.USER_NOT_FIND);
        }
        return ResultVOUtil.success(User2UserVOConverter.convert(user));
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete")
    public ResultVO<Object> delete(@RequestBody List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.error("【删除用户】id不能为空");
            throw new SellException(ResultEnum.USER_ID_NOT_EMPTY);
        }
        userService.deleteUsersWithIds(ids);
        return ResultVOUtil.success();
    }

    @ApiOperation("查询用户")
    @GetMapping("/list")
    public ResultVO<PaginationVO<UserVO>> list(
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNum,
            @RequestParam(required = false) String username
    ) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        Specification<User> specification = new Specification<User>() {
            @Override
            // Root 用于获取属性字段，CriteriaQuery 可以用于简单条件查询，CriteriaBuilder 用于构造复杂条件查询
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //集合 用于封装查询条件
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(username)) {
                    list.add(criteriaBuilder.like(root.get("username"), "%" + username + "%"));
                }

                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };

        Page<UserVO> userVOPage = userService.findAll(specification, pageRequest).map(User2UserVOConverter::convert);

       return ResultVOUtil.success(Page2PaginationVOConverter.convert(userVOPage));
    }
}
