package com.wangjp.sell.controller;

import com.wangjp.sell.constant.DictConstant;
import com.wangjp.sell.converter.Dict2DictTreeNodeConverter;
import com.wangjp.sell.converter.Page2PaginationVOConverter;
import com.wangjp.sell.entity.Dict;
import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import com.wangjp.sell.form.DictForm;
import com.wangjp.sell.groups.Update;
import com.wangjp.sell.pojo.DictTreeNode;
import com.wangjp.sell.service.DictService;
import com.wangjp.sell.utils.ResultVOUtil;
import com.wangjp.sell.utils.TreeUtil;
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
 * @date 2021/5/23 12:26 下午
 * @detail
 */
@Slf4j
@Api(tags = "字典管理")
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @ApiOperation("创建字典")
    @PostMapping("/create")
    public ResultVO<Object> create(@RequestBody @Validated DictForm dictForm) {
        Integer parentId = dictForm.getParentId();
        String parentIds;
        if (parentId == null) {
            parentId = DictConstant.rootParentId;
            parentIds = DictConstant.rootParentId.toString();
        } else {
            Dict parentDict = dictService.findById(parentId);
            if (parentDict == null) {
                log.error("【新建字典】父级字典未找到，parentId={}", parentId);
                throw new SellException(ResultEnum.DICT_PARENT_ID_NOT_EXIST);
            }
            parentIds = parentDict.getParentIds().concat(",").concat(parentDict.getId().toString());
        }

        Dict dict = new Dict();
        dict.setParentId(parentId);
        dict.setParentIds(parentIds);
        dict.setDictCode(dictForm.getDictCode());
        dict.setDictName(dictForm.getDictName());
        dict.setDictValue(dictForm.getDictValue());

        dictService.save(dict);

        return ResultVOUtil.success();
    }

    @ApiOperation("修改字典")
    @PostMapping("/update/{id}")
    public ResultVO<Object> update(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) DictForm dictForm) {
        Dict dict = dictService.findById(id);
        if (dict == null) {
            log.error("【修改字典】字典未找到，id={}", id);
            throw new SellException(ResultEnum.DICT_NOT_EXIST);
        }
        dict.setDictName(dictForm.getDictName());
        dict.setDictValue(dictForm.getDictValue());

        dictService.save(dict);

        return ResultVOUtil.success();
    }

    @ApiOperation("删除字典")
    @PostMapping("/delete")
    public ResultVO<Object> delete(@RequestBody List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.error("【删除字典】id不能为空");
            throw new SellException(ResultEnum.DICT_ID_NOT_EMPTY);
        }
        for (Integer id : ids) {
            List<Dict> children = dictService.findByParentId(id);
            if (!CollectionUtils.isEmpty(children)) {
                log.error("【删除字典】存在子节点，id={}", id);
                throw new SellException(ResultEnum.DICT_DELETE_EXIST_CHILDREN);
            }
        }
        dictService.deleteDictWithIds(ids);
        return ResultVOUtil.success();
    }

    @ApiOperation("查询字典")
    @GetMapping("/getInfo/{id}")
    public ResultVO<Dict> getUserInfo(@PathVariable("id") Integer id) {
        Dict dict = dictService.findById(id);
        if (dict == null) {
            log.error("【查询字典】字典未找到，id={}", id);
            throw new SellException(ResultEnum.DICT_NOT_EXIST);
        }
        return ResultVOUtil.success(dict);
    }

    @ApiOperation("获取子树结构")
    @GetMapping("/getSubTree")
    public ResultVO<Collection<DictTreeNode>> getSubTree() {
        List<DictTreeNode> dictTreeNodeList = Dict2DictTreeNodeConverter.convert(dictService.findAll());
        Collection<DictTreeNode> tree = TreeUtil.list2Tree(dictTreeNodeList, DictTreeNode.class);
        Collection<DictTreeNode> result = TreeUtil.getSubTree(tree, DictTreeNode.class, DictConstant.subTreeShowLevel);
        return ResultVOUtil.success(result);
    }

    @ApiOperation("获取字典列表")
    @GetMapping("/list")
    public ResultVO<PaginationVO<Dict>> list(
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNum,
            @RequestParam(required = false) String dictCode,
            @RequestParam(required = false) String dictName,
            @RequestParam(required = false) Integer parentId
    ) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        Specification<Dict> specification = new Specification<Dict>() {
            @Override
            // Root 用于获取属性字段，CriteriaQuery 可以用于简单条件查询，CriteriaBuilder 用于构造复杂条件查询
            public Predicate toPredicate(Root<Dict> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //集合 用于封装查询条件
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(dictCode)) {
                    list.add(criteriaBuilder.like(root.get("dictCode"), "%" + dictCode + "%"));
                }
                if (!StringUtils.isEmpty(dictName)) {
                    list.add(criteriaBuilder.like(root.get("dictName"), "%" + dictName + "%"));
                }
                if (parentId != null) {
                    list.add(criteriaBuilder.equal(root.get("parentId"), parentId));
                }

                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };

        Page<Dict> dictPage = dictService.findAll(specification, pageRequest);

        return ResultVOUtil.success(Page2PaginationVOConverter.convert(dictPage));
    }

    @ApiOperation("获取所有字典列表")
    @GetMapping("/allList")
    public ResultVO<List<Dict>> allList(@RequestParam(required = false) Integer level) {
        List<Dict> dictList = dictService.findAll();
        if (level != null) {
            dictList = dictList.stream().filter(e -> e.getParentIds().split(",").length == level).collect(Collectors.toList());
        }
        return ResultVOUtil.success(dictList);
    }
}
