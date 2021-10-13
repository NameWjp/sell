package com.wangjp.sell.controller;

import com.wangjp.sell.constant.OrganConstant;
import com.wangjp.sell.converter.Organ2OrganTreeNodeConverter;
import com.wangjp.sell.converter.Organ2OrganVOConverter;
import com.wangjp.sell.converter.Page2PaginationVOConverter;
import com.wangjp.sell.entity.Organ;
import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import com.wangjp.sell.form.OrganForm;
import com.wangjp.sell.groups.Update;
import com.wangjp.sell.pojo.OrganTreeNode;
import com.wangjp.sell.service.OrganService;
import com.wangjp.sell.utils.PaginationUtil;
import com.wangjp.sell.utils.ResultVOUtil;
import com.wangjp.sell.utils.TreeUtil;
import com.wangjp.sell.utils.UserUtil;
import com.wangjp.sell.vo.OrganVO;
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
 * @date 2021/6/21 9:37 下午
 * @detail
 */
@Slf4j
@Api(tags = "组织机构管理")
@RestController
@RequestMapping("/organ")
public class OrganController {

    @Autowired
    OrganService organService;

    @ApiOperation("创建组织机构")
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('organ:create')")
    public ResultVO<Object> create(@RequestBody @Validated OrganForm organForm) {
        Integer parentId = organForm.getParentId();
        if (parentId == null) {
            parentId = OrganConstant.rootParentId;
        } else {
            Organ parentOrgan = organService.findById(parentId);
            if (parentOrgan == null) {
                log.error("【新建组织机构】父级机构未找到，parentId={}", parentId);
                throw new SellException(ResultEnum.ORGAN_PARENT_ID_NOT_EXIST);
            }
        }

        Organ organ = new Organ();

        organ.setParentId(parentId);
        organ.setName(organForm.getName());

        organService.save(organ);

        return ResultVOUtil.success();
    }

    @ApiOperation("修改组织机构")
    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('organ:update')")
    public ResultVO<Object> update(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) OrganForm organForm) {
        Organ organ = organService.findById(id);
        if (organ == null) {
            log.error("【修改组织机构】组织机构未找到，id={}", id);
            throw new SellException(ResultEnum.ORGAN_NOT_EXIST);
        }

        Integer parentId = organForm.getParentId();
        if (parentId == null) {
            parentId = OrganConstant.rootParentId;
        } else if (!parentId.equals(OrganConstant.rootParentId)){
            Organ parentOrgan = organService.findById(parentId);
            if (parentOrgan == null) {
                log.error("【修改组织机构】父级组织机构未找到，parentId={}", parentId);
                throw new SellException(ResultEnum.ORGAN_PARENT_ID_NOT_EXIST);
            }
        }

        organ.setParentId(parentId);
        organ.setName(organForm.getName());

        organService.save(organ);

        return ResultVOUtil.success();
    }

    @ApiOperation("删除组织机构")
    @PostMapping("/delete")
    @PreAuthorize("hasAnyAuthority('organ:delete')")
    public ResultVO<Object> delete(@RequestBody List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.error("【删除组织机构】id不能为空");
            throw new SellException(ResultEnum.ORGAN_ID_NOT_EMPTY);
        }
        for (Integer id : ids) {
            List<Organ> children = organService.findByParentId(id);
            if (!CollectionUtils.isEmpty(children)) {
                log.error("【删除组织机构】存在子节点，id={}", id);
                throw new SellException(ResultEnum.ORGAN_DELETE_EXIST_CHILDREN);
            }
        }
        organService.deleteOrganWithIds(ids);
        return ResultVOUtil.success();
    }

    @ApiOperation("获取组织机构列表")
    @GetMapping("/list")
    public ResultVO<PaginationVO<OrganVO>> list(
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNum,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer parentId
    ) {
        Integer organId = UserUtil.getCurrentUserInfoVO().getOrganId();
        List<Integer> allOrganId = organService.findSelfAndChildren(organId).stream().map(Organ::getId).collect(Collectors.toList());

        PaginationVO<OrganVO> result;

        Specification<Organ> specification = new Specification<Organ>() {
            @Override
            // Root 用于获取属性字段，CriteriaQuery 可以用于简单条件查询，CriteriaBuilder 用于构造复杂条件查询
            public Predicate toPredicate(Root<Organ> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                // 根据当前登录用户的组织机构过滤数据
                list.add(root.get("id").in(allOrganId));
                if (!StringUtils.isEmpty(name)) {
                    list.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
                }
                if (parentId != null) {
                    list.add(criteriaBuilder.equal(root.get("parentId"), parentId));
                }

                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };

        if (pageSize == 0) {
            List<Organ> organList = organService.findAll(specification);
            List<Integer> parentIds = organList.stream().map(Organ::getParentId).distinct().collect(Collectors.toList());
            List<Organ> parentList = organService.findByIdIn(parentIds);
            List<OrganVO> organVOList = Organ2OrganVOConverter.convert(organList, parentList);
            result = PaginationUtil.genNotPaging(organVOList);
        } else {
            PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
            Page<Organ> organPage = organService.findAll(specification, pageRequest);
            List<Integer> parentIds = organPage.getContent().stream().map(Organ::getParentId).distinct().collect(Collectors.toList());
            List<Organ> parentList = organService.findByIdIn(parentIds);
            Page<OrganVO> organVOPage = organPage.map(organ -> Organ2OrganVOConverter.convert(organ, parentList));
            result = Page2PaginationVOConverter.convert(organVOPage);
        }

        return ResultVOUtil.success(result);
    }

    @ApiOperation("查询组织机构")
    @GetMapping("/getInfo/{id}")
    public ResultVO<Organ> getOrganInfo(@PathVariable("id") Integer id) {
        Organ organ = organService.findById(id);
        if (organ == null) {
            log.error("【查询组织机构】组织机构未找到，id={}", id);
            throw new SellException(ResultEnum.ORGAN_NOT_EXIST);
        }
        return ResultVOUtil.success(organ);
    }

    @ApiOperation("获取树结构")
    @GetMapping("/getTree")
    public ResultVO<Collection<OrganTreeNode>> getTree() {
        Integer organId = UserUtil.getCurrentUserInfoVO().getOrganId();
        List<Integer> allChildrenOrganId = organService.findAllChildren(organId).stream().map(Organ::getId).collect(Collectors.toList());
        List<Integer> allOrganId = organService.findSelfAndParent(organId).stream().map(Organ::getId).collect(Collectors.toList());
        allOrganId.addAll(allChildrenOrganId);

        List<OrganTreeNode> organTreeNodeList = Organ2OrganTreeNodeConverter.convert(organService.findByIdIn(allOrganId));
        List<OrganTreeNode> tree = (List<OrganTreeNode>) TreeUtil.list2Tree(organTreeNodeList, OrganTreeNode.class);
        return ResultVOUtil.success(tree);
    }
}
