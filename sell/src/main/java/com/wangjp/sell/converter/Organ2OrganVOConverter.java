package com.wangjp.sell.converter;

import com.wangjp.sell.entity.Organ;
import com.wangjp.sell.vo.OrganVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/21 10:45 下午
 * @detail
 */
public class Organ2OrganVOConverter {

    public static OrganVO convert(Organ organ, List<Organ> parentList) {
        OrganVO organVO = new OrganVO();
        BeanUtils.copyProperties(organ, organVO);
        String parentName = "";
        for (Organ parent : parentList) {
            if (parent.getId().equals(organ.getParentId())) {
                parentName = parent.getName();
            }
        }
        organVO.setParentName(parentName);
        return organVO;
    }

    public static List<OrganVO> convert(List<Organ> organList, List<Organ> parentList) {
        return organList.stream().map(organ -> Organ2OrganVOConverter.convert(organ, parentList)).collect(Collectors.toList());
    }
}
