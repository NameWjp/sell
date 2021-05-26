package com.wangjp.sell.controller;

import com.wangjp.sell.constant.DictConstant;
import com.wangjp.sell.converter.Dict2DictTreeNodeConverter;
import com.wangjp.sell.entity.Dict;
import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import com.wangjp.sell.form.DictForm;
import com.wangjp.sell.pojo.DictTreeNode;
import com.wangjp.sell.service.DictService;
import com.wangjp.sell.utils.ResultVOUtil;
import com.wangjp.sell.utils.TreeUtil;
import com.wangjp.sell.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

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
        Dict preDict = dictService.findByDictValue(dictForm.getDictValue());
        if (preDict != null) {
            log.error("【新建字典】字典值已存在，preDict={}", preDict.toString());
            throw new SellException(ResultEnum.DICT_VALUE_ALREADY_EXIST);
        }

        Integer parentId = dictForm.getParentId();
        String parentIds;
        if (parentId == null) {
            parentId = DictConstant.rootParentId;
            parentIds = DictConstant.rootParentId.toString();
        } else {
            Dict parentDict = dictService.findById(parentId);
            if (parentDict == null) {
                log.error("【新建字典】父级字典未找到，parentId={}", parentId);
                throw new SellException(ResultEnum.DICT_NOT_EXIST);
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

    @ApiOperation("获取子树结构")
    @GetMapping("/getSubTree")
    public ResultVO<Collection<DictTreeNode>> getSubTree() {
        List<DictTreeNode> dictTreeNodeList = Dict2DictTreeNodeConverter.convert(dictService.findAll());
        return ResultVOUtil.success(TreeUtil.list2Tree(dictTreeNodeList, DictTreeNode.class));
    }
}
